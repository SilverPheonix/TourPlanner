package at.technikum.studentmanagementsystem2.service;

import at.technikum.studentmanagementsystem2.config.Properties_Config;
import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import at.technikum.studentmanagementsystem2.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

@Service
public class TourService {

    private final TourRepository tourRepository;
    private final TourLogService tourLogService;

    private static final Logger logger = LogManager.getLogger(TourService.class);

    @Autowired
    public TourService(TourRepository tourRepository, TourLogService tourLogService) {
        this.tourRepository = tourRepository;
        this.tourLogService = tourLogService;
    }

    public List<Tour> getAllTours() {
        logger.info("Fetching all tours");
        return tourRepository.findAll();
    }

    public Optional<Tour> getTourById(UUID id) {
        logger.info("Fetching tour by ID: " + id);
        return tourRepository.findById(id);
    }

    public Tour createTour(Tour tour) {
        logger.info("Creating tour: " + tour.getName());
        return tourRepository.save(tour);
    }

    public Tour updateTour(Tour updatedTour) {
        logger.info("Updating tour with ID: " + updatedTour.getId());
        return tourRepository.findById(updatedTour.getId()).map(tour -> {
            tour.setName(updatedTour.getName());
            tour.setDescription(updatedTour.getDescription());
            tour.setFrom(updatedTour.getFrom());
            tour.setTo(updatedTour.getTo());
            tour.setTransportType(updatedTour.getTransportType());
            tour.setDistance(updatedTour.getDistance());
            tour.setEstimatedTime(updatedTour.getEstimatedTime());
            tour.setImageUrl(updatedTour.getImageUrl());
            tour.setStartLat(updatedTour.getStartLat());
            tour.setStartLon(updatedTour.getStartLon());
            tour.setEndLat(updatedTour.getEndLat());
            tour.setEndLon(updatedTour.getEndLon());
            return tourRepository.save(tour);
        }).orElseThrow(() -> {
            logger.error("Tour not found for update: " + updatedTour.getId());
            return new RuntimeException("Tour not found with id: " + updatedTour.getId());
        });
    }

    public void deleteTour(UUID id) {
        if (!tourRepository.existsById(id)) {
            logger.warn("Attempted to delete non-existent tour: " + id);
            throw new RuntimeException("Tour not found with id: " + id);
        }
        tourRepository.deleteById(id);
        logger.info("Deleted tour with ID: " + id);
    }

    public void updateComputedAttributes(TourViewModel tourViewModel) {
        logger.info("Updating computed attributes for tour: " + tourViewModel.getName());
        Tour tour = tourRepository.findById(UUID.fromString(tourViewModel.getId()))
                .orElseThrow(() -> {
                    logger.error("Tour not found for computing attributes: " + tourViewModel.getId());
                    return new IllegalArgumentException("Tour with ID " + UUID.fromString(tourViewModel.getId()) + " not found");
                });

        List<TourLog> logs = tourLogService.getTourLogsByTourId(tourViewModel.toTour().getId());
        logger.debug("Found " + logs.size() + " logs for tour: " + tour.getName());

        // Popularity = Anzahl der Logs
        int popularity = logs.size();

        double totalScore = 0;

        for (TourLog log : logs) {
            double difficultyScore = switch (log.getDifficulty().toLowerCase()) {
                case "leicht" -> 10;
                case "mittel" -> 5;
                case "schwer" -> 0;
                default -> 5;
            };

            double timeScore;
            double totalTime = log.getTotalTime();
            if (totalTime <= 1) {
                timeScore = 10;
            } else if (totalTime <= 3) {
                timeScore = 8;
            } else if (totalTime <= 5) {
                timeScore = 6;
            } else if (totalTime <= 7) {
                timeScore = 4;
            } else if (totalTime <= 9) {
                timeScore = 2;
            } else {
                timeScore = 0;
            }

            double distanceScore;
            double totalDistance = log.getTotalDistance();
            if (totalDistance <= 2) {
                distanceScore = 10;
            } else if (totalDistance <= 5) {
                distanceScore = 8;
            } else if (totalDistance <= 8) {
                distanceScore = 6;
            } else if (totalDistance <= 11) {
                distanceScore = 4;
            } else if (totalDistance <= 14) {
                distanceScore = 2;
            } else {
                distanceScore = 0;
            }

            double logScore = (difficultyScore + timeScore + distanceScore) / 3.0;
            totalScore += logScore;
        }

        // Durchschnitts-Score als Kinderfreundlichkeitswert
        double childFriendliness = logs.isEmpty() ? 0 : totalScore / logs.size();

        tour.setPopularity(popularity);
        tour.setChildFriendliness(childFriendliness);
        tourViewModel.childFriendlinessProperty().set(childFriendliness);
        tourViewModel.popularityProperty().set(popularity);

        tourRepository.save(tour);
    }

    public String getRouteGeoJson(TourViewModel tour) throws IOException {
        // Koordinaten aus Tour holen
        double startLat = tour.getStartLat();
        double startLon = tour.getStartLon();
        double endLat = tour.getEndLat();
        double endLon = tour.getEndLon();
        if (!tour.hasCoordinates()) {
            startLat = 48.205127181547354;
            startLon = 16.365594863891605;
            endLat   = 48.205127181547354;
            endLon   = 16.36274099349976;
        }


        //get API Key from config
        Properties props = Properties_Config.loadProperties();
        String apiKey = props.getProperty("openrouteservices.api.key");

        //Request an OpenRouteService mit den Koordinaten bauen
        String url = "https://api.openrouteservice.org/v2/directions/driving-car/geojson";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("coordinates", List.of(
                List.of(startLon, startLat),
                List.of(endLon, endLat)
        ));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return response.getBody(); // GeoJSON string
    }


}

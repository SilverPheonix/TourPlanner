package at.technikum.studentmanagementsystem2.service;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import at.technikum.studentmanagementsystem2.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TourService {

    private final TourRepository tourRepository;
    private final TourLogService tourLogService;

    @Autowired
    public TourService(TourRepository tourRepository, TourLogService tourLogService) {
        this.tourRepository = tourRepository;
        this.tourLogService = tourLogService;
    }

    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public Optional<Tour> getTourById(UUID id) {
        return tourRepository.findById(id);
    }

    public Tour createTour(Tour tour) {
        return tourRepository.save(tour);
    }

    public Tour updateTour(Tour updatedTour) {
        return tourRepository.findById(updatedTour.getId()).map(tour -> {
            tour.setName(updatedTour.getName());
            tour.setDescription(updatedTour.getDescription());
            tour.setFrom(updatedTour.getFrom());
            tour.setTo(updatedTour.getTo());
            tour.setTransportType(updatedTour.getTransportType());
            tour.setDistance(updatedTour.getDistance());
            tour.setEstimatedTime(updatedTour.getEstimatedTime());
            tour.setImageUrl(updatedTour.getImageUrl());
            return tourRepository.save(tour);
        }).orElseThrow(() -> new RuntimeException("Tour not found with id: " + updatedTour.getId()));
    }

    public void deleteTour(UUID id) {
        if (!tourRepository.existsById(id)) {
            throw new RuntimeException("Tour not found with id: " + id);
        }
        tourRepository.deleteById(id);
    }

    public void updateComputedAttributes(TourViewModel tourViewModel) {
        Tour tour = tourRepository.findById(tourViewModel.toTour().getId()).orElseThrow();
        List<TourLog> logs = tourLogService.getTourLogsByTourId(tourViewModel.toTour().getId());

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

}

package at.technikum.studentmanagementsystem2.service;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TourService {

    private final TourRepository tourRepository;

    @Autowired
    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
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
}

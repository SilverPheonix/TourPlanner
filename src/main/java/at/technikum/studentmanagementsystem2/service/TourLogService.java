package at.technikum.studentmanagementsystem2.service;

import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.repository.TourLogRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TourLogService {

    private final TourLogRepository tourLogRepository;

    public TourLogService(TourLogRepository repo) {
        this.tourLogRepository = repo;
    }

    public List<TourLog> getTourLogsByTourId(UUID tourId) {
        return tourLogRepository.findAll().stream()
                .filter(log -> log.getTour() != null && log.getTour().getId().equals(tourId))
                .toList();
    }

    public void saveTourLog(TourLog tourLog) {
        tourLogRepository.save(tourLog);
    }

    public void deleteTourLog(UUID logId) {
        tourLogRepository.deleteById(logId);
    }
}

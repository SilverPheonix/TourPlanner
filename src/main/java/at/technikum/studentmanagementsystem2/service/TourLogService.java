package at.technikum.studentmanagementsystem2.service;

import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.repository.TourLogRepository;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.UUID;

@Service
public class TourLogService {

    private final TourLogRepository tourLogRepository;
    private static final Logger logger = LogManager.getLogger(TourLogService.class);

    public TourLogService(TourLogRepository repo) {
        this.tourLogRepository = repo;
    }

    public List<TourLog> getTourLogsByTourId(UUID tourId) {
        logger.info("Fetching logs for tour ID: {}", tourId);
        return tourLogRepository.findAll().stream()
                .filter(log -> log.getTour() != null && log.getTour().getId().equals(tourId))
                .toList();
    }

    public void saveTourLog(TourLog tourLog) {
        logger.info("Saving tour log for tour ID: {}", tourLog.getTour() != null ? tourLog.getTour().getId() : "null");
        tourLogRepository.save(tourLog);
    }

    public void deleteTourLog(UUID logId) {
        logger.info("Deleting tour log with ID: {}", logId);
        tourLogRepository.deleteById(logId);
    }
}

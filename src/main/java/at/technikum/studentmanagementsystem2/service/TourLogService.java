package at.technikum.studentmanagementsystem2.service;

import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.repository.TourLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TourLogService {
    private final TourLogRepository repository;

    public TourLogService(TourLogRepository repository) {
        this.repository = repository;
    }

    public List<TourLog> getTourLogsByTourId(UUID tourId) {
        return repository.findByTourId(tourId);
    }

    public TourLog saveTourLog(TourLog tourLog) {
        return repository.save(tourLog);
    }

    public void deleteTourLog(UUID id) {
        repository.deleteById(id);
    }
}

package at.technikum.studentmanagementsystem2.service;

import at.technikum.studentmanagementsystem2.models.TourLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.UUID;

public class TourLogService {
    private final ObservableList<TourLog> tourLogs = FXCollections.observableArrayList();

    public List<TourLog> getTourLogsByTourId(UUID tourId) {
        return tourLogs.filtered(log -> log.getTourId().equals(tourId));
    }

    public void saveTourLog(TourLog tourLog) {
        tourLogs.removeIf(log -> log.getId().equals(tourLog.getId()));  // Remove old entry if exists
        tourLogs.add(tourLog);
    }

    public void deleteTourLog(UUID logId) {
        tourLogs.removeIf(log -> log.getId().equals(logId));
    }
}

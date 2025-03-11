package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.service.TourLogService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.UUID;

public class TourLogViewModel {
    private final TourLogService service;
    private final ObservableList<TourLog> tourLogs = FXCollections.observableArrayList();

    public TourLogViewModel(TourLogService service) {
        this.service = service;
    }

    /**
     * Lädt alle Tour-Logs für eine bestimmte Tour.
     */
    public void loadTourLogs(UUID tourId) {
        List<TourLog> logs = service.getTourLogsByTourId(tourId);
        tourLogs.setAll(logs);
    }

    /**
     * Gibt die Liste der Tour-Logs zurück (für die UI).
     */
    public ObservableList<TourLog> getTourLogs() {
        return tourLogs;
    }

    /**
     * Erstellt oder aktualisiert ein Tour-Log.
     */
    public void saveTourLog(TourLog tourLog) {
        service.saveTourLog(tourLog);
        loadTourLogs(tourLog.getTourId()); // Aktualisiere die Liste
    }

    /**
     * Löscht ein Tour-Log.
     */
    public void deleteTourLog(TourLog tourLog) {
        service.deleteTourLog(tourLog.getId());
        loadTourLogs(tourLog.getTourId()); // Aktualisiere die Liste
    }
}

package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.TourLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.UUID;

public class TourLogViewModel {
    private final ObservableList<TourLog> tourLogs = FXCollections.observableArrayList();

    public TourLogViewModel() {
        // Optional: Beispiel-Daten zum Testen
        tourLogs.add(new TourLog(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(),"Wunderschöne Tour", "medium",200,14,4));
    }

    /**
     * Gibt die Liste der Tour-Logs zurück (für die UI).
     */
    public ObservableList<TourLog> getTourLogs() {
        return tourLogs;
    }

    /**
     * Fügt ein neues Tour-Log hinzu.
     */
    public void addTourLog(TourLog tourLog) {
        tourLogs.add(tourLog);
    }

    /**
     * Aktualisiert ein vorhandenes Tour-Log.
     */
    public void updateTourLog(TourLog updatedTourLog) {
        for (int i = 0; i < tourLogs.size(); i++) {
            if (tourLogs.get(i).getId().equals(updatedTourLog.getId())) {
                tourLogs.set(i, updatedTourLog);
                break;
            }
        }
    }

    /**
     * Löscht ein Tour-Log.
     */
    public void deleteTourLog(TourLog tourLog) {
        tourLogs.remove(tourLog);
    }
}

package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.TourLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TourLogTableViewModel {
    private final ObservableList<TourLogViewModel> tourLogs = FXCollections.observableArrayList();

    public TourLogTableViewModel() {
        /* Beispiel-Daten (zum Testen)
        tourLogs.add(new TourLogViewModel(new TourLog(
                UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(),
                "Wunderschöne Tour", "medium", 200, 14, 4
        )));*/
    }

    /**
     * Gibt die Liste der Tour-Logs zurück (für die UI).
     */
    public ObservableList<TourLogViewModel> getTourLogs() {
        return tourLogs;
    }

    /**
     * Fügt ein neues Tour-Log hinzu.
     */
    public void addTourLog(TourLogViewModel tourLog) {
        tourLogs.add(tourLog);
    }

    /**
     * Aktualisiert ein vorhandenes Tour-Log.
     */
    public void updateTourLog(TourLogViewModel updatedTourLog) {
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
    public void deleteTourLog(TourLogViewModel tourLog) {
        tourLogs.remove(tourLog);
    }
}

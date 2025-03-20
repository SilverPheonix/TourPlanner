package at.technikum.studentmanagementsystem2.controller;

import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.mvvm.TourLogTableViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourLogViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class TourLogController {
    @FXML private TableView<TourLogViewModel> logTable;
    @FXML private TableColumn<TourLogViewModel, LocalDateTime> colDate;
    @FXML private TableColumn<TourLogViewModel, String> colComment;
    @FXML private TableColumn<TourLogViewModel, String> colDifficulty;
    @FXML private TableColumn<TourLogViewModel, Double> colDistance;
    @FXML private TableColumn<TourLogViewModel, Double> colTime;
    @FXML private TableColumn<TourLogViewModel, Integer> colRating;
    @FXML private Button btnAdd, btnEdit, btnDelete;

    private TourLogTableViewModel tourLogViewModel;
    private TourViewModel currentTour;

    public TourLogController() {
        System.out.println("[TourLogController] Constructor called.");
    }

    @FXML
    public void initialize() {
        setupTableColumns();
        setupButtonActions();
    }

    /**
     * Initialisiert die Spalten der Tabelle mit den passenden Eigenschaften.
     */
    private void setupTableColumns() {
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        colComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        colDifficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        colDistance.setCellValueFactory(new PropertyValueFactory<>("totalDistance"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
    }

    /**
     * Setzt die Button-Events.
     */
    private void setupButtonActions() {
        btnAdd.setOnAction(e -> addTourLog());
        btnEdit.setOnAction(e -> editTourLog());
        btnDelete.setOnAction(e -> deleteTourLog());
    }

    /**
     * Setzt das ViewModel für die Tour-Logs.
     * @param tourLogViewModel Das ViewModel für die Tour-Logs.
     */
    public void setViewModel(TourLogTableViewModel tourLogViewModel) {
        if (tourLogViewModel == null) {
            System.err.println("⚠ TourLogTableViewModel ist null!");
            return;
        }
        this.tourLogViewModel = tourLogViewModel;
        logTable.setItems(tourLogViewModel.getTourLogs());
    }

    /**
     * Setzt die aktuelle Tour und lädt die zugehörigen Logs.
     * @param tour Die aktuell ausgewählte Tour.
     */
    public void setTour(TourViewModel tour) {
        if (tour == null) {
            System.err.println("⚠ Keine Tour ausgewählt!");
            return;
        }
        this.currentTour = tour;
        ObservableList<TourLogViewModel> logs = FXCollections.observableArrayList();
        for (TourLog log : tour.getTourLogs()) {
            logs.add(new TourLogViewModel(log));
        }
        tourLogViewModel.getTourLogs().setAll(logs);    }

    /**
     * Erstellt ein neues Tour-Log für die aktuelle Tour.
     */
    private void addTourLog() {
        if (!ensureTourSelected()) return;

        TourLog newLog = new TourLog(
                UUID.randomUUID(),
                UUID.fromString(currentTour.getId()),
                LocalDateTime.now(),
                "Neue Tour-Log",
                "Einfach",
                10.0,
                60.0,
                5
        );

        tourLogViewModel.addTourLog(new TourLogViewModel(newLog));
        currentTour.getTourLogs().add(newLog);
    }

    /**
     * Bearbeitet das aktuell ausgewählte Tour-Log.
     */
    private void editTourLog() {
        if (!ensureTourSelected()) return;

        TourLogViewModel selectedLog = logTable.getSelectionModel().getSelectedItem();
        if (selectedLog != null) {
            selectedLog.setComment("Bearbeitetes Log");
            tourLogViewModel.updateTourLog(selectedLog);
            logTable.refresh();  // UI aktualisieren
        } else {
            System.err.println("⚠ Kein Tour-Log ausgewählt!");
        }
    }

    /**
     * Löscht das aktuell ausgewählte Tour-Log.
     */
    private void deleteTourLog() {
        if (!ensureTourSelected()) return;

        TourLogViewModel selectedLog = logTable.getSelectionModel().getSelectedItem();
        if (selectedLog != null) {
            tourLogViewModel.deleteTourLog(selectedLog);
            currentTour.getTourLogs().remove(selectedLog);
        } else {
            System.err.println("⚠ Kein Tour-Log ausgewählt!");
        }
    }

    /**
     * Prüft, ob eine Tour ausgewählt ist, und gibt eine Warnung aus, falls nicht.
     */
    private boolean ensureTourSelected() {
        if (currentTour == null) {
            System.err.println("⚠ Keine Tour ausgewählt!");
            return false;
        }
        return true;
    }
}

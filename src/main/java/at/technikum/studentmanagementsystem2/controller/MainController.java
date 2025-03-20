package at.technikum.studentmanagementsystem2.controller;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.mvvm.TourLogViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourTableViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourLogTableViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class MainController {

    @FXML private ListView<TourViewModel> tourListView;
    @FXML private TextField tourNameField, tourDescriptionField, tourFromField, tourToField, tourTransportField;
    @FXML private TableView<TourLogViewModel> tourLogTable;
    @FXML private TableColumn<TourLogViewModel, LocalDateTime> logDateColumn;
    @FXML private TableColumn<TourLogViewModel, String> logCommentColumn;
    @FXML private TableColumn<TourLogViewModel, String> logDifficultyColumn;
    @FXML private TableColumn<TourLogViewModel, Double> logDistanceColumn;
    @FXML private TableColumn<TourLogViewModel, Double> logTimeColumn;
    @FXML private TableColumn<TourLogViewModel, Integer> logRatingColumn;


    private TourTableViewModel tourTableViewModel = new TourTableViewModel();
    private TourLogTableViewModel tourLogViewModel = new TourLogTableViewModel();

    public void setViewModels(TourTableViewModel tourTableViewModel, TourLogTableViewModel tourLogTableViewModel) {
        this.tourTableViewModel = tourTableViewModel;
        this.tourLogViewModel = tourLogTableViewModel;
    }

    @FXML
    public void initialize() {
        // Lade Tour-Liste
        tourListView.setItems(tourTableViewModel.getTours());

        // Event: Wenn eine Tour ausgewählt wird
        tourListView.getSelectionModel().selectedItemProperty().addListener((obs, oldTour, newTour) -> {
            if (newTour != null) {
                showTourDetails(newTour);
            }
        });

        // Definiere die Zelle, die für die Anzeige verwendet wird
        tourListView.setCellFactory(param -> new ListCell<TourViewModel>() {
            @Override
            protected void updateItem(TourViewModel item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getName() + " (ID: " + item.getId() + ")");
                }
            }
        });

        // Lade Log-Tabelle
        logDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateTimeProperty());
        logCommentColumn.setCellValueFactory(cellData -> cellData.getValue().commentProperty());
        logDifficultyColumn.setCellValueFactory(cellData -> cellData.getValue().difficultyProperty());
        logDistanceColumn.setCellValueFactory(cellData -> cellData.getValue().totalDistanceProperty().asObject());
        logTimeColumn.setCellValueFactory(cellData -> cellData.getValue().totalTimeProperty().asObject());
        logRatingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());
        tourLogTable.setItems(tourLogViewModel.getTourLogs());
    }

    // 📌 Tour-Details anzeigen
    private void showTourDetails(TourViewModel tour) {
        tourNameField.setText(tour.getName());
        tourDescriptionField.setText(tour.getDescription());
        tourFromField.setText(tour.getFrom());
        tourToField.setText(tour.getTo());
        tourTransportField.setText(tour.getTransportType());

        // Lade die Logs für diese Tour
        ObservableList<TourLogViewModel> logs = FXCollections.observableArrayList();
        for (TourLog log : tour.getTourLogs()) {
            logs.add(new TourLogViewModel(log));
        }
        tourLogViewModel.getTourLogs().setAll(logs);

    }

    @FXML
    private void onTourSelected() {
        TourViewModel selectedTour = tourListView.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            showTourDetails(selectedTour);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // 📌 Aktionen für Touren
    @FXML
    private void onNewTour() {
        Tour newTour = new Tour(
                UUID.randomUUID(), "Neue Tour", "Beschreibung der Tour",
                "Startort", "Zielort", "Auto", 100.0, 60.0, "image_url"
        );
        TourViewModel newTourVM = new TourViewModel(newTour);
        tourTableViewModel.addTour(newTourVM);
        tourListView.setItems(tourTableViewModel.getTours());
    }

    @FXML
    private void onEditTour() {
        TourViewModel selectedTour = tourListView.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            selectedTour.nameProperty().set(tourNameField.getText());
            selectedTour.descriptionProperty().set(tourDescriptionField.getText());
            selectedTour.fromProperty().set(tourFromField.getText());
            selectedTour.toProperty().set(tourToField.getText());
            selectedTour.transportTypeProperty().set(tourTransportField.getText());
            // Nach der Bearbeitung die Liste aktualisieren
            tourListView.setItems(tourTableViewModel.getTours());
        } else {
            showAlert("Fehler", "Keine Tour ausgewählt!", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void onDeleteTour() {
        TourViewModel selectedTour = tourListView.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            tourTableViewModel.deleteTour(selectedTour);
        } else {
            showAlert("Fehler", "Keine Tour ausgewählt!", Alert.AlertType.WARNING);
        }
        // Nach dem Löschen die Liste aktualisieren
        tourListView.setItems(tourTableViewModel.getTours());
    }

    // 📌 Aktionen für Tour-Logs
    @FXML
    private void onNewLog() {
        TourViewModel selectedTour = tourListView.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            TourLog newLog = new TourLog(
                    UUID.randomUUID(), UUID.fromString(selectedTour.getId()),
                    LocalDateTime.now(), "Neuer Log-Eintrag", "Mittel", 50, 5, 3
            );
            TourLogViewModel newLogVM = new TourLogViewModel(newLog);
            tourLogViewModel.addTourLog(newLogVM);
            tourLogTable.setItems(tourLogViewModel.getTourLogs());

        } else {
            showAlert("Fehler", "Keine Tour ausgewählt!", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void onEditLog() {
        TourLogViewModel selectedLog = tourLogTable.getSelectionModel().getSelectedItem();
        if (selectedLog != null) {
            selectedLog.commentProperty().set("Bearbeiteter Kommentar");
            selectedLog.difficultyProperty().set("Schwer");
            selectedLog.ratingProperty().set(5);
            // Nach der Bearbeitung die Liste aktualisieren
            tourLogTable.setItems(tourLogViewModel.getTourLogs());
        } else {
            showAlert("Fehler", "Kein Tour-Log ausgewählt!", Alert.AlertType.WARNING);
        }

    }

    @FXML
    private void onDeleteLog() {
        TourLogViewModel selectedLog = tourLogTable.getSelectionModel().getSelectedItem();
        if (selectedLog != null) {
            tourLogViewModel.deleteTourLog(selectedLog);
            // Nach dem Löschen die Liste aktualisieren
            tourLogTable.setItems(tourLogViewModel.getTourLogs());
        } else {
            showAlert("Fehler", "Kein Tour-Log ausgewählt!", Alert.AlertType.WARNING);
        }
    }

}

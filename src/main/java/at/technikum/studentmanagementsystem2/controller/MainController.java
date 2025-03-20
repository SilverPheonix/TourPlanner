package at.technikum.studentmanagementsystem2.controller;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.mvvm.TourLogViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourTableViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourLogTableViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class MainController {

    @FXML private ListView<TourViewModel> tourListView;
    @FXML private TextField tourNameField, tourDescriptionField, tourFromField, tourToField, tourTransportField, tourDistanceField, tourEstimatedtimeField, tourImageField;
    @FXML private TableView<TourLogViewModel> tourLogTable;
    @FXML private TableColumn<TourLogViewModel, LocalDateTime> logDateColumn;
    @FXML private TableColumn<TourLogViewModel, String> logCommentColumn;
    @FXML private TableColumn<TourLogViewModel, String> logDifficultyColumn;
    @FXML private TableColumn<TourLogViewModel, Double> logDistanceColumn;
    @FXML private TableColumn<TourLogViewModel, Double> logTimeColumn;
    @FXML private TableColumn<TourLogViewModel, Integer> logRatingColumn;
    @FXML
    private ImageView tourImageView;

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
        //Lade Map Placeholder
        URL imageUrl = getClass().getResource("/at/technikum/studentmanagementsystem2/map_placeholder.jpg");
        if (imageUrl == null) {
            System.out.println("Bild nicht gefunden!");
        } else {
            Image image = new Image(imageUrl.toExternalForm());
            tourImageView.setImage(image);
        }


        // Lade Log-Tabelle
        logDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateTimeProperty());
        logCommentColumn.setCellValueFactory(cellData -> cellData.getValue().commentProperty());
        logDifficultyColumn.setCellValueFactory(cellData -> cellData.getValue().difficultyProperty());
        logDistanceColumn.setCellValueFactory(cellData -> cellData.getValue().totalDistanceProperty().asObject());
        logTimeColumn.setCellValueFactory(cellData -> cellData.getValue().totalTimeProperty().asObject());
        logRatingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());
        tourLogTable.setItems(tourLogViewModel.getTourLogs());
    }

    public void setImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            tourImageView.setImage(new Image(imageUrl));
        }
    }

    // 📌 Tour-Details anzeigen
    private void showTourDetails(TourViewModel tour) {
        tourNameField.setText(tour.getName());
        tourDescriptionField.setText(tour.getDescription());
        tourFromField.setText(tour.getFrom());
        tourToField.setText(tour.getTo());
        tourTransportField.setText(tour.getTransportType());
        tourDistanceField.setText(tour.distanceProperty().asString().getValue());
        tourEstimatedtimeField.setText(tour.estimatedTimeProperty().asString().getValue());
        tourImageField.setText(tour.imageUrlProperty().getValue());

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
        // Prüfen, ob eine Tour ausgewählt wurde
        TourViewModel selectedTour = tourListView.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            // Holen der Eingabewerte
            String name = tourNameField.getText().trim();
            String description = tourDescriptionField.getText().trim();
            String from = tourFromField.getText().trim();
            String to = tourToField.getText().trim();
            String transportType = tourTransportField.getText().trim();
            String distanceText = tourDistanceField.getText().trim();
            String estimatedTimeText = tourEstimatedtimeField.getText().trim();
            String image = tourImageField.getText().trim();

            // Validierung der Eingabewerte
            if (name.isEmpty() || description.isEmpty() || from.isEmpty() || to.isEmpty() || transportType.isEmpty()) {
                showAlert("Fehler", "Alle Felder müssen ausgefüllt sein!", Alert.AlertType.WARNING);
                return; // Wenn ein Feld leer ist, wird der Vorgang abgebrochen
            }

            double distance;
            double estimatedTime;

            try {
                distance = Double.parseDouble(distanceText);
            } catch (NumberFormatException e) {
                showAlert("Fehler", "Ungültiger Wert für die Entfernung!", Alert.AlertType.WARNING);
                return;
            }

            try {
                estimatedTime = Double.parseDouble(estimatedTimeText);
            } catch (NumberFormatException e) {
                showAlert("Fehler", "Ungültiger Wert für die geschätzte Zeit!", Alert.AlertType.WARNING);
                return;
            }

            // Aktualisiere die ausgewählte Tour
            selectedTour.nameProperty().set(name);
            selectedTour.descriptionProperty().set(description);
            selectedTour.fromProperty().set(from);
            selectedTour.toProperty().set(to);
            selectedTour.transportTypeProperty().set(transportType);
            selectedTour.imageUrlProperty().set(image);
            selectedTour.distanceProperty().set(distance);
            selectedTour.estimatedTimeProperty().set(estimatedTime);

            // Nach der Bearbeitung die Liste aktualisieren
            tourListView.setItems(tourTableViewModel.getTours());
            // Liste neu zeichnen
            tourListView.refresh();

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
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/technikum/studentmanagementsystem2/TourLogDialog.fxml"));
                Parent root = loader.load();

                TourLogDialogController controller = loader.getController();
                controller.setTitle("Neues Tour-Log");
                controller.setValues("", "Mittel", 0.0, 0.0, 3); // Standardwerte setzen

                Stage stage = new Stage();
                stage.setTitle("Neues Tour-Log");
                stage.setScene(new Scene(root, 300,400));
                stage.showAndWait();

                if (controller.isSaved()) {
                    TourLog newLog = new TourLog(
                            UUID.randomUUID(), UUID.fromString(selectedTour.getId()),
                            LocalDateTime.now(),
                            controller.getComment(),
                            controller.getDifficulty(),
                            controller.getDistance(),
                            controller.getTime(),
                            controller.getRating()
                    );

                    TourLogViewModel newLogVM = new TourLogViewModel(newLog);
                    tourLogViewModel.addTourLog(newLogVM);
                    selectedTour.getTourLogs().add(newLog);
                    tourLogTable.setItems(tourLogViewModel.getTourLogs());
                    tourLogTable.refresh();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Fehler", "Keine Tour ausgewählt!", Alert.AlertType.WARNING);
        }
    }




    @FXML
    private void onEditLog() {
        TourLogViewModel selectedLog = tourLogTable.getSelectionModel().getSelectedItem();
        if (selectedLog != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/technikum/studentmanagementsystem2/TourLogDialog.fxml"));
                Parent root = loader.load();

                TourLogDialogController controller = loader.getController();
                controller.setTitle("Tour-Log bearbeiten");
                controller.setValues(selectedLog.getComment(), selectedLog.getDifficulty(), selectedLog.getTotalDistance(), selectedLog.getTotalTime(), selectedLog.getRating());

                Stage stage = new Stage();
                stage.setTitle("Tour-Log bearbeiten");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                if (controller.isSaved()) {
                    selectedLog.setComment(controller.getComment());
                    selectedLog.setDifficulty(controller.getDifficulty());
                    selectedLog.setTotalDistance(controller.getDistance());
                    selectedLog.setTotalTime(controller.getTime());
                    selectedLog.setRating(controller.getRating());
                    tourLogTable.refresh();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Fehler", "Kein Tour-Log ausgewählt!", Alert.AlertType.WARNING);
        }
    }


    @FXML
    private void onDeleteLog() {
        TourLogViewModel selectedLog = tourLogTable.getSelectionModel().getSelectedItem();
        if (selectedLog != null) {
            // Log aus dem ViewModel löschen
            tourLogViewModel.deleteTourLog(selectedLog);

            // Log aus der zugrunde liegenden Tour löschen
            TourViewModel selectedTour = tourListView.getSelectionModel().getSelectedItem();
            if (selectedTour != null) {
                selectedTour.getTourLogs().removeIf(log -> log.getId().equals(selectedLog.getId()));
            }
            // Nach dem Löschen die Liste aktualisieren
            tourLogTable.setItems(tourLogViewModel.getTourLogs());
        } else {
            showAlert("Fehler", "Kein Tour-Log ausgewählt!", Alert.AlertType.WARNING);
        }
    }


}

package at.technikum.studentmanagementsystem2.controller;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.mvvm.TourLogViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourTableViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourLogTableViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import at.technikum.studentmanagementsystem2.controller.TourEditDialogController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.converter.NumberStringConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Component
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
    }

    public void setImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            tourImageView.setImage(new Image(imageUrl));
        }
    }

    private void showTourDetails(TourViewModel tour) {
        // Setze die Felder mit Bindungen für die Zwei-Wege-Datenbindung
        tourNameField.textProperty().bind(tour.nameProperty());
        tourDescriptionField.textProperty().bind(tour.descriptionProperty());
        tourFromField.textProperty().bind(tour.fromProperty());
        tourToField.textProperty().bind(tour.toProperty());
        tourTransportField.textProperty().bind(tour.transportTypeProperty());
        tourDistanceField.textProperty().bindBidirectional(tour.distanceProperty(), new NumberStringConverter());
        tourEstimatedtimeField.textProperty().bindBidirectional(tour.estimatedTimeProperty(), new NumberStringConverter());
        tourImageField.textProperty().bind(tour.imageUrlProperty());

        // Deaktiviere die Textfelder für den Lesemodus
        tourNameField.setEditable(false);
        tourDescriptionField.setEditable(false);
        tourFromField.setEditable(false);
        tourToField.setEditable(false);
        tourTransportField.setEditable(false);
        tourDistanceField.setEditable(false);
        tourEstimatedtimeField.setEditable(false);
        tourImageField.setEditable(false);


        // Lade Map-Bild
        URL imageUrl = getClass().getResource("/at/technikum/studentmanagementsystem2/map_placeholder.jpg");
        if (imageUrl != null) {
            Image placeholderImage = new Image(imageUrl.toExternalForm());
            tourImageView.setImage(placeholderImage);
        }

        // Lade die Log-Tabelle mit den Logs der ausgewählten Tour
        ObservableList<TourLogViewModel> logs = FXCollections.observableArrayList();
        for (TourLog log : tour.getTourLogs()) {
            logs.add(new TourLogViewModel(log));
        }
        tourLogViewModel.getTourLogs().setAll(logs);
        tourLogTable.setItems(tourLogViewModel.getTourLogs());

        // Hier binden wir auch die Spalten an die entsprechenden Eigenschaften der TourLogViewModels
        logDateColumn.setCellValueFactory(cellData -> cellData.getValue().dateTimeProperty());
        logCommentColumn.setCellValueFactory(cellData -> cellData.getValue().commentProperty());
        logDifficultyColumn.setCellValueFactory(cellData -> cellData.getValue().difficultyProperty());
        logDistanceColumn.setCellValueFactory(cellData -> cellData.getValue().totalDistanceProperty().asObject());
        logTimeColumn.setCellValueFactory(cellData -> cellData.getValue().totalTimeProperty().asObject());
        logRatingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());
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

    // Aktionen für Touren
    @FXML
    private void onNewTour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/technikum/studentmanagementsystem2/TourDialog.fxml"));
            Parent root = loader.load();

            Tour newTour = new Tour(UUID.randomUUID(), "", "", "", "", "", 0.0, 0.0, "");
            TourViewModel newTourVM = new TourViewModel(newTour);

            TourEditDialogController controller = loader.getController();
            controller.init(newTourVM);
            controller.setTitle("Neue Tour erstellen");

            Stage stage = new Stage();
            stage.setTitle("Neue Tour");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            if (controller.isSaved()) {
                tourTableViewModel.addTour(newTourVM);
                tourListView.setItems(tourTableViewModel.getTours());
                tourListView.refresh();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onEditTour() {
        TourViewModel selectedTour = tourListView.getSelectionModel().getSelectedItem();

        if (selectedTour == null) {
            // Keine Auswahl getroffen, ggf. Warnung anzeigen
            Alert alert = new Alert(Alert.AlertType.WARNING, "Bitte wählen Sie eine Tour zum Bearbeiten aus.");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/technikum/studentmanagementsystem2/TourDialog.fxml"));
            Parent editDialog = loader.load();

            TourEditDialogController controller = loader.getController();
            controller.init(selectedTour);  // Übergibt die ausgewählte Tour

            Stage stage = new Stage();
            stage.setTitle("Tour bearbeiten");
            stage.setScene(new Scene(editDialog));
            stage.initModality(Modality.APPLICATION_MODAL);  // Blockiert, bis Dialog geschlossen wird
            stage.showAndWait();

            // Option: Daten im TableView neu anzeigen
            tourListView.refresh();

        } catch (IOException e) {
            e.printStackTrace();
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

    // Aktionen für Tour-Logs
    @FXML
    private void onNewLog() {
        TourViewModel selectedTour = tourListView.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/technikum/studentmanagementsystem2/TourLogDialog.fxml"));
                Parent root = loader.load();

                // Create a dummy Tour object
                Tour dummyTour = new Tour();
                dummyTour.setId(UUID.fromString(selectedTour.getId()));

                // Create a dummy TourLog object with the Tour reference
                TourLog dummyLog = new TourLog(
                        UUID.randomUUID(),
                        dummyTour,
                        LocalDateTime.now(),
                        "",         // comment
                        "Mittel",   // difficulty
                        0.0,        // distance
                        0.0,        // time
                        3           // rating
                );

                TourLogViewModel newLogViewModel = new TourLogViewModel(dummyLog);

                TourLogDialogController controller = loader.getController();
                controller.setTitle("Neues Tour-Log");
                controller.setValues(newLogViewModel); // Set default values

                Stage stage = new Stage();
                stage.setTitle("Neues Tour-Log");
                stage.setScene(new Scene(root, 300, 400));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

                if (controller.isSaved()) {
                    // Extract the final TourLog from the ViewModel
                    TourLog finalLog = new TourLog(
                            newLogViewModel.getId(),
                            dummyTour,
                            LocalDateTime.now(),
                            newLogViewModel.getComment(),
                            newLogViewModel.getDifficulty(),
                            newLogViewModel.getTotalDistance(),
                            newLogViewModel.getTotalTime(),
                            newLogViewModel.getRating()
                    );

                    // Add the new log to the table and model
                    TourLogViewModel finalVM = new TourLogViewModel(finalLog);
                    tourLogViewModel.addTourLog(finalVM);
                    selectedTour.getTourLogs().add(finalLog);
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
                controller.setValues(selectedLog);

                Stage stage = new Stage();
                stage.setTitle("Tour-Log bearbeiten");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
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

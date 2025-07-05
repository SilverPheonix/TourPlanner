package at.technikum.studentmanagementsystem2.controller;

import at.technikum.studentmanagementsystem2.helpers.AlertHelper;
import at.technikum.studentmanagementsystem2.helpers.JavaBridge;
import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.mvvm.TourLogViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourTableViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourLogTableViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import at.technikum.studentmanagementsystem2.service.TourJsonService;
import at.technikum.studentmanagementsystem2.service.TourLogService;
import at.technikum.studentmanagementsystem2.service.TourReportService;
import at.technikum.studentmanagementsystem2.service.TourService;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class MainController {

    @Autowired private TourService tourService;
    @Autowired private TourLogService tourLogService;

    @FXML private ListView<TourViewModel> tourListView;
    @FXML private Label tourNameField, tourDescriptionField, tourFromField, tourToField, tourTransportField,
            tourDistanceField, tourEstimatedtimeField, tourPopularityLabel, tourChildFriendlinessLabel;

    @FXML private TableView<TourLogViewModel> tourLogTable;
    @FXML private TableColumn<TourLogViewModel, LocalDateTime> logDateColumn;
    @FXML private TableColumn<TourLogViewModel, String> logCommentColumn;
    @FXML private TableColumn<TourLogViewModel, String> logDifficultyColumn;
    @FXML private TableColumn<TourLogViewModel, Double> logDistanceColumn;
    @FXML private TableColumn<TourLogViewModel, Double> logTimeColumn;
    @FXML private TableColumn<TourLogViewModel, Integer> logRatingColumn;
    @FXML private ImageView tourMapImageView;

    private TourTableViewModel tourTableViewModel = new TourTableViewModel();
    private TourLogTableViewModel tourLogViewModel = new TourLogTableViewModel();



    public void setViewModels(TourTableViewModel tourTableViewModel, TourLogTableViewModel tourLogTableViewModel) {
        this.tourTableViewModel = tourTableViewModel;
        this.tourLogViewModel = tourLogTableViewModel;
    }

    @FXML
    public void initialize() {
        // Lade Touren aus Service
        List<Tour> tours = tourService.getAllTours();
        List<TourViewModel> viewModels = tours.stream()
                .map(TourViewModel::new)
                .toList();
        tourTableViewModel.getTours().setAll(viewModels);
        tourListView.setItems(tourTableViewModel.getTours());

        // Listener
        tourListView.getSelectionModel().selectedItemProperty().addListener((obs, oldTour, newTour) -> {
            if (newTour != null) {
                showTourDetails(newTour);
            }
        });

        // Cell Factory
        tourListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(TourViewModel item, boolean empty) {
                super.updateItem(item, empty);
                setText((item == null || empty) ? null : item.getName() + " (ID: " + item.getId() + ")");
            }
        });
    }

    private void showTourDetails(TourViewModel tour) {
        // Setze die Felder mit Bindungen für die Zwei-Wege-Datenbindung
        tourNameField.textProperty().bind(tour.nameProperty());
        tourDescriptionField.textProperty().bind(tour.descriptionProperty());
        tourFromField.textProperty().bind(tour.fromProperty());
        tourToField.textProperty().bind(tour.toProperty());
        tourTransportField.textProperty().bind(tour.transportTypeProperty());
        tourDistanceField.textProperty().bind(tour.distanceProperty().asString());
        tourEstimatedtimeField.textProperty().bind(tour.estimatedTimeProperty().asString());

        tourPopularityLabel.textProperty().bind(tour.popularityProperty().asString());
        tourChildFriendlinessLabel.textProperty().bind(tour.childFriendlinessProperty().asString());


        // ---- Leaflet Map laden ----
        String imagePath = tour.getImageUrl();
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(new File(imagePath).toURI().toString());
            tourMapImageView.setImage(image);
            tourMapImageView.setVisible(true);
        }


        // Lade die Log-Tabelle mit den Logs der ausgewählten Tour
        List<TourLog> logs = tourLogService.getTourLogsByTourId(UUID.fromString(tour.getId()));
        List<TourLogViewModel> logVMs = logs.stream().map(TourLogViewModel::new).toList();
        tourLogViewModel.getTourLogs().setAll(logVMs);
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


    // Aktionen für Touren
    @FXML
    private void onNewTour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/technikum/studentmanagementsystem2/TourDialog.fxml"));
            Parent root = loader.load();

            Tour newTour = new Tour(UUID.randomUUID(), "", "", "", "", "", 0.0, 0.0, "");
            TourViewModel newTourVM = new TourViewModel(newTour);

            TourEditDialogController controller = loader.getController();
            controller.setTourService(tourService);
            controller.init(newTourVM);
            controller.setTitle("Neue Tour erstellen");

            Stage stage = new Stage();
            stage.setTitle("Neue Tour");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            if (controller.isSaved()) {
                Tour savedTour = newTourVM.toTour();

                tourService.createTour(savedTour);
                tourTableViewModel.addTour(new TourViewModel(savedTour));
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
            AlertHelper.showAlert("Fehler", "Bitte wählen Sie eine Tour zum Bearbeiten aus.", Alert.AlertType.WARNING);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/technikum/studentmanagementsystem2/TourDialog.fxml"));
            Parent editDialog = loader.load();

            TourEditDialogController controller = loader.getController();
            controller.setTourService(tourService);
            controller.init(selectedTour);  // Übergibt die ausgewählte Tour

            Stage stage = new Stage();
            stage.setTitle("Tour bearbeiten");
            stage.setScene(new Scene(editDialog));
            stage.initModality(Modality.APPLICATION_MODAL);  // Blockiert, bis Dialog geschlossen wird
            stage.showAndWait();

            if (controller.isSaved()) {
                Tour updatedTour = selectedTour.toTour();
                tourService.updateTour(updatedTour); // Persistieren der Änderung
                tourListView.refresh();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onDeleteTour() {
        TourViewModel selectedTour = tourListView.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            tourService.deleteTour(selectedTour.toTour().getId());
            tourTableViewModel.deleteTour(selectedTour);
            tourListView.refresh();
        } else {
            AlertHelper.showAlert("Fehler", "Keine Tour ausgewählt!", Alert.AlertType.WARNING);
        }
    }

    // Aktionen für Tour-Logs
    @FXML
    private void onNewLog() {
        TourViewModel selectedTour = tourListView.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/technikum/studentmanagementsystem2/TourLogDialog.fxml"));
                Parent root = loader.load();

                // Create a dummy Tour log object
                TourLog dummyLog = new TourLog(
                        UUID.randomUUID(),
                        selectedTour.toTour(),
                        LocalDateTime.now(), "", "Mittel", 0.0, 0.0, 3
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
                    TourLog savedLog = newLogViewModel.toTourLog(selectedTour.toTour());
                    tourLogService.saveTourLog(savedLog);
                    tourLogViewModel.addTourLog(new TourLogViewModel(savedLog));
                    tourService.updateComputedAttributes(selectedTour);
                    tourLogTable.refresh();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            AlertHelper.showAlert("Fehler", "Keine Tour ausgewählt!", Alert.AlertType.WARNING);
        }
    }




    @FXML
    private void onEditLog() {
        TourLogViewModel selectedLog = tourLogTable.getSelectionModel().getSelectedItem();
        TourViewModel tour = tourListView.getSelectionModel().getSelectedItem();

        if (selectedLog != null ) {
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
                    // ViewModel updaten
                    selectedLog.setComment(controller.getComment());
                    selectedLog.setDifficulty(controller.getDifficulty());
                    selectedLog.setTotalDistance(controller.getDistance());
                    selectedLog.setTotalTime(controller.getTime());
                    selectedLog.setRating(controller.getRating());

                    // Persistieren im Service
                    tourLogService.saveTourLog(selectedLog.toTourLog(tour.toTour()));

                    tourService.updateComputedAttributes(tour);
                    tourListView.refresh();
                    tourLogTable.refresh();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            AlertHelper.showAlert("Fehler", "Kein Tour-Log ausgewählt!", Alert.AlertType.WARNING);
        }
    }


    @FXML
    private void onDeleteLog() {
        TourLogViewModel selectedLog = tourLogTable.getSelectionModel().getSelectedItem();
        TourViewModel selectedTour = tourListView.getSelectionModel().getSelectedItem();

        if (selectedLog != null) {
            tourLogViewModel.deleteTourLog(selectedLog);
            selectedTour.getTourLogs().removeIf(log -> log.getId().equals(selectedLog.getId()));
            tourLogService.deleteTourLog(selectedLog.toTourLog(selectedTour.toTour()).getId());

            tourLogTable.refresh();
        } else {
            AlertHelper.showAlert("Fehler", "Kein Tour-Log ausgewählt!", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void onReport(){
        TourViewModel selectedTour = tourListView.getSelectionModel().getSelectedItem();

        if (selectedTour == null) {
            AlertHelper.showAlert("Fehler", "Bitte wählen Sie eine Tour zum Erstellen eines Reports aus.", Alert.AlertType.WARNING);
            return;
        }
        List<TourLog> logs = tourLogService.getTourLogsByTourId(selectedTour.toTour().getId());

        // Erstelle den Report
        TourReportService.exportTourToPDF(selectedTour.toTour(), logs);
    }

    @FXML
    private void onSummaryReport() {
        List<Tour> allTours = tourService.getAllTours();

        if (allTours.isEmpty()) {
            AlertHelper.showAlert("Keine Touren", "Es sind keine Touren vorhanden.", Alert.AlertType.INFORMATION);
            return;
        }

        TourReportService.exportSummarizeReportToPDF(allTours, tourLogService);

        AlertHelper.showAlert("Erfolg", "Zusammenfassender Report erfolgreich erstellt!", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void onExportTourJson() {
        TourViewModel selectedTourVM = tourListView.getSelectionModel().getSelectedItem();

        if (selectedTourVM == null) {
            AlertHelper.showAlert("Fehler", "Bitte wählen Sie eine Tour zum Exportieren aus.", Alert.AlertType.WARNING);
            return;
        }
        try {
            List<TourLog> logs = tourLogService.getTourLogsByTourId(selectedTourVM.toTour().getId());

            // JSON-Export ohne Pfadübergabe
            TourJsonService.exportTour(selectedTourVM.toTour(), logs);

            AlertHelper.showAlert("Erfolg", "Tour wurde erfolgreich exportiert.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            e.printStackTrace();
            AlertHelper.showAlert("Fehler", "Fehler beim Exportieren: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


}

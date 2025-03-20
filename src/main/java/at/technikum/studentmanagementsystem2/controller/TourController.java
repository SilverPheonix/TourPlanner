package at.technikum.studentmanagementsystem2.controller;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.mvvm.TourLogTableViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourTableViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.UUID;

public class TourController {
    @FXML private TableView<TourViewModel> tourTable;
    @FXML private TableColumn<TourViewModel, String> colName;
    @FXML private TableColumn<TourViewModel, String> colDescription;
    @FXML private TableColumn<TourViewModel, String> colFrom;
    @FXML private TableColumn<TourViewModel, String> colTo;
    @FXML private TableColumn<TourViewModel, String> colTransportType;
    @FXML private TableColumn<TourViewModel, Double> colDistance;
    @FXML private TableColumn<TourViewModel, Double> colEstimatedTime;
    @FXML private Button btnAdd, btnEdit, btnDelete;

    private TourTableViewModel viewModel;
    private boolean isViewModelSet = false; // Track if setViewModel() was called

    public TourController() {
        System.out.println("[TourController] Constructor called.");
    }

    public void setViewModel(TourTableViewModel viewModel) {
        this.viewModel = viewModel;
        this.isViewModelSet = true;
        System.out.println("[TourController] ViewModel successfully injected!");

        if (tourTable != null) {
            bindUI();
        } else {
            System.err.println("[TourController] tourTable is NULL! Binding postponed...");
        }
    }
    @FXML
    public void initialize() {
        System.out.println("[TourController] initialize() called.");

        if (viewModel == null) {
            System.err.println("[TourController] ViewModel is NULL in initialize()! Waiting for setViewModel...");
            return;
        }

        bindUI(); // Proceed only if ViewModel is already set
    }

    private void bindUI() {
        System.out.println("[TourController] Setting up TableView columns...");
        colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        colFrom.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
        colTo.setCellValueFactory(cellData -> cellData.getValue().toProperty());
        colTransportType.setCellValueFactory(cellData -> cellData.getValue().transportTypeProperty());
        colDistance.setCellValueFactory(cellData -> cellData.getValue().distanceProperty().asObject());
        colEstimatedTime.setCellValueFactory(cellData -> cellData.getValue().estimatedTimeProperty().asObject());

        System.out.println("[TourController] Binding TableView data...");
        tourTable.setItems(viewModel.getTours());

        btnAdd.setOnAction(e -> onNewTourClick());
        btnEdit.setOnAction(e -> onEditTourClick());
        btnDelete.setOnAction(e -> onDeleteTourClick());
    }

    @FXML
    private void onNewTourClick() {
        Tour newTour = new Tour(
                UUID.randomUUID(), "", "", "", "", "", 0.0, 0.0, ""
        );
        TourViewModel tourViewModel = new TourViewModel(newTour);

        boolean okClicked = openTourEditDialog(tourViewModel);
        if (okClicked) {
            viewModel.addTour(tourViewModel);  // Hier wird die Tour hinzugefügt
            tourTable.refresh();  // Tabelle aktualisieren
        }
    }


    @FXML
    private void onEditTourClick() {
        ObservableList<TourViewModel> selectedItems = tourTable.getSelectionModel().getSelectedItems();
        if (selectedItems.isEmpty()) {
            showAlert("Keine Auswahl", "Bitte wählen Sie eine Tour zur Bearbeitung aus.");
            return;
        }

        TourViewModel selectedTour = selectedItems.get(0);
        TourViewModel tempTour = selectedTour; // Kopie für Bearbeitung

        boolean okClicked = openTourEditDialog(tempTour);
        if (okClicked) {
            viewModel.updateTour(tempTour);
        }
    }

    @FXML
    private void onShowLogsClick() {
        TourViewModel selectedTour = tourTable.getSelectionModel().getSelectedItem();
        if (selectedTour == null) {
            showAlert("Keine Auswahl", "Bitte wählen Sie eine Tour aus, um die Logs anzuzeigen.");
            return;
        }

        openTourLogView(selectedTour);
    }

    private void openTourLogView(TourViewModel tourViewModel) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/technikum/studentmanagementsystem2/TourLogView.fxml"));
            Parent page = loader.load();


            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tour Logs für " + tourViewModel.getName());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tourTable.getScene().getWindow());
            dialogStage.setScene(new Scene(page));

            TourLogController controller = loader.getController();
            controller.setViewModel(new TourLogTableViewModel());
            controller.setTour(tourViewModel);

            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean openTourEditDialog(TourViewModel tourViewModel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/technikum/studentmanagementsystem2/tourEditWindow.fxml"));
            Parent page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tour bearbeiten");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tourTable.getScene().getWindow());
            dialogStage.setScene(new Scene(page));

            TourEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTourViewModel(tourViewModel);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @FXML
    private void onDeleteTourClick() {
        if (viewModel == null) {
            System.err.println("[TourController] Cannot delete tour, ViewModel is NULL!");
            return;
        }
        TourViewModel selectedTour = tourTable.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            System.out.println("[TourController] Deleting tour: " + selectedTour.getName());
            viewModel.deleteTour(selectedTour);
            tourTable.refresh();
        } else {
            System.err.println("[TourController] No tour selected for deletion!");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

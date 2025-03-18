package at.technikum.studentmanagementsystem2.controller;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.UUID;

public class TourController {
    @FXML private TableView<Tour> tourTable;
    @FXML private TableColumn<Tour, String> colName;
    @FXML private TableColumn<Tour, String> colDescription;
    @FXML private TableColumn<Tour, String> colFrom;
    @FXML private TableColumn<Tour, String> colTo;
    @FXML private TableColumn<Tour, String> colTransportType;
    @FXML private TableColumn<Tour, Double> colDistance;
    @FXML private TableColumn<Tour, Double> colEstimatedTime;
    @FXML private Button btnAdd, btnEdit, btnDelete;

    private TourViewModel viewModel;

    public TourController() {
        System.out.println("TourController created (default constructor)");
    }

    public void setViewModel(TourViewModel viewModel) {
        this.viewModel = viewModel;
        System.out.println("ViewModel successfully injected!");

        if (tourTable != null) {
            System.out.println("TourTable is initialized, binding data...");
            tourTable.setItems(viewModel.getTours());
        } else {
            System.err.println("TourTable is NULL in setViewModel(). Binding postponed...");
        }
    }

    @FXML
    public void initialize() {
        System.out.println("TourController initialize() called.");

        // Ensure TableView is correctly linked before using ViewModel
        if (tourTable == null) {
            System.err.println("tourTable is NULL! Ensure FXML is correctly linked.");
            return;
        }

        System.out.println("TourTable is initialized, setting columns...");

        colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        colFrom.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
        colTo.setCellValueFactory(cellData -> cellData.getValue().toProperty());
        colTransportType.setCellValueFactory(cellData -> cellData.getValue().transportTypeProperty());
        colDistance.setCellValueFactory(cellData -> cellData.getValue().distanceProperty().asObject());
        colEstimatedTime.setCellValueFactory(cellData -> cellData.getValue().estimatedTimeProperty().asObject());

        if (viewModel == null) {
            System.err.println("ViewModel is NULL in initialize()! Waiting for setViewModel...");
            return;
        }

        tourTable.setItems(viewModel.getTours());

        // Button actions
        btnAdd.setOnAction(e -> addTour());
        btnEdit.setOnAction(e -> editTour());
        btnDelete.setOnAction(e -> deleteTour());
    }


    private void addTour() {
        if (viewModel == null) {
            System.err.println("Cannot add tour, ViewModel is NULL!");
            return;
        }
        Tour newTour = new Tour(
                UUID.randomUUID(), "New Tour", "A beautiful tour",
                "Vienna", "Salzburg", "Car",
                250.0, 180.0, "image_url"
        );
        viewModel.saveTour(newTour);
    }

    private void editTour() {
        if (viewModel == null) {
            System.err.println("Cannot edit tour, ViewModel is NULL!");
            return;
        }
        Tour selectedTour = tourTable.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            selectedTour.setDescription("Updated Description");
            viewModel.saveTour(selectedTour);
        }
    }

    private void deleteTour() {
        if (viewModel == null) {
            System.err.println("Cannot delete tour, ViewModel is NULL!");
            return;
        }
        Tour selectedTour = tourTable.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            viewModel.deleteTour(selectedTour);
        }
    }
}

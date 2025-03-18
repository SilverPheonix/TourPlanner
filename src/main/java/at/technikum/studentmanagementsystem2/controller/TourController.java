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
    private boolean isViewModelSet = false; // Track if setViewModel() was called

    public TourController() {
        System.out.println("[TourController] Constructor called.");
    }

    public void setViewModel(TourViewModel viewModel) {
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

        btnAdd.setOnAction(e -> addTour());
        btnEdit.setOnAction(e -> editTour());
        btnDelete.setOnAction(e -> deleteTour());
    }

    private void addTour() {
        if (viewModel == null) {
            System.err.println("[TourController] Cannot add tour, ViewModel is NULL!");
            return;
        }
        System.out.println("[TourController] Adding new tour...");
        Tour newTour = new Tour(
                UUID.randomUUID(), "New Tour", "A beautiful tour",
                "Vienna", "Salzburg", "Car",
                250.0, 180.0, "placeholder.jpg"
        );
        viewModel.saveTour(newTour);
        tourTable.refresh();  // Ensure UI updates
    }

    private void editTour() {
        if (viewModel == null) {
            System.err.println("[TourController] Cannot edit tour, ViewModel is NULL!");
            return;
        }
        Tour selectedTour = tourTable.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            System.out.println("[TourController] Editing tour: " + selectedTour.getName());
            selectedTour.setDescription("Updated Description");
            viewModel.saveTour(selectedTour);
            tourTable.refresh();
        } else {
            System.err.println("[TourController] No tour selected for editing!");
        }
    }

    private void deleteTour() {
        if (viewModel == null) {
            System.err.println("[TourController] Cannot delete tour, ViewModel is NULL!");
            return;
        }
        Tour selectedTour = tourTable.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            System.out.println("[TourController] Deleting tour: " + selectedTour.getName());
            viewModel.deleteTour(selectedTour);
            tourTable.refresh();
        } else {
            System.err.println("[TourController] No tour selected for deletion!");
        }
    }
}

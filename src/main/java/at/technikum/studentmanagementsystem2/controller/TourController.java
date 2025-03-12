package at.technikum.studentmanagementsystem2.controller;

import  at.technikum.studentmanagementsystem2.models.Tour;
import  at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
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

    public TourController(TourViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void initialize() {
        tourTable.setItems(viewModel.getTours());

        // Bindet die Daten der TableView an das ViewModel
        tourTable.setItems(viewModel.getTours());

        // Button-Aktionen setzen
        btnAdd.setOnAction(e -> addTour());
        btnEdit.setOnAction(e -> editTour());
        btnDelete.setOnAction(e -> deleteTour());
    }

    private void addTour() {
        Tour newTour = new Tour(
                UUID.randomUUID(), "New Tour", "A beautiful tour",
                "Vienna", "Salzburg", "Car",
                250.0, 180.0, "image_url"
        );
        viewModel.saveTour(newTour);
    }

    private void editTour() {
        Tour selectedTour = tourTable.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            selectedTour.setDescription("Updated Description");
            viewModel.saveTour(selectedTour);
        }
    }

    private void deleteTour() {
        Tour selectedTour = tourTable.getSelectionModel().getSelectedItem();
        if (selectedTour != null) {
            viewModel.deleteTour(selectedTour);
        }
    }
}


package at.technikum.studentmanagementsystem2.controller;

import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.mvvm.TourLogViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.UUID;

public class TourLogController {
    @FXML private TableView<TourLog> logTable;
    @FXML private TableColumn<TourLog, String> colDate;
    @FXML private TableColumn<TourLog, String> colComment;
    @FXML private TableColumn<TourLog, String> colDifficulty;
    @FXML private TableColumn<TourLog, Double> colDistance;
    @FXML private TableColumn<TourLog, Double> colTime;
    @FXML private TableColumn<TourLog, Integer> colRating;
    @FXML private Button btnAdd, btnEdit, btnDelete;

    private TourLogViewModel viewModel;
    private boolean isViewModelSet = false;

    public void setViewModel(TourLogViewModel viewModel) {
        this.viewModel = viewModel;
        this.isViewModelSet = true;
    }
    public TourLogController() {
        System.out.println("[TourLogController] Constructor called.");
    }

    public void initialize() {
        if (viewModel != null) {
            logTable.setItems(viewModel.getTourLogs());

            // Button-Events
            btnAdd.setOnAction(e -> addTourLog());
            btnEdit.setOnAction(e -> editTourLog());
            btnDelete.setOnAction(e -> deleteTourLog());
        }
    }
    public void setTourViewModel(TourViewModel tourViewModel) {
        //viewModel.loadTourLogs(tourViewModel.getId());
        logTable.setItems(viewModel.getTourLogs());
    }


    private void addTourLog() {
        TourLog newLog = new TourLog(UUID.randomUUID(), UUID.randomUUID(),
                java.time.LocalDateTime.now(), "Great trip!", "Medium",
                15.0, 120.0, 4);
        viewModel.addTourLog(newLog);
    }

    private void editTourLog() {
        TourLog selectedLog = logTable.getSelectionModel().getSelectedItem();
        if (selectedLog != null) {
            selectedLog.setComment("Updated Comment");
            viewModel.updateTourLog(selectedLog);
        }
    }

    private void deleteTourLog() {
        TourLog selectedLog = logTable.getSelectionModel().getSelectedItem();
        if (selectedLog != null) {
            viewModel.deleteTourLog(selectedLog);
        }
    }
}

package at.technikum.studentmanagementsystem2.controller;

import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.mvvm.TourLogViewModel;
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

    public void initialize() {
        viewModel = new TourLogViewModel(new TourLogService());

        // Tabellenbindung
        logTable.setItems(viewModel.getTourLogs());

        // Button-Events
        btnAdd.setOnAction(e -> addTourLog());
        btnEdit.setOnAction(e -> editTourLog());
        btnDelete.setOnAction(e -> deleteTourLog());
    }

    private void addTourLog() {
        TourLog newLog = new TourLog(UUID.randomUUID(), UUID.randomUUID(),
                java.time.LocalDateTime.now(), "Great trip!", "Medium",
                15.0, 120.0, 4);
        viewModel.saveTourLog(newLog);
    }

    private void editTourLog() {
        TourLog selectedLog = logTable.getSelectionModel().getSelectedItem();
        if (selectedLog != null) {
            selectedLog.setComment("Updated Comment");
            viewModel.saveTourLog(selectedLog);
        }
    }

    private void deleteTourLog() {
        TourLog selectedLog = logTable.getSelectionModel().getSelectedItem();
        if (selectedLog != null) {
            viewModel.deleteTourLog(selectedLog);
        }
    }
}

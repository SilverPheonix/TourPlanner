package at.technikum.studentmanagementsystem2.controller;

import at.technikum.studentmanagementsystem2.mvvm.TourTableViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class TourEditDialogController {

    @FXML private TextField tourNameField;
    @FXML private TextArea tourDescriptionField;
    @FXML private TextField tourFromField;
    @FXML private TextField tourToField;
    @FXML private TextField tourTransportField;
    @FXML private TextField tourDistanceField;
    @FXML private TextField tourEstimatedtimeField;
    @FXML private TextField tourImageField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Label titleLabel;

    private TourViewModel currentTour;
    private boolean saved = false;


    public void init(TourViewModel tour) {
        this.currentTour = tour;

        tourNameField.textProperty().bindBidirectional(currentTour.nameProperty());
        tourDescriptionField.textProperty().bindBidirectional(currentTour.descriptionProperty());
        tourFromField.textProperty().bindBidirectional(currentTour.fromProperty());
        tourToField.textProperty().bindBidirectional(currentTour.toProperty());
        tourTransportField.textProperty().bindBidirectional(currentTour.transportTypeProperty());
        tourDistanceField.textProperty().bindBidirectional(currentTour.distanceProperty(), new NumberStringConverter());
        tourEstimatedtimeField.textProperty().bindBidirectional(currentTour.estimatedTimeProperty(), new NumberStringConverter());
        tourImageField.textProperty().bindBidirectional(currentTour.imageUrlProperty());
    }


    // Wird aufgerufen, wenn der "Save"-Button gedrückt wird
    @FXML
    private void onSave() {
        saved = true;
        // Schließe das Dialogfenster
        closeDialog();
    }
    public boolean isSaved() {
        return saved;
    }

    // Wird aufgerufen, wenn der "Cancel"-Button gedrückt wird
    @FXML
    private void onCancel() {
        // Schließe das Dialogfenster ohne Änderungen
        closeDialog();
    }

    // Hilfsmethode, um das Dialogfenster zu schließen
    private void closeDialog() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    // Titel des Fensters setzen
    public void setTitle(String title) {
        titleLabel.setText(title);
    }
}

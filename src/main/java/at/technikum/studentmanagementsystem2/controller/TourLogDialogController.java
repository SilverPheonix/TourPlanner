package at.technikum.studentmanagementsystem2.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class TourLogDialogController {

    @FXML private Label titleLabel;
    @FXML private TextField commentField, distanceField, timeField;
    @FXML private ComboBox<String> difficultyBox;
    @FXML private Slider ratingSlider;
    @FXML private Button cancelButton, saveButton;

    private boolean isSaved = false;

    @FXML
    private void initialize() {
        // Initialisiert die Schwierigkeit mit Standardwerten
        difficultyBox.setItems(FXCollections.observableArrayList("Leicht", "Mittel", "Schwer"));

        // Setzt die Aktionen für die Buttons
        //cancelButton.setOnAction(event -> commentField.getScene().getWindow().hide());
        //saveButton.setOnAction(event -> handleSave(event));
    }

    // Titel des Fensters setzen
    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    // Werte aus dem MainController setzen
    public void setValues(String comment, String difficulty, double distance, double time, int rating) {
        commentField.setText(comment);
        difficultyBox.setValue(difficulty);
        distanceField.setText(String.valueOf(distance));
        timeField.setText(String.valueOf(time));
        ratingSlider.setValue(rating);
    }

    // Überprüfen, ob gespeichert wurde
    public boolean isSaved() {
        return isSaved;
    }

    // Methoden, um die eingegebenen Werte abzurufen
    public String getComment() {
        return commentField.getText();
    }

    public String getDifficulty() {
        return difficultyBox.getValue();
    }

    public double getDistance() {
        return Double.parseDouble(distanceField.getText());
    }

    public double getTime() {
        return Double.parseDouble(timeField.getText());
    }

    public int getRating() {
        return (int) ratingSlider.getValue();
    }

    // Method to handle saving the data, with validation
    @FXML
    private void handleSave(ActionEvent event) {
        // Validation
        if (isInputValid()) {
            // Mark as saved if input is valid
            isSaved = true;

            commentField.getScene().getWindow().hide();
        } else {
            // Show error message if validation fails
            showAlert("Fehler", "Bitte stellen Sie sicher, dass alle Felder korrekt ausgefüllt sind.", Alert.AlertType.ERROR);
        }
    }

    // Validate the input fields
    private boolean isInputValid() {
        // Check if comment is empty
        if (getComment().isEmpty()) {
            showAlert("Fehler", "Kommentar darf nicht leer sein!", Alert.AlertType.ERROR);
            return false;
        }

        // Check if difficulty is selected
        if (getDifficulty() == null) {
            showAlert("Fehler", "Bitte wählen Sie eine Schwierigkeit aus.", Alert.AlertType.ERROR);
            return false;
        }

        // Validate distance
        try {
            double distance = getDistance();
            if (distance <= 0) {
                showAlert("Fehler", "Distanz muss eine positive Zahl sein!", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Fehler", "Distanz muss eine gültige Zahl sein!", Alert.AlertType.ERROR);
            return false;
        }

        // Validate time
        try {
            double time = getTime();
            if (time <= 0) {
                showAlert("Fehler", "Zeit muss eine positive Zahl sein!", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Fehler", "Zeit muss eine gültige Zahl sein!", Alert.AlertType.ERROR);
            return false;
        }

        // Rating is implicitly validated by the slider, no need to check here.

        return true; // All validations passed
    }

    // Helper method to show an alert
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Method to handle the cancel action
    @FXML
    private void handleCancel(ActionEvent event) {
        // Confirm if the user really wants to cancel
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Abbrechen");
        alert.setHeaderText("Möchten Sie wirklich abbrechen?");
        alert.setContentText("Alle nicht gespeicherten Änderungen gehen verloren.");

        // Show the alert and get the user's choice
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // If user confirms, close the dialog without saving
                commentField.getScene().getWindow().hide();
            }
            // If user cancels, do nothing and keep the dialog open
        });
    }
}

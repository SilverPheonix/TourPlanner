package at.technikum.studentmanagementsystem2.controller;

import at.technikum.studentmanagementsystem2.mvvm.TourTableViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;


public class TourEditController {

    @FXML
    private VBox vbox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField fromField;

    @FXML
    private TextField toField;

    @FXML
    private TextField transporttypeField;

    @FXML
    private TextField distanceField;

    @FXML
    private TextField estimatedtimeField;

    private Stage dialogStage;
    private TourViewModel tourViewModel;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER -> handleOk();
                case ESCAPE -> handleCancel();
            }
        });

    }
    public void setTourViewModel(TourViewModel tourViewModel) {
        this.tourViewModel = tourViewModel;

        // Bind Name
        nameField.textProperty().bindBidirectional(tourViewModel.nameProperty());

        // Bind Description
        descriptionField.textProperty().bindBidirectional(tourViewModel.descriptionProperty());

        // Bind From and To
        fromField.textProperty().bindBidirectional(tourViewModel.fromProperty());
        toField.textProperty().bindBidirectional(tourViewModel.toProperty());

        // Bind Transport Type
        transporttypeField.textProperty().bindBidirectional(tourViewModel.transportTypeProperty());

        // Bind Distance and Estimated Time with NumberStringConverter
        NumberStringConverter converter = new NumberStringConverter();
        distanceField.textProperty().bindBidirectional(tourViewModel.distanceProperty(), converter);
        estimatedtimeField.textProperty().bindBidirectional(tourViewModel.estimatedTimeProperty(), converter);
    }


    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            okClicked = true;
            System.out.println("Ok Clicked: " + okClicked);  // Debug-Ausgabe
            dialogStage.close();  // Dialog schließen
        } else {
            System.out.println("Invalid input detected.");  // Debug-Ausgabe
        }
    }


    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
            errorMessage += "Name ist erforderlich!\n";
        }
        if (fromField.getText() == null || fromField.getText().trim().isEmpty()) {
            errorMessage += "Startort ist erforderlich!\n";
        }
        if (toField.getText() == null || toField.getText().trim().isEmpty()) {
            errorMessage += "Zielort ist erforderlich!\n";
        }
        if (transporttypeField.getText() == null || transporttypeField.getText().trim().isEmpty()) {
            errorMessage += "Transporttyp ist erforderlich!\n";
        }

        try {
            Double.parseDouble(distanceField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Distanz muss eine gültige Zahl sein!\n";
        }
        try {
            Double.parseDouble(estimatedtimeField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Geschätzte Zeit muss eine gültige Zahl sein!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ungültige Eingabe");
            alert.setHeaderText("Bitte korrigiere die fehlerhaften Felder");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }


}

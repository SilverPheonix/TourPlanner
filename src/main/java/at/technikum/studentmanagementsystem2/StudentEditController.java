package at.technikum.studentmanagementsystem2;

import at.technikum.studentmanagementsystem2.models.Student;
import at.technikum.studentmanagementsystem2.mvvm.StudentViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;


public class StudentEditController {

    @FXML
    private VBox vbox;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField semesterField;

    private Stage dialogStage;
    private StudentViewModel studentViewModel;
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

    public void setStudentViewModel(StudentViewModel studentViewModel) {
        this.studentViewModel = studentViewModel;
        // Bind ID (read-only)
        idField.textProperty().bind(studentViewModel.idProperty());

        // Bind Name (2-way)
        nameField.textProperty().bindBidirectional(studentViewModel.nameProperty());

        // Bind Semester with NumberStringConverter for IntegerProperty
        NumberStringConverter converter = new NumberStringConverter();
        semesterField.textProperty().bindBidirectional(studentViewModel.semesterProperty(), converter);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
            errorMessage += "Name is required!\n";
        }

        try {
            Integer.parseInt(semesterField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Semester must be a valid number!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}

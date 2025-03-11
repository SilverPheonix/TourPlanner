package at.technikum.studentmanagementsystem2;

import at.technikum.studentmanagementsystem2.helpers.UUIDv7Generator;
import at.technikum.studentmanagementsystem2.mediators.ButtonDisableSelectionMediator;
import at.technikum.studentmanagementsystem2.mediators.Mediator;
import at.technikum.studentmanagementsystem2.models.Student;
import at.technikum.studentmanagementsystem2.mvvm.StudentTableViewModel;
import at.technikum.studentmanagementsystem2.mvvm.StudentViewModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentManagementController implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private Label welcomeText;
    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView studentTable;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn semesterColumn;

    private StudentTableViewModel studentTableView;

    private ArrayList<Mediator> mediators = new ArrayList<>();

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onNewButtonClick() {
        Student student = new Student(UUIDv7Generator.generateUUIDv7(), "", 1);
        StudentViewModel svm = new StudentViewModel(student);
        studentTableView.newStudent(svm);
        studentTable.getSelectionModel().clearSelection();
        studentTable.getSelectionModel().select(svm);
        this.onEditButtonClick();
    }

    @FXML
    protected void onEditButtonClick() {
        ObservableList<StudentViewModel> selectedItems = studentTable.getSelectionModel().getSelectedItems();

        if (selectedItems != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("studentEditWindow.fxml"));
                Parent page = loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Edit Student");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(vbox.getScene().getWindow());
                dialogStage.setScene(new Scene(page));


                // Pass ViewModel to the controller
                StudentEditController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                StudentViewModel studentViewModel = new StudentViewModel(selectedItems.get(0));
                controller.setStudentViewModel(studentViewModel);

                dialogStage.showAndWait();

                if (controller.isOkClicked()) {
                    studentTableView.updateStudent(studentViewModel);
                    studentTable.refresh(); // Reflect changes in TableView
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Student Selected");
            alert.setContentText("Please select a student to edit.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentTableView = new StudentTableViewModel();
        studentTable.setItems(studentTableView.getData());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        studentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        studentTable.getSelectionModel().select(0);
        this.mediators.add(new ButtonDisableSelectionMediator(this.editButton,this.studentTable, true, false, true));
        this.mediators.add(new ButtonDisableSelectionMediator(this.deleteButton,this.studentTable, true, false, false));
    }
}
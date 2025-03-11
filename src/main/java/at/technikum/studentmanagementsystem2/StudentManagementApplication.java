package at.technikum.studentmanagementsystem2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentManagementApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StudentManagementApplication.class.getResource("studentManagementWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Student Management System V0.0.1");
        stage.setScene(scene);
        stage.show();
        stage.sizeToScene();
    }

    public static void main(String[] args) {
        launch();
    }
}
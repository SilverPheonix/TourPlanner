package at.technikum.studentmanagementsystem2;

import at.technikum.studentmanagementsystem2.config.DatabaseConnection;
import at.technikum.studentmanagementsystem2.controller.MainController;
import at.technikum.studentmanagementsystem2.mvvm.TourTableViewModel;
import at.technikum.studentmanagementsystem2.mvvm.TourLogTableViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize the DB creation script
            DatabaseConnection.initializeDatabase();
            System.out.println("Application started...");

            // Lade die neue Hauptansicht
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/technikum/studentmanagementsystem2/MainView.fxml"));
            Parent root = loader.load();

            // Controller holen
            MainController controller = loader.getController();

            // ViewModels erstellen und dem Controller setzen
            TourTableViewModel tourTableViewModel = new TourTableViewModel();
            TourLogTableViewModel tourLogTableViewModel = new TourLogTableViewModel();
            controller.setViewModels(tourTableViewModel, tourLogTableViewModel);

            // Szene und Fenster setzen
            primaryStage.setScene(new Scene(root,1000,600));
            primaryStage.setTitle("Tour Management System");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

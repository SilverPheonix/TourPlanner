package at.technikum.studentmanagementsystem2;

import at.technikum.studentmanagementsystem2.controller.TourController;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/technikum/studentmanagementsystem2/TourView.fxml"));
            Parent root = loader.load();

            TourController controller = loader.getController();
            TourViewModel viewModel = new TourViewModel();
            controller.setViewModel(viewModel);  // ✅ Inject ViewModel before UI loads

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Tour Management");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

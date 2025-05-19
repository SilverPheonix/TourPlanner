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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/at/technikum/studentmanagementsystem2/config.properties")
public class Main extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() throws Exception {
        // Start Spring Boot
        springContext = new SpringApplicationBuilder(Main.class)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                    "/at/technikum/studentmanagementsystem2/MainView.fxml"
            ));
            // Use Spring's Bean Factory to resolve controllers and manage dependencies
            fxmlLoader.setControllerFactory(springContext::getBean);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 1000, 600);

            // Set the stage
            primaryStage.setScene(scene);
            primaryStage.setTitle("Tour Management System");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        // Shut down Spring when JavaFX application closes
        springContext.close();
    }

    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();
        launch(args); // Entry point of JavaFX application
    }
}


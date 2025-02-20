module at.tourplanner.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.tourplanner.tourplanner to javafx.fxml;
    exports at.tourplanner.tourplanner;
}
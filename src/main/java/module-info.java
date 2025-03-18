module at.technikum.studentmanagementsystem2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.context;
    requires spring.web;
    requires spring.data.jpa;
    requires java.sql;

    // Open packages to JavaFX for reflection
    opens at.technikum.studentmanagementsystem2 to javafx.fxml;
    opens at.technikum.studentmanagementsystem2.controller to javafx.fxml;
    opens at.technikum.studentmanagementsystem2.models to javafx.base;  // For JavaFX bindings (if needed)

    // Export packages to make them accessible
    exports at.technikum.studentmanagementsystem2;
    exports at.technikum.studentmanagementsystem2.models;
    exports at.technikum.studentmanagementsystem2.mvvm;
}

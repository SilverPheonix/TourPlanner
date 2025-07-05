package at.technikum.studentmanagementsystem2.helpers;

import javafx.scene.control.Alert;

public class AlertHelper {

    public static void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Optional: Convenience-Methoden für häufige Fälle
    public static void showError(String message) {
        showAlert("Fehler", message, Alert.AlertType.ERROR);
    }

    public static void showInfo(String message) {
        showAlert("Info", message, Alert.AlertType.INFORMATION);
    }
}

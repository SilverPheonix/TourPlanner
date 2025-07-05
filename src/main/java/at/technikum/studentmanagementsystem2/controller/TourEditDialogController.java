package at.technikum.studentmanagementsystem2.controller;

import at.technikum.studentmanagementsystem2.helpers.JavaBridge;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import at.technikum.studentmanagementsystem2.service.TourService;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import netscape.javascript.JSObject;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URL;
import at.technikum.studentmanagementsystem2.helpers.AlertHelper;


@Component
public class TourEditDialogController {

    @FXML private TextField tourNameField;
    @FXML private TextArea tourDescriptionField;
    @FXML private TextField tourFromField;
    @FXML private TextField tourToField;
    @FXML private TextField tourTransportField;
    @FXML private TextField tourDistanceField;
    @FXML private TextField tourEstimatedtimeField;
    @FXML private TextField tourImageField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Label titleLabel;
    @FXML private WebView tourMapView;

    private TourViewModel currentTour;
    private TourService tourService;
    private boolean saved = false;

    public void setTourService(TourService service) {
        this.tourService = service;
    }


    public void init(TourViewModel tour) {
        this.currentTour = tour;

        tourNameField.textProperty().bindBidirectional(currentTour.nameProperty());
        tourDescriptionField.textProperty().bindBidirectional(currentTour.descriptionProperty());
        tourFromField.textProperty().bindBidirectional(currentTour.fromProperty());
        tourToField.textProperty().bindBidirectional(currentTour.toProperty());
        tourTransportField.textProperty().bindBidirectional(currentTour.transportTypeProperty());
        tourDistanceField.textProperty().bindBidirectional(currentTour.distanceProperty(), new NumberStringConverter());
        tourEstimatedtimeField.textProperty().bindBidirectional(currentTour.estimatedTimeProperty(), new NumberStringConverter());
        tourImageField.textProperty().bindBidirectional(currentTour.imageUrlProperty());

        //Load Map
        WebEngine engine = tourMapView.getEngine();
        URL mapHtmlUrl = getClass().getResource("/map.html");

        if (mapHtmlUrl != null) {
            engine.load(mapHtmlUrl.toExternalForm()+ "?mode=edit");
            engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {

                    JSObject window = (JSObject) engine.executeScript("window");
                    JavaBridge bridge = new JavaBridge(currentTour, tourService, tourMapView);
                    window.setMember("javaConnector", bridge);

                    if (currentTour.hasCoordinates()) {
                        try {
                            String geoJson = tourService.getRouteGeoJson(currentTour);
                            String escapedGeoJson = geoJson.replace("\"", "\\\"");
                            engine.executeScript("window.loadRoute(\"" + escapedGeoJson + "\");");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }

    }


    // Wird aufgerufen, wenn der "Save"-Button gedrückt wird
    @FXML
    private void onSave() {
        if (isInputValid()) {
            saved = true;
            closeDialog();
        }
    }

    public boolean isSaved() {
        return saved;
    }

    // Wird aufgerufen, wenn der "Cancel"-Button gedrückt wird
    @FXML
    private void onCancel() {
        // Schließe das Dialogfenster ohne Änderungen
        closeDialog();
    }

    // Hilfsmethode, um das Dialogfenster zu schließen
    private void closeDialog() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    // Titel des Fensters setzen
    public void setTitle(String title) {
        titleLabel.setText(title);
    }


    private boolean isInputValid() {
        // Name
        if (tourNameField.getText().trim().isEmpty()) {
            AlertHelper.showAlert("Fehler", "Name darf nicht leer sein!", Alert.AlertType.ERROR);
            return false;
        }

        // From
        if (tourFromField.getText().trim().isEmpty()) {
            AlertHelper.showAlert("Fehler", "\"Von\" darf nicht leer sein!", Alert.AlertType.ERROR);
            return false;
        }

        // To
        if (tourToField.getText().trim().isEmpty()) {
            AlertHelper.showAlert("Fehler", "\"Nach\" darf nicht leer sein!", Alert.AlertType.ERROR);
            return false;
        }

        // Transport
        if (tourTransportField.getText().trim().isEmpty()) {
            AlertHelper.showAlert("Fehler", "Transportmittel darf nicht leer sein!", Alert.AlertType.ERROR);
            return false;
        }

        // Distance
        try {
            double dist = Double.parseDouble(tourDistanceField.getText().trim());
            if (dist <= 0) {
                AlertHelper.showAlert("Fehler", "Distanz muss positiv sein!", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            AlertHelper.showAlert("Fehler", "Distanz muss eine gültige Zahl sein!", Alert.AlertType.ERROR);
            return false;
        }

        // Time
        try {
            double time = Double.parseDouble(tourEstimatedtimeField.getText().trim());
            if (time <= 0) {
                AlertHelper.showAlert("Fehler", "Zeit muss positiv sein!", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            AlertHelper.showAlert("Fehler", "Zeit muss eine gültige Zahl sein!", Alert.AlertType.ERROR);
            return false;
        }

        // Coordinates check
        if (!currentTour.hasCoordinates()) {
            AlertHelper.showAlert("Fehler", "Bitte wählen Sie Start- und Zielkoordinaten auf der Karte!", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

}

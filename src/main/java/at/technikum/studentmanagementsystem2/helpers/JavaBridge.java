package at.technikum.studentmanagementsystem2.helpers;

import javafx.application.Platform;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import at.technikum.studentmanagementsystem2.service.TourService;
import javafx.application.Platform;
import javafx.scene.web.WebView;

import java.io.IOException;

public class JavaBridge {
    private final TourViewModel currentTour;
    private final TourService tourService;
    private final WebView tourMapView;

    public JavaBridge(TourViewModel currentTour, TourService tourService, WebView tourMapView) {
        this.currentTour = currentTour;
        this.tourService = tourService;
        this.tourMapView = tourMapView;
    }

    public void setCoordinates(double startLat, double startLon, double endLat, double endLon) {
        currentTour.setstartLat(startLat);
        currentTour.setstartLon(startLon);
        currentTour.setendLat(endLat);
        currentTour.setendLon(endLon);

        // Optional: Route anzeigen
        Platform.runLater(() -> {
            try {
                String geoJson = tourService.getRouteGeoJson(currentTour);
                String escapedGeoJson = geoJson.replace("\"", "\\\"");
                tourMapView.getEngine().executeScript("window.loadRoute(\"" + escapedGeoJson + "\");");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}

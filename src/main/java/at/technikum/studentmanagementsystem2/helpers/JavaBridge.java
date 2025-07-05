package at.technikum.studentmanagementsystem2.helpers;

import javafx.application.Platform;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import at.technikum.studentmanagementsystem2.service.TourService;
import javafx.scene.image.WritableImage;
import javafx.scene.web.WebView;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;


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

        Platform.runLater(() -> {
            try {
                String geoJson = tourService.getRouteGeoJson(currentTour);
                String escapedGeoJson = geoJson.replace("\"", "\\\"");
                tourMapView.getEngine().executeScript("window.loadRoute(\"" + escapedGeoJson + "\");");

                // Screenshot-Ordner erstellen, falls nicht vorhanden
                File dir = new File("screenshots");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Alte Screenshots der aktuellen Tour löschen
                File[] oldScreenshots = dir.listFiles((d, name) ->
                        name.startsWith(currentTour.getId() + "_") && name.endsWith(".png"));
                if (oldScreenshots != null) {
                    for (File old : oldScreenshots) {
                        old.delete();
                    }
                }

                // Neuen Screenshot speichern
                String filename = "screenshots/" + currentTour.getId() + "_" + System.currentTimeMillis() + ".png";
                captureMapScreenshot(filename);
                currentTour.setImageUrl(filename);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public void captureMapScreenshot(String fileName) {
        Platform.runLater(() -> {
            WritableImage image = tourMapView.snapshot(null, null);
            File file = new File(fileName);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                System.out.println("Screenshot gespeichert unter: " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}

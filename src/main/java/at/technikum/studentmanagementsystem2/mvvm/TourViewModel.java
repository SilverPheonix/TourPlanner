package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.UUID;

public class TourViewModel {
    private final ObservableList<Tour> tours = FXCollections.observableArrayList();

    public TourViewModel() {
        // Sample data for testing
        tours.add(new Tour(UUID.randomUUID(), "Test Tour", "Description",
                "Vienna", "Graz", "Train", 200.0, 120.0, "image_url"));
    }

    // Expose the ObservableList for JavaFX bindings
    public ObservableList<Tour> getTours() {
        return tours;
    }

    public void saveTour(Tour tour) {
        int index = tours.indexOf(tour);
        if (index == -1) {
            System.out.println("[TourViewModel] Adding new tour: " + tour.getName());
            tours.add(tour);
        } else {
            System.out.println("[TourViewModel] Updating tour: " + tour.getName());
            tours.set(index, tour);
        }
    }

    public void deleteTour(Tour tour) {
        System.out.println("[TourViewModel] Deleting tour: " + tour.getName());
        tours.remove(tour);
    }
}

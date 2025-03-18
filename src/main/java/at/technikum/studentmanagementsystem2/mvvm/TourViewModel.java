package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.UUID;

public class TourViewModel {
    private final ObservableList<Tour> tours = FXCollections.observableArrayList();

    public TourViewModel() {
        // Sample data for testing (optional)
        tours.add(new Tour(UUID.randomUUID(), "Test Tour", "Description",
                "Vienna", "Graz", "Train", 200.0, 120.0, "image_url"));
    }

    // ✅ Expose the ObservableList for JavaFX bindings
    public ObservableList<Tour> getTours() {
        return tours;
    }

    // ✅ Save a new tour or update an existing one
    public void saveTour(Tour tour) {
        if (!tours.contains(tour)) {
            tours.add(tour);
        } else {
            int index = tours.indexOf(tour);
            tours.set(index, tour);
        }
    }

    // ✅ Remove a tour from the list
    public void deleteTour(Tour tour) {
        tours.remove(tour);
    }
}

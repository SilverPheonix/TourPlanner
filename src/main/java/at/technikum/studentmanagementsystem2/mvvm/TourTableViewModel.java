package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TourTableViewModel {
    private final ObservableList<TourViewModel> tours = FXCollections.observableArrayList();

    public TourTableViewModel() {
        // Sample-Daten zum Testen
        tours.add(new TourViewModel(new Tour(
                UUID.randomUUID(), "Test Tour", "Eine schöne Tour",
                "Wien", "Graz", "Zug", 200.0, 120.0, "image_url"
        )));
    }

    public ObservableList<TourViewModel> getTours() {
        return tours;
    }

    public void addTour(TourViewModel tour) {
        tours.add(tour);
    }

    public void updateTour(TourViewModel updatedTour) {
        for (TourViewModel tour : tours) {
            if (tour.getId().equals(updatedTour.getId())) {
                tour.nameProperty().set(updatedTour.getName());
                tour.descriptionProperty().set(updatedTour.getDescription());
                tour.fromProperty().set(updatedTour.getFrom());
                tour.toProperty().set(updatedTour.getTo());
                tour.transportTypeProperty().set(updatedTour.getTransportType());
                tour.distanceProperty().set(updatedTour.getDistance());
                tour.estimatedTimeProperty().set(updatedTour.getEstimatedTime());
                tour.imageUrlProperty().set(updatedTour.getImageUrl());
                tour.childFriendlinessProperty().set(updatedTour.getChildFriendliness());
                tour.popularityProperty().set(updatedTour.getPopularity());
                tour.startLatProperty().set(updatedTour.getStartLat());
                tour.startLonProperty().set(updatedTour.getStartLon());
                tour.endLatProperty().set(updatedTour.getEndLat());
                tour.endLonProperty().set(updatedTour.getEndLon());
            }
        }
    }

    public void deleteTour(TourViewModel tour) {
        tours.remove(tour);
    }
}

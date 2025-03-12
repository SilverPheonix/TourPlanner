package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.repository.TourRepository;
import at.technikum.studentmanagementsystem2.service.TourService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourViewModel {
    private final TourService service;
    private final ObservableList<Tour> tours = FXCollections.observableArrayList();

    public TourViewModel(TourService service) {
        this.service = service;
        loadTours();
    }
    public TourViewModel() {
        this.service = new TourService(); // Default service instance
        loadTours();
    }

    private void loadTours() {
        tours.setAll(service.getAllTours());
    }

    public ObservableList<Tour> getTours() {
        return tours;
    }

    public void addTour(Tour tour) {
        service.createTour(tour);
        loadTours();
    }

    public void removeTour(Tour tour) {
        service.deleteTour(tour.getId());
        loadTours();
    }

    public void saveTour(Tour newTour) {
    }

    public void deleteTour(Tour selectedTour) {

    }
}
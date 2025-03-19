package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.UUID;

public class TourViewModel {
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty description;
    private final StringProperty from;
    private final StringProperty to;
    private final StringProperty transportType;
    private final DoubleProperty distance;
    private final DoubleProperty estimatedTime;
    private final StringProperty imageUrl;
    private final ObservableList<TourLog> tourLogs = FXCollections.observableArrayList();

    public ObservableList<TourLog> getTourLogs() {
        return tourLogs;
    }

    public void loadTourLogs() {
        //tourLogs.setAll(TourLogService.getTourLogsByTourId(getId()));
    }


    public TourViewModel(Tour tour) {
        this.id = new SimpleStringProperty(tour.getId().toString());
        this.name = new SimpleStringProperty(tour.getName());
        this.description = new SimpleStringProperty(tour.getDescription());
        this.from = new SimpleStringProperty(tour.getFrom());
        this.to = new SimpleStringProperty(tour.getTo());
        this.transportType = new SimpleStringProperty(tour.getTransportType());
        this.distance = new SimpleDoubleProperty(tour.getDistance());
        this.estimatedTime = new SimpleDoubleProperty(tour.getEstimatedTime());
        this.imageUrl = new SimpleStringProperty(tour.getImageUrl());
    }

    public StringProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty fromProperty() { return from; }
    public StringProperty toProperty() { return to; }
    public StringProperty transportTypeProperty() { return transportType; }
    public DoubleProperty distanceProperty() { return distance; }
    public DoubleProperty estimatedTimeProperty() { return estimatedTime; }
    public StringProperty imageUrlProperty() { return imageUrl; }

    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getDescription() { return description.get(); }
    public String getFrom() { return from.get(); }
    public String getTo() { return to.get(); }
    public String getTransportType() { return transportType.get(); }
    public double getDistance() { return distance.get(); }
    public double getEstimatedTime() { return estimatedTime.get(); }
    public String getImageUrl() { return imageUrl.get(); }
}

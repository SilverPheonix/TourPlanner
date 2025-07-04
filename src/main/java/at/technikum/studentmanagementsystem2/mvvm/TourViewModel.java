package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
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
    private final IntegerProperty popularity;
    private final DoubleProperty childFriendliness;
    private final DoubleProperty startLat;
    private final DoubleProperty startLon;
    private final DoubleProperty endLat;
    private final DoubleProperty endLon;;

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
        this.popularity = new SimpleIntegerProperty(tour.getPopularity());
        this.childFriendliness = new SimpleDoubleProperty(tour.getChildFriendliness());
        this.startLat = new SimpleDoubleProperty(tour.getStartLat());
        this.startLon = new SimpleDoubleProperty(tour.getStartLon());
        this.endLat = new SimpleDoubleProperty(tour.getEndLat());
        this.endLon = new SimpleDoubleProperty(tour.getEndLon());
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

    public IntegerProperty popularityProperty() { return popularity; }
    public DoubleProperty childFriendlinessProperty() { return childFriendliness; }

    public DoubleProperty startLatProperty() { return startLat; }
    public DoubleProperty startLonProperty() { return startLon; }
    public DoubleProperty endLatProperty() { return endLat; }
    public DoubleProperty endLonProperty() { return endLon; }

    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getDescription() { return description.get(); }
    public String getFrom() { return from.get(); }
    public String getTo() { return to.get(); }
    public String getTransportType() { return transportType.get(); }
    public double getDistance() { return distance.get(); }
    public double getEstimatedTime() { return estimatedTime.get(); }
    public String getImageUrl() { return imageUrl.get(); }
    public int getPopularity() { return popularity.get(); }
    public double getChildFriendliness() { return childFriendliness.get(); }
    public double getStartLat() { return startLat.get(); }
    public double getStartLon() { return startLon.get(); }
    public double getEndLat() { return endLat.get(); }
    public double getEndLon() { return endLon.get(); }

    //Setter
    public void setName(String name) {
        this.name.set(name);
    }
    public void setDescription(String description) {
        this.description.set(description);
    }
    public void setFrom(String from) {
        this.from.set(from);
    }
    public void setTo(String to) {
        this.to.set(to);
    }
    public void setTransportType(String transportType) {
        this.transportType.set(transportType);
    }
    public void setDistance(double distance) {
        this.distance.set(distance);
    }
    public void setEstimatedTime(double time) {
        this.estimatedTime.set(time);
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }
    public void setPopularity(int popularity) { this.popularity.set(popularity); }
    public void setChildFriendliness( double childFriendliness) { this.childFriendliness.set(childFriendliness); }
    public void setstartLat(double startlat) { this.startLat.set(startlat); }
    public void setstartLon(double startlon) { this.startLon.set(startlon); }
    public void setendLat(double endlat) { this.endLat.set(endlat); }
    public void setendLon(double endlon) { this.endLon.set(endlon); }

    public Tour toTour() {
        return new Tour(
                UUID.fromString(getId()),
                getName(),
                getDescription(),
                getFrom(),
                getTo(),
                getTransportType(),
                getDistance(),
                getEstimatedTime(),
                getImageUrl(),
                getPopularity(),
                getChildFriendliness(),
                getStartLat(),
                getStartLon(),
                getEndLat(),
                getEndLon()
        );
    }

    public boolean hasCoordinates() {
        return startLat.get() != 0 && startLon.get() != 0 && endLat.get() != 0 && endLon.get() != 0;
    }

}

package at.technikum.studentmanagementsystem2.models;

import javafx.beans.property.*;

import java.util.UUID;

public class Tour {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final StringProperty transportType = new SimpleStringProperty();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final DoubleProperty estimatedTime = new SimpleDoubleProperty();
    private final ObjectProperty<UUID> id = new SimpleObjectProperty<>();
    private final StringProperty imageUrl = new SimpleStringProperty();

    public Tour(UUID id, String name, String description, String from, String to, String transportType,
                double distance, double estimatedTime, String imageUrl) {
        this.id.set(id);
        this.name.set(name);
        this.description.set(description);
        this.from.set(from);
        this.to.set(to);
        this.transportType.set(transportType);
        this.distance.set(distance);
        this.estimatedTime.set(estimatedTime);
        this.imageUrl.set(imageUrl);
    }

    // ✅ Getters for JavaFX Properties
    public StringProperty nameProperty() { return name; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty fromProperty() { return from; }
    public StringProperty toProperty() { return to; }
    public StringProperty transportTypeProperty() { return transportType; }
    public DoubleProperty distanceProperty() { return distance; }
    public DoubleProperty estimatedTimeProperty() { return estimatedTime; }
    public ObjectProperty<UUID> idProperty() { return id; }
    public StringProperty imageUrlProperty() { return imageUrl; }

    // ✅ Regular getters (for logic)
    public UUID getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getDescription() { return description.get(); }
    public String getFrom() { return from.get(); }
    public String getTo() { return to.get(); }
    public String getTransportType() { return transportType.get(); }
    public double getDistance() { return distance.get(); }
    public double getEstimatedTime() { return estimatedTime.get(); }
    public String getImageUrl() { return imageUrl.get(); }

    // ✅ Setters (for logic)
    public void setName(String name) { this.name.set(name); }
    public void setDescription(String description) { this.description.set(description); }
    public void setFrom(String from) { this.from.set(from); }
    public void setTo(String to) { this.to.set(to); }
    public void setTransportType(String transportType) { this.transportType.set(transportType); }
    public void setDistance(double distance) { this.distance.set(distance); }
    public void setEstimatedTime(double estimatedTime) { this.estimatedTime.set(estimatedTime); }
    public void setImageUrl(String imageUrl) { this.imageUrl.set(imageUrl); }
}

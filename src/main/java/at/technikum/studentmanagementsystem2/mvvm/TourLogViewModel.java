package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.TourLog;
import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class TourLogViewModel {
    private final ObjectProperty<UUID> id;
    private final ObjectProperty<UUID> tourId;
    private final ObjectProperty<LocalDateTime> dateTime;
    private final StringProperty comment;
    private final StringProperty difficulty;
    private final DoubleProperty totalDistance;
    private final DoubleProperty totalTime;
    private final IntegerProperty rating;

    public TourLogViewModel(TourLog tourLog) {
        this.id = new SimpleObjectProperty<>(tourLog.getId());
        this.tourId = new SimpleObjectProperty<>(tourLog.getTourId());
        this.dateTime = new SimpleObjectProperty<>(tourLog.getDateTime());
        this.comment = new SimpleStringProperty(tourLog.getComment());
        this.difficulty = new SimpleStringProperty(tourLog.getDifficulty());
        this.totalDistance = new SimpleDoubleProperty(tourLog.getTotalDistance());
        this.totalTime = new SimpleDoubleProperty(tourLog.getTotalTime());
        this.rating = new SimpleIntegerProperty(tourLog.getRating());
    }

    // 📌 JavaFX Properties für Binding in TableView
    public ObjectProperty<LocalDateTime> dateTimeProperty() { return dateTime; }
    public StringProperty commentProperty() { return comment; }
    public StringProperty difficultyProperty() { return difficulty; }
    public DoubleProperty totalDistanceProperty() { return totalDistance; }
    public DoubleProperty totalTimeProperty() { return totalTime; }
    public IntegerProperty ratingProperty() { return rating; }

    // Getter
    public UUID getId() { return id.get(); }
    public UUID getTourId() { return tourId.get(); }
    public LocalDateTime getDateTime() { return dateTime.get(); }
    public String getComment() { return comment.get(); }
    public String getDifficulty() { return difficulty.get(); }
    public double getTotalDistance() { return totalDistance.get(); }
    public double getTotalTime() { return totalTime.get(); }
    public int getRating() { return rating.get(); }

    // Setter
    public void setDateTime(LocalDateTime dateTime) { this.dateTime.set(dateTime); }
    public void setComment(String comment) { this.comment.set(comment); }
    public void setDifficulty(String difficulty) { this.difficulty.set(difficulty); }
    public void setTotalDistance(double totalDistance) { this.totalDistance.set(totalDistance); }
    public void setTotalTime(double totalTime) { this.totalTime.set(totalTime); }
    public void setRating(int rating) { this.rating.set(rating); }
}

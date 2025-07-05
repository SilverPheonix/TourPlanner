package at.technikum.studentmanagementsystem2.dto;

import java.time.LocalDateTime;
import java.util.UUID;

// Beispiel: TourLogDTO
public class TourLogDTO {
    private UUID id;
    private LocalDateTime dateTime;
    private String comment;
    private String difficulty;
    private double totalDistance;
    private double totalTime;
    private int rating;

    // Getter + Setter
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public double getTotalDistance() { return totalDistance; }
    public void setTotalDistance(double totalDistance) { this.totalDistance = totalDistance; }

    public double getTotalTime() { return totalTime; }
    public void setTotalTime(double totalTime) { this.totalTime = totalTime; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}


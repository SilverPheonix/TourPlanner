package at.technikum.studentmanagementsystem2.dto;

import java.util.List;
import java.util.UUID;

public class TourExportDTO {
    private UUID id;
    private String name;
    private String description;
    private String from;
    private String to;
    private String transportType;
    private double distance;
    private double estimatedTime;
    private String imageUrl;
    private int popularity;
    private double childFriendliness;
    private double startLat;
    private double startLon;
    private double endLat;
    private double endLon;
    private List<TourLogDTO> tourLogs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getChildFriendliness() {
        return childFriendliness;
    }

    public void setChildFriendliness(double childFriendliness) {
        this.childFriendliness = childFriendliness;
    }

    public double getStartLon() {
        return startLon;
    }

    public void setStartLon(double startLon) {
        this.startLon = startLon;
    }

    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public double getEndLat() {
        return endLat;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }

    public double getEndLon() {
        return endLon;
    }

    public void setEndLon(double endLon) {
        this.endLon = endLon;
    }

    public List<TourLogDTO> getTourLogs() {
        return tourLogs;
    }

    public void setTourLogs(List<TourLogDTO> tourLogs) {
        this.tourLogs = tourLogs;
    }
}

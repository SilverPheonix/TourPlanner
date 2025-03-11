package at.technikum.studentmanagementsystem2.models;

import java.util.UUID;

public class Tour {
    private UUID id;
    private String name;
    private String description;
    private String from;
    private String to;
    private String transportType;
    private double distance;
    private double estimatedTime;
    private String routeImageUrl; // URL oder Pfad zum Tour-Bild

    public Tour(UUID id, String name, String description, String to, String from, String transportType, double distance, double estimatedTime, String routeImageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.to = to;
        this.from = from;
        this.transportType = transportType;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.routeImageUrl = routeImageUrl;
    }

    // Konstruktoren, Getter und Setter

    public String getRouteImageUrl() {
        return routeImageUrl;
    }

    public void setRouteImageUrl(String routeImageUrl) {
        this.routeImageUrl = routeImageUrl;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

}

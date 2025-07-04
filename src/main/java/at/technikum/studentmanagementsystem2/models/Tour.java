package at.technikum.studentmanagementsystem2.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String description;

    @Column(name = "`from`") // Escaping the reserved keyword
    private String from;

    @Column(name = "`to`")
    private String to;


    @Column(name = "transport_type")
    private String transportType;

    private double distance;

    @Column(name = "estimated_time")
    private double estimatedTime;

    @Column(name = "image_url")
    private String imageUrl;


    //Compuated values
    @Column(name = "popularity")
    private int popularity;

    @Column(name = "child_friendliness")
    private double childFriendliness;

    //Map Values
    @Column(name = "startLat")
    private double startLat;
    @Column(name = "startLon")
    private double startLon;
    @Column(name = "endLat")
    private double endLat;
    @Column(name = "endLon")
    private double endLon;


    // No-arg constructor for JPA
    public Tour() {}

    // Parameterized constructor
    public Tour(UUID id, String name, String description, String from, String to, String transportType,
                double distance, double estimatedTime, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.from = from;
        this.to = to;
        this.transportType = transportType;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.imageUrl = imageUrl;

    }
    // aditional paramater  constructor
    public Tour(UUID id, String name, String description, String from, String to, String transportType,
                double distance, double estimatedTime, String imageUrl, Integer popularity, double childFriendliness,
                double startLat, double startLon, double endLat, double endLon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.from = from;
        this.to = to;
        this.transportType = transportType;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.imageUrl = imageUrl;
        this.popularity = popularity;
        this.childFriendliness = childFriendliness;
        this.startLat = startLat;
        this.startLon = startLon;
        this.endLat = endLat;
        this.endLon = endLon;

    }

    // Getters and setters for JPA fields
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getTransportType() { return transportType; }
    public void setTransportType(String transportType) { this.transportType = transportType; }

    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }

    public double getEstimatedTime() { return estimatedTime; }
    public void setEstimatedTime(double estimatedTime) { this.estimatedTime = estimatedTime; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public double getChildFriendliness() {
        return childFriendliness;
    }

    public void setChildFriendliness(double childFriendliness) {
        this.childFriendliness = childFriendliness;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public double getEndLon() {
        return endLon;
    }

    public void setEndLon(double endLon) {
        this.endLon = endLon;
    }

    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public double getStartLon() {
        return startLon;
    }

    public void setStartLon(double startLon) {
        this.startLon = startLon;
    }

    public double getEndLat() {
        return endLat;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }
}
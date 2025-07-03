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
}
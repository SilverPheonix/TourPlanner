package at.technikum.studentmanagementsystem2.service;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.repository.TourRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class TourService {
    private final TourRepository repository;
    private final RestTemplate restTemplate;

    public TourService(TourRepository repository) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
    }

    public Tour createTour(Tour tour) {
        // OpenRouteService API aufrufen
        String apiUrl = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=YOUR_API_KEY&start="
                + tour.getFrom() + "&end=" + tour.getTo();

        // API-Response parsen und Distanz/Zeit setzen
        String response = restTemplate.getForObject(apiUrl, String.class);
        // (Hier JSON auslesen und Werte setzen)
        tour.setDistance(10.5); // Dummy-Wert ersetzen
        tour.setEstimatedTime(20.0);

        return repository.save(tour);
    }

    public List<Tour> getAllTours() {
        return repository.findAll();
    }

    public void deleteTour(UUID id) {
        repository.deleteById(id);
    }
}

package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.Tour;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TourTableViewModelTest {

    private TourTableViewModel tourTableViewModel;
    private TourViewModel newTourViewModel;

    @BeforeEach
    void setUp() {
        tourTableViewModel = new TourTableViewModel();

        // Neue Tour erstellen
        Tour newTour = new Tour(
                UUID.randomUUID(),
                "Neue Tour",
                "Eine neue Beschreibung",
                "Salzburg",
                "Innsbruck",
                "Auto",
                300.0,
                180.0,
                "neues_bild.jpg",
                4,
                4.5,
                47.8095,
                13.0550,
                47.2692,
                11.4041
        );

        newTourViewModel = new TourViewModel(newTour);
    }

    @Test
    void testAddTour() {
        int initialSize = tourTableViewModel.getTours().size();
        tourTableViewModel.addTour(newTourViewModel);
        ObservableList<TourViewModel> tours = tourTableViewModel.getTours();

        assertEquals(initialSize + 1, tours.size());
        assertTrue(tours.contains(newTourViewModel));
    }

    @Test
    void testUpdateTour() {
        tourTableViewModel.addTour(newTourViewModel);

        // Werte ändern
        newTourViewModel.setName("Geänderte Tour");
        newTourViewModel.setDescription("Geänderte Beschreibung");
        newTourViewModel.setDistance(999.0);

        // Update ausführen
        tourTableViewModel.updateTour(newTourViewModel);

        TourViewModel updated = tourTableViewModel.getTours().stream()
                .filter(t -> t.getId().equals(newTourViewModel.getId()))
                .findFirst()
                .orElse(null);

        assertNotNull(updated);
        assertEquals("Geänderte Tour", updated.getName());
        assertEquals("Geänderte Beschreibung", updated.getDescription());
        assertEquals(999.0, updated.getDistance());
    }

    @Test
    void testDeleteTour() {
        tourTableViewModel.addTour(newTourViewModel);
        assertTrue(tourTableViewModel.getTours().contains(newTourViewModel));

        tourTableViewModel.deleteTour(newTourViewModel);
        assertFalse(tourTableViewModel.getTours().contains(newTourViewModel));
    }
}

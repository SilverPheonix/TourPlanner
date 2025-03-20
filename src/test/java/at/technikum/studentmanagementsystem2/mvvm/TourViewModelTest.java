package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class TourViewModelTest {

    private TourViewModel tourViewModel;

    @BeforeEach
    void setUp() {
        // Erstellen einer Test-Tour mit einer zufälligen ID
        Tour testTour = new Tour(
                UUID.randomUUID(),
                "Test Tour",
                "Eine schöne Test-Tour",
                "Wien",
                "Graz",
                "Zug",
                200.0,
                120.0,
                "test_image_url"
        );
        tourViewModel = new TourViewModel(testTour);
    }

    @Test
    void testGetters() {
        assertNotNull(tourViewModel.getId());
        assertEquals("Test Tour", tourViewModel.getName());
        assertEquals("Eine schöne Test-Tour", tourViewModel.getDescription());
        assertEquals("Wien", tourViewModel.getFrom());
        assertEquals("Graz", tourViewModel.getTo());
        assertEquals("Zug", tourViewModel.getTransportType());
        assertEquals(200.0, tourViewModel.getDistance());
        assertEquals(120.0, tourViewModel.getEstimatedTime());
        assertEquals("test_image_url", tourViewModel.getImageUrl());
    }

    @Test
    void testProperties() {
        assertEquals("Test Tour", tourViewModel.nameProperty().get());
        assertEquals("Eine schöne Test-Tour", tourViewModel.descriptionProperty().get());
        assertEquals("Wien", tourViewModel.fromProperty().get());
        assertEquals("Graz", tourViewModel.toProperty().get());
        assertEquals("Zug", tourViewModel.transportTypeProperty().get());
        assertEquals(200.0, tourViewModel.distanceProperty().get());
        assertEquals(120.0, tourViewModel.estimatedTimeProperty().get());
        assertEquals("test_image_url", tourViewModel.imageUrlProperty().get());
    }

    @Test
    void testLoadTourLogs() {
        // Da loadTourLogs() momentan leer ist, können wir diesen Test nur vorbereiten.
        // Wenn es implementiert wird, könnten wir hier die Tour Logs laden und überprüfen.
        tourViewModel.loadTourLogs();
        assertTrue(tourViewModel.getTourLogs().isEmpty()); // Es sollten keine Logs geladen sein.
    }

    @Test
    void testAddTourLog() {
        // Ein TourLog kann hinzugefügt werden (Testen der TourLog-Listeneigenschaften)
        UUID tourLogId = UUID.randomUUID();
        TourLog tourLog = new TourLog(tourLogId, UUID.randomUUID(), null, "Kommentar", "schwierig", 100.0, 10.0, 4);
        tourViewModel.getTourLogs().add(tourLog);

        assertEquals(1, tourViewModel.getTourLogs().size());
        assertEquals(tourLogId, tourViewModel.getTourLogs().get(0).getId());
    }

    @Test
    void testTourLogEmptyList() {
        // Wenn keine TourLogs hinzugefügt wurden, sollte die Liste leer sein
        assertTrue(tourViewModel.getTourLogs().isEmpty());
    }
}

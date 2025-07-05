package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class TourViewModelTest {

    private TourViewModel tourViewModel;
    private UUID testTourId;

    @BeforeEach
    void setUp() {
        testTourId = UUID.randomUUID();

        Tour testTour = new Tour(
                testTourId,
                "Test Tour",
                "Eine schöne Test-Tour",
                "Wien",
                "Graz",
                "Zug",
                200.0,
                120.0,
                "test_image_url",
                5,           // Popularity
                3.5,         // ChildFriendliness
                48.2082,     // StartLat
                16.3738,     // StartLon
                47.0707,     // EndLat
                15.4395      // EndLon
        );
        tourViewModel = new TourViewModel(testTour);
    }

    @Test
    void testGetters() {
        assertEquals(testTourId.toString(), tourViewModel.getId());
        assertEquals("Test Tour", tourViewModel.getName());
        assertEquals("Eine schöne Test-Tour", tourViewModel.getDescription());
        assertEquals("Wien", tourViewModel.getFrom());
        assertEquals("Graz", tourViewModel.getTo());
        assertEquals("Zug", tourViewModel.getTransportType());
        assertEquals(200.0, tourViewModel.getDistance());
        assertEquals(120.0, tourViewModel.getEstimatedTime());
        assertEquals("test_image_url", tourViewModel.getImageUrl());
        assertEquals(5, tourViewModel.getPopularity());
        assertEquals(3.5, tourViewModel.getChildFriendliness());
        assertEquals(48.2082, tourViewModel.getStartLat());
        assertEquals(16.3738, tourViewModel.getStartLon());
        assertEquals(47.0707, tourViewModel.getEndLat());
        assertEquals(15.4395, tourViewModel.getEndLon());
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
        assertEquals(5, tourViewModel.popularityProperty().get());
        assertEquals(3.5, tourViewModel.childFriendlinessProperty().get());
        assertEquals(48.2082, tourViewModel.startLatProperty().get());
        assertEquals(16.3738, tourViewModel.startLonProperty().get());
        assertEquals(47.0707, tourViewModel.endLatProperty().get());
        assertEquals(15.4395, tourViewModel.endLonProperty().get());
    }

    @Test
    void testHasCoordinates() {
        assertTrue(tourViewModel.hasCoordinates());

        tourViewModel.setstartLat(0.0);
        assertFalse(tourViewModel.hasCoordinates());
    }

    @Test
    void testToTour() {
        Tour convertedTour = tourViewModel.toTour();
        assertEquals(testTourId, convertedTour.getId());
        assertEquals("Test Tour", convertedTour.getName());
        assertEquals(3.5, convertedTour.getChildFriendliness());
        assertEquals(48.2082, convertedTour.getStartLat());
        assertEquals(15.4395, convertedTour.getEndLon());
    }

    @Test
    void testLoadTourLogs() {
        tourViewModel.loadTourLogs();
        assertTrue(tourViewModel.getTourLogs().isEmpty());
    }

    @Test
    void testAddTourLog() {
        UUID tourLogId = UUID.randomUUID();
        TourLog tourLog = new TourLog(tourLogId, new Tour(), null, "Kommentar", "schwierig", 100.0, 10.0, 4);
        tourViewModel.getTourLogs().add(tourLog);

        assertEquals(1, tourViewModel.getTourLogs().size());
        assertEquals(tourLogId, tourViewModel.getTourLogs().get(0).getId());
    }

    @Test
    void testTourLogEmptyList() {
        assertTrue(tourViewModel.getTourLogs().isEmpty());
    }
}

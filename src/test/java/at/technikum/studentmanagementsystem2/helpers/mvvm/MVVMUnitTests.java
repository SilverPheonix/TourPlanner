package at.technikum.studentmanagementsystem2.helpers.mvvm;
import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.mvvm.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class MVVMUnitTests {
    private TourLogTableViewModel tourLogTableViewModel;
    private TourTableViewModel tourTableViewModel;

    @BeforeEach
    void setUp() {
        tourLogTableViewModel = new TourLogTableViewModel();
        tourTableViewModel = new TourTableViewModel();
    }

    @Test
    void testAddTourLog() {
        TourLogViewModel log = new TourLogViewModel(new TourLog(
                UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(),
                "Great Trip", "Medium", 15.0, 120.0, 4));
        tourLogTableViewModel.addTourLog(log);
        assertTrue(tourLogTableViewModel.getTourLogs().contains(log));
    }

    @Test
    void testAddTour() {
        TourViewModel tour = new TourViewModel(new Tour(
                UUID.randomUUID(), "Test Tour", "Description",
                "Vienna", "Graz", "Train", 200.0, 120.0, "image_url"));
        tourTableViewModel.addTour(tour);
        assertTrue(tourTableViewModel.getTours().contains(tour));
    }

    @Test
    void testRemoveTourLog() {
        TourLogViewModel log = new TourLogViewModel(new TourLog(
                UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(),
                "Great Trip", "Medium", 15.0, 120.0, 4));
        tourLogTableViewModel.addTourLog(log);
        tourLogTableViewModel.getTourLogs().remove(log);
        assertFalse(tourLogTableViewModel.getTourLogs().contains(log));
    }

    @Test
    void testGetTourLogs() {
        assertNotNull(tourLogTableViewModel.getTourLogs());
        assertTrue(tourLogTableViewModel.getTourLogs().isEmpty());
    }

    @Test
    void testRemoveTour() {
        TourViewModel tour = new TourViewModel(new Tour(
                UUID.randomUUID(), "Test Tour", "Description",
                "Vienna", "Graz", "Train", 200.0, 120.0, "image_url"));
        tourTableViewModel.addTour(tour);
        tourTableViewModel.getTours().remove(tour);
        assertFalse(tourTableViewModel.getTours().contains(tour));
    }

    @Test
    void testGetTours() {
        assertNotNull(tourTableViewModel.getTours());
        assertTrue(tourTableViewModel.getTours().isEmpty());
    }

    @Test
    void testTourLogViewModelProperties() {
        TourLog log = new TourLog(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(),
                "Great Trip", "Medium", 15.0, 120.0, 4);
        TourLogViewModel logViewModel = new TourLogViewModel(log);

        assertEquals("Great Trip", logViewModel.commentProperty().get());
        assertEquals("Medium", logViewModel.difficultyProperty().get());
        assertEquals(15.0, logViewModel.totalDistanceProperty().get());
        assertEquals(120.0, logViewModel.totalTimeProperty().get());
        assertEquals(4, logViewModel.ratingProperty().get());
    }


    @Test
    void testTourViewModelProperties() {
        Tour tour = new Tour(UUID.randomUUID(), "Test Tour", "Description",
                "Vienna", "Graz", "Train", 200.0, 120.0, "image_url");
        TourViewModel tourViewModel = new TourViewModel(tour);

        assertEquals("Test Tour", tourViewModel.nameProperty().get());
        assertEquals("Description", tourViewModel.descriptionProperty().get());
        assertEquals("Vienna", tourViewModel.fromProperty().get());
        assertEquals("Graz", tourViewModel.toProperty().get());
        assertEquals("Train", tourViewModel.transportTypeProperty().get());
        assertEquals(200.0, tourViewModel.distanceProperty().get());
        assertEquals(120.0, tourViewModel.estimatedTimeProperty().get());
    }
}

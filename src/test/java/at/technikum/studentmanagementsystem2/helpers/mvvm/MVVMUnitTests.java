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
        // Create a Tour instance with valid parameters
        Tour tour = new Tour(
                UUID.randomUUID(), // Assume Tour now allows UUID for id
                "Test Tour",
                "Description",
                "Vienna",
                "Graz",
                "Train",
                200.0,
                120.0,
                "image_url"
        );

        // Create TourLog
        TourLogViewModel log = new TourLogViewModel(new TourLog(
                UUID.randomUUID(), // id
                tour,              // Pass the Tour object here
                LocalDateTime.now(),
                "Great Trip",
                "Medium",
                15.0,
                120.0,
                4
        ));

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
    void testGetTourLogs() {
        assertNotNull(tourLogTableViewModel.getTourLogs());
        assertTrue(tourLogTableViewModel.getTourLogs().isEmpty());
    }

    @Test
    void testRemoveTour() {
        TourViewModel tour = new TourViewModel(new Tour(
                UUID.randomUUID(), // Generates a random UUID (id)
                "Test Tour",
                "Description",
                "Vienna",
                "Graz",
                "Train",
                200.0,
                120.0,
                "image_url"
        ));

        tourTableViewModel.addTour(tour); // Add the tour
        tourTableViewModel.getTours().remove(tour); // Remove it
        assertFalse(tourTableViewModel.getTours().contains(tour)); // Assert it's no longer present
    }


    @Test
    void testGetTours() {
        assertNotNull(tourTableViewModel.getTours());
    }

    @Test
    void testTourLogViewModelProperties() {
        // Create a Tour object
        Tour tour = new Tour(
                UUID.randomUUID(),  // id
                "Seaside Escape",   // name
                "A relaxing trip",  // description
                "Miami",            // from
                "Key West",         // to
                "Car",              // transportType
                150.5,              // distance
                180.0,              // estimatedTime
                "seaside_image.jpg" // imageUrl
        );

        // Create a TourLog object with the Tour object
        TourLog log = new TourLog(
                UUID.randomUUID(),  // id
                tour,               // Tour object
                LocalDateTime.now(),// dateTime
                "Incredible experience!", // comment
                "Easy",             // difficulty
                150.5,              // totalDistance
                180.0,              // totalTime
                5                   // rating
        );

        // Wrap it in a TourLogViewModel
        TourLogViewModel logViewModel = new TourLogViewModel(log);

        // Validate the ViewModel properties
        assertEquals("Incredible experience!", logViewModel.commentProperty().get());
        assertEquals("Easy", logViewModel.difficultyProperty().get());
        assertEquals(150.5, logViewModel.totalDistanceProperty().get());
        assertEquals(180.0, logViewModel.totalTimeProperty().get());
        assertEquals(5, logViewModel.ratingProperty().get());
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

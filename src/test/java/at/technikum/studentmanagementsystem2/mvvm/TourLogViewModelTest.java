package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TourLogViewModelTest {

    private TourLogViewModel tourLogViewModel;
    private TourLog originalTourLog;
    private Tour tour;

    @BeforeEach
    void setUp() {
        // Beispiel-Tour
        tour = new Tour();
        tour.setId(UUID.randomUUID());

        // Beispiel-TourLog
        originalTourLog = new TourLog(
                UUID.randomUUID(),
                tour,
                LocalDateTime.of(2023, 10, 10, 12, 0),
                "Test Comment",
                "medium",
                100.0,
                50.0,
                5
        );

        tourLogViewModel = new TourLogViewModel(originalTourLog);
    }

    @Test
    void testGetters() {
        assertEquals(originalTourLog.getId(), tourLogViewModel.getId());
        assertEquals(originalTourLog.getTour().getId(), tourLogViewModel.getTourId());
        assertEquals(originalTourLog.getDateTime(), tourLogViewModel.getDateTime());
        assertEquals(originalTourLog.getComment(), tourLogViewModel.getComment());
        assertEquals(originalTourLog.getDifficulty(), tourLogViewModel.getDifficulty());
        assertEquals(originalTourLog.getTotalDistance(), tourLogViewModel.getTotalDistance());
        assertEquals(originalTourLog.getTotalTime(), tourLogViewModel.getTotalTime());
        assertEquals(originalTourLog.getRating(), tourLogViewModel.getRating());
    }

    @Test
    void testSetters() {
        LocalDateTime newDateTime = LocalDateTime.of(2024, 1, 1, 10, 0);
        tourLogViewModel.setDateTime(newDateTime);
        assertEquals(newDateTime, tourLogViewModel.getDateTime());

        tourLogViewModel.setComment("Updated comment");
        assertEquals("Updated comment", tourLogViewModel.getComment());

        tourLogViewModel.setDifficulty("hard");
        assertEquals("hard", tourLogViewModel.getDifficulty());

        tourLogViewModel.setTotalDistance(150.0);
        assertEquals(150.0, tourLogViewModel.getTotalDistance());

        tourLogViewModel.setTotalTime(75.0);
        assertEquals(75.0, tourLogViewModel.getTotalTime());

        tourLogViewModel.setRating(3);
        assertEquals(3, tourLogViewModel.getRating());
    }

    @Test
    void testJavaFxProperties() {
        assertNotNull(tourLogViewModel.dateTimeProperty());
        assertNotNull(tourLogViewModel.commentProperty());
        assertNotNull(tourLogViewModel.difficultyProperty());
        assertNotNull(tourLogViewModel.totalDistanceProperty());
        assertNotNull(tourLogViewModel.totalTimeProperty());
        assertNotNull(tourLogViewModel.ratingProperty());
    }

    @Test
    void testToTourLog() {
        tourLogViewModel.setComment("Log conversion");
        tourLogViewModel.setDifficulty("easy");
        tourLogViewModel.setTotalDistance(200.0);
        tourLogViewModel.setTotalTime(90.0);
        tourLogViewModel.setRating(4);

        TourLog converted = tourLogViewModel.toTourLog(tour);

        assertEquals(tourLogViewModel.getId(), converted.getId());
        assertEquals(tour.getId(), converted.getTour().getId());
        assertEquals("Log conversion", converted.getComment());
        assertEquals("easy", converted.getDifficulty());
        assertEquals(200.0, converted.getTotalDistance());
        assertEquals(90.0, converted.getTotalTime());
        assertEquals(4, converted.getRating());

        // Das Datum wird zur Laufzeit neu gesetzt
        assertNotNull(converted.getDateTime());
    }
}

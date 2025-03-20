package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.TourLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TourLogViewModelTest {
    private TourLogViewModel tourLogViewModel;
    private TourLog tourLog;

    @BeforeEach
    void setUp() {
        // Erstelle eine TourLog-Instanz für die Tests
        UUID id = UUID.randomUUID();
        UUID tourId = UUID.randomUUID();
        LocalDateTime dateTime = LocalDateTime.now();
        tourLog = new TourLog(id, tourId, dateTime, "Test Comment", "medium", 100.0, 50.0, 5);

        // Erstelle das TourLogViewModel mit der TourLog-Instanz
        tourLogViewModel = new TourLogViewModel(tourLog);
    }

    @Test
    void testGetter() {
        // Überprüfe, ob die Getter die richtigen Werte zurückgeben
        assertEquals(tourLog.getId(), tourLogViewModel.getId());
        assertEquals(tourLog.getTourId(), tourLogViewModel.getTourId());
        assertEquals(tourLog.getDateTime(), tourLogViewModel.getDateTime());
        assertEquals(tourLog.getComment(), tourLogViewModel.getComment());
        assertEquals(tourLog.getDifficulty(), tourLogViewModel.getDifficulty());
        assertEquals(tourLog.getTotalDistance(), tourLogViewModel.getTotalDistance());
        assertEquals(tourLog.getTotalTime(), tourLogViewModel.getTotalTime());
        assertEquals(tourLog.getRating(), tourLogViewModel.getRating());
    }

    @Test
    void testSetter() {
        // Setze neue Werte und prüfe, ob sie gesetzt wurden
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(1);
        tourLogViewModel.setDateTime(newDateTime);
        assertEquals(newDateTime, tourLogViewModel.getDateTime());

        tourLogViewModel.setComment("Updated Comment");
        assertEquals("Updated Comment", tourLogViewModel.getComment());

        tourLogViewModel.setDifficulty("hard");
        assertEquals("hard", tourLogViewModel.getDifficulty());

        tourLogViewModel.setTotalDistance(120.0);
        assertEquals(120.0, tourLogViewModel.getTotalDistance());

        tourLogViewModel.setTotalTime(60.0);
        assertEquals(60.0, tourLogViewModel.getTotalTime());

        tourLogViewModel.setRating(4);
        assertEquals(4, tourLogViewModel.getRating());
    }

    @Test
    void testProperties() {
        // Überprüfe, ob die JavaFX-Properties korrekt initialisiert sind
        assertNotNull(tourLogViewModel.dateTimeProperty());
        assertNotNull(tourLogViewModel.commentProperty());
        assertNotNull(tourLogViewModel.difficultyProperty());
        assertNotNull(tourLogViewModel.totalDistanceProperty());
        assertNotNull(tourLogViewModel.totalTimeProperty());
        assertNotNull(tourLogViewModel.ratingProperty());
    }
}

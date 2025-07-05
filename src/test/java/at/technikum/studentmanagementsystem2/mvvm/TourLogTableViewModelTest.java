package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TourLogTableViewModelTest {

    private TourLogTableViewModel tourLogTableViewModel;
    private Tour tour;

    @BeforeEach
    void setUp() {
        tourLogTableViewModel = new TourLogTableViewModel();

        // Beispiel-Tour für TourLogViewModel
        tour = new Tour();
        tour.setId(UUID.randomUUID());
    }

    private TourLogViewModel createExampleTourLogViewModel(UUID id) {
        return new TourLogViewModel(
                new TourLog(
                        id,
                        tour,
                        LocalDateTime.of(2024, 1, 1, 12, 0),
                        "Comment",
                        "easy",
                        100.0,
                        50.0,
                        3
                )
        );
    }

    @Test
    void testAddTourLog() {
        TourLogViewModel tourLogViewModel = createExampleTourLogViewModel(UUID.randomUUID());
        tourLogTableViewModel.addTourLog(tourLogViewModel);

        assertEquals(1, tourLogTableViewModel.getTourLogs().size());
        assertEquals(tourLogViewModel, tourLogTableViewModel.getTourLogs().get(0));
    }

    @Test
    void testDeleteTourLog() {
        TourLogViewModel tourLogViewModel = createExampleTourLogViewModel(UUID.randomUUID());
        tourLogTableViewModel.addTourLog(tourLogViewModel);
        assertEquals(1, tourLogTableViewModel.getTourLogs().size());

        tourLogTableViewModel.deleteTourLog(tourLogViewModel);
        assertEquals(0, tourLogTableViewModel.getTourLogs().size());
    }

    @Test
    void testUpdateTourLog() {
        UUID id = UUID.randomUUID();
        TourLogViewModel originalLog = createExampleTourLogViewModel(id);
        tourLogTableViewModel.addTourLog(originalLog);

        // Neues Log mit gleichem ID, aber veränderten Daten
        TourLogViewModel updatedLog = createExampleTourLogViewModel(id);
        updatedLog.setComment("Updated Comment");
        updatedLog.setDifficulty("hard");
        updatedLog.setRating(5);

        tourLogTableViewModel.updateTourLog(updatedLog);

        TourLogViewModel result = tourLogTableViewModel.getTourLogs().get(0);
        assertEquals("Updated Comment", result.getComment());
        assertEquals("hard", result.getDifficulty());
        assertEquals(5, result.getRating());
    }

    @Test
    void testUpdateTourLog_NotFound_DoesNothing() {
        TourLogViewModel log1 = createExampleTourLogViewModel(UUID.randomUUID());
        TourLogViewModel log2 = createExampleTourLogViewModel(UUID.randomUUID());

        tourLogTableViewModel.addTourLog(log1);
        tourLogTableViewModel.updateTourLog(log2); // ID existiert nicht -> kein Update

        assertEquals(log1.getComment(), tourLogTableViewModel.getTourLogs().get(0).getComment());
    }
}

package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.TourLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

class TourLogTableViewModelTest {
    private TourLogTableViewModel tourLogTableViewModel;

    @BeforeEach
    void setUp() {
        tourLogTableViewModel = new TourLogTableViewModel();
    }

    @Test
    void testAddTourLog() {
        TourLogViewModel tourLogViewModel = mock(TourLogViewModel.class);
        tourLogTableViewModel.addTourLog(tourLogViewModel);

        assertEquals(1, tourLogTableViewModel.getTourLogs().size());
    }

    @Test
    void testUpdateTourLog() {
        UUID id = UUID.randomUUID();
        TourLogViewModel originalTourLog = new TourLogViewModel(new TourLog(id, UUID.randomUUID(), LocalDateTime.now(), "Test Tour", "medium", 100, 10, 5));
        tourLogTableViewModel.addTourLog(originalTourLog);

        TourLogViewModel updatedTourLog = new TourLogViewModel(new TourLog(id, UUID.randomUUID(), LocalDateTime.now(), "Updated Tour", "hard", 150, 12, 4));
        tourLogTableViewModel.updateTourLog(updatedTourLog);

        assertEquals("Updated Tour", tourLogTableViewModel.getTourLogs().get(0).getComment());
        assertEquals("hard", tourLogTableViewModel.getTourLogs().get(0).getDifficulty());
    }

    @Test
    void testDeleteTourLog() {
        TourLogViewModel tourLogViewModel = mock(TourLogViewModel.class);
        tourLogTableViewModel.addTourLog(tourLogViewModel);
        tourLogTableViewModel.deleteTourLog(tourLogViewModel);

        assertEquals(0, tourLogTableViewModel.getTourLogs().size());
    }
}

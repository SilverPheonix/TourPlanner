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
    void testDeleteTourLog() {
        TourLogViewModel tourLogViewModel = mock(TourLogViewModel.class);
        tourLogTableViewModel.addTourLog(tourLogViewModel);
        tourLogTableViewModel.deleteTourLog(tourLogViewModel);

        assertEquals(0, tourLogTableViewModel.getTourLogs().size());
    }
}

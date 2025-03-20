package at.technikum.studentmanagementsystem2.mvvm;

import at.technikum.studentmanagementsystem2.models.Tour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class TourTableViewModelTest {
    private TourTableViewModel tourTableViewModel;

    @BeforeEach
    void setUp() {
        tourTableViewModel = new TourTableViewModel();
    }

    @Test
    void testAddTour() {
        TourViewModel tourViewModel = mock(TourViewModel.class);
        tourTableViewModel.addTour(tourViewModel);

        assertEquals(2, tourTableViewModel.getTours().size());
    }
}

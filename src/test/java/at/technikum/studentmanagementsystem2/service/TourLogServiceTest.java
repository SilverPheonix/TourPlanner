package at.technikum.studentmanagementsystem2.service;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.repository.TourLogRepository;
import at.technikum.studentmanagementsystem2.service.TourLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TourLogServiceTest {

    private TourLogRepository tourLogRepositoryMock;
    private TourLogService tourLogService;

    private UUID testTourId;
    private UUID testLogId;

    @BeforeEach
    void setUp() {
        tourLogRepositoryMock = mock(TourLogRepository.class);
        tourLogService = new TourLogService(tourLogRepositoryMock);

        testTourId = UUID.randomUUID();
        testLogId = UUID.randomUUID();
    }

    @Test
    void getTourLogsByTourId_FiltersCorrectly() {
        Tour tour = new Tour();
        tour.setId(testTourId);

        TourLog matchingLog = mock(TourLog.class);
        when(matchingLog.getTour()).thenReturn(tour);

        TourLog nonMatchingLog = mock(TourLog.class);
        when(nonMatchingLog.getTour()).thenReturn(null);

        TourLog logWithOtherTour = mock(TourLog.class);
        Tour otherTour = new Tour();
        otherTour.setId(UUID.randomUUID());
        when(logWithOtherTour.getTour()).thenReturn(otherTour);

        when(tourLogRepositoryMock.findAll()).thenReturn(List.of(matchingLog, nonMatchingLog, logWithOtherTour));

        List<TourLog> result = tourLogService.getTourLogsByTourId(testTourId);

        assertEquals(1, result.size());
        assertTrue(result.contains(matchingLog));
        verify(tourLogRepositoryMock).findAll();
    }

    @Test
    void saveTourLog_CallsRepositorySave() {
        TourLog tourLog = mock(TourLog.class);
        when(tourLog.getTour()).thenReturn(null);

        tourLogService.saveTourLog(tourLog);

        verify(tourLogRepositoryMock).save(tourLog);
    }

    @Test
    void deleteTourLog_CallsRepositoryDeleteById() {
        tourLogService.deleteTourLog(testLogId);

        verify(tourLogRepositoryMock).deleteById(testLogId);
    }
}

package at.technikum.studentmanagementsystem2.service;

import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.mvvm.TourViewModel;
import at.technikum.studentmanagementsystem2.repository.TourRepository;
import at.technikum.studentmanagementsystem2.service.TourLogService;
import at.technikum.studentmanagementsystem2.service.TourService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TourServiceTest {

    private TourRepository tourRepositoryMock;
    private TourLogService tourLogServiceMock;
    private TourService tourService;

    private UUID testTourId;
    private Tour testTour;

    @BeforeEach
    void setUp() {
        tourRepositoryMock = mock(TourRepository.class);
        tourLogServiceMock = mock(TourLogService.class);
        tourService = new TourService(tourRepositoryMock, tourLogServiceMock);

        testTourId = UUID.randomUUID();
        testTour = new Tour();
        testTour.setId(testTourId);
        testTour.setName("Test Tour");
    }

    @Test
    void getAllTours_ReturnsList() {
        List<Tour> tours = List.of(testTour);
        when(tourRepositoryMock.findAll()).thenReturn(tours);

        List<Tour> result = tourService.getAllTours();

        assertEquals(1, result.size());
        assertEquals("Test Tour", result.get(0).getName());
        verify(tourRepositoryMock, times(1)).findAll();
    }

    @Test
    void getTourById_ReturnsTour() {
        when(tourRepositoryMock.findById(testTourId)).thenReturn(Optional.of(testTour));

        Optional<Tour> result = tourService.getTourById(testTourId);

        assertTrue(result.isPresent());
        assertEquals("Test Tour", result.get().getName());
        verify(tourRepositoryMock).findById(testTourId);
    }

    @Test
    void createTour_SavesTour() {
        when(tourRepositoryMock.save(testTour)).thenReturn(testTour);

        Tour result = tourService.createTour(testTour);

        assertEquals(testTour, result);
        verify(tourRepositoryMock).save(testTour);
    }

    @Test
    void updateTour_ExistingTour_UpdatesAndSaves() {
        Tour updatedTour = new Tour();
        updatedTour.setId(testTourId);
        updatedTour.setName("Updated Name");

        when(tourRepositoryMock.findById(testTourId)).thenReturn(Optional.of(testTour));
        when(tourRepositoryMock.save(any(Tour.class))).thenAnswer(i -> i.getArgument(0));

        Tour result = tourService.updateTour(updatedTour);

        assertEquals("Updated Name", result.getName());
        verify(tourRepositoryMock).findById(testTourId);
        verify(tourRepositoryMock).save(testTour);
    }

    @Test
    void updateTour_TourNotFound_ThrowsException() {
        Tour updatedTour = new Tour();
        updatedTour.setId(testTourId);

        when(tourRepositoryMock.findById(testTourId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> tourService.updateTour(updatedTour));
        assertTrue(ex.getMessage().contains("Tour not found"));

        verify(tourRepositoryMock).findById(testTourId);
        verify(tourRepositoryMock, never()).save(any());
    }

    @Test
    void deleteTour_ExistingTour_Deletes() {
        when(tourRepositoryMock.existsById(testTourId)).thenReturn(true);

        tourService.deleteTour(testTourId);

        verify(tourRepositoryMock).existsById(testTourId);
        verify(tourRepositoryMock).deleteById(testTourId);
    }

    @Test
    void deleteTour_NotExisting_ThrowsException() {
        when(tourRepositoryMock.existsById(testTourId)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> tourService.deleteTour(testTourId));
        assertTrue(ex.getMessage().contains("Tour not found"));

        verify(tourRepositoryMock).existsById(testTourId);
        verify(tourRepositoryMock, never()).deleteById(any());
    }

}
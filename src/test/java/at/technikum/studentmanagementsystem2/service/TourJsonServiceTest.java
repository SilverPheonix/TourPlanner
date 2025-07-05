package at.technikum.studentmanagementsystem2.service;

import at.technikum.studentmanagementsystem2.dto.TourExportDTO;
import at.technikum.studentmanagementsystem2.helpers.TourMapper;
import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TourJsonServiceTest {

    private Tour testTour;
    private List<TourLog> testLogs;

    private TourService tourServiceMock;
    private TourLogService tourLogServiceMock;

    @BeforeEach
    void setUp() {
        testTour = new Tour();
        testTour.setId(UUID.randomUUID());
        testTour.setName("Test Tour");
        testTour.setDescription("Description");
        testTour.setFrom("Wien");
        testTour.setTo("Graz");
        testTour.setTransportType("Car");
        testTour.setDistance(300.0);
        testTour.setEstimatedTime(120.0);
        testTour.setImageUrl("image.jpg");

        TourLog log = new TourLog(
                UUID.randomUUID(),
                testTour,
                LocalDateTime.of(2024, 7, 1, 10, 0),
                "Nice trip",
                "medium",
                300.0,
                120.0,
                4
        );
        testLogs = List.of(log);

        tourServiceMock = Mockito.mock(TourService.class);
        tourLogServiceMock = Mockito.mock(TourLogService.class);
    }
    @Test
    void testExportTour_CreatesValidJsonFile() throws IOException {
        // Verzeichnis und Datei vor dem Export auflisten
        File exportDir = new File("exports");
        if (!exportDir.exists()) exportDir.mkdirs();

        int filesBefore = exportDir.listFiles().length;

        // Export durchführen
        TourJsonService.exportTour(testTour, testLogs);

        // Nach dem Export
        File[] filesAfter = exportDir.listFiles();
        assertNotNull(filesAfter);
        assertTrue(filesAfter.length > filesBefore);

        // Finde die neue Datei
        File newFile = null;
        for (File file : filesAfter) {
            if (file.getName().contains(testTour.getId().toString()) && file.getName().endsWith(".json")) {
                newFile = file;
                break;
            }
        }

        assertNotNull(newFile, "Exportierte Datei wurde nicht gefunden");
        assertTrue(newFile.length() > 0);

        // Datei kann korrekt wieder eingelesen werden
        TourExportDTO dto = TourJsonService.importTour(newFile.getPath());
        assertEquals("Test Tour", dto.getName());
        assertEquals(1, dto.getTourLogs().size());
        assertEquals("Nice trip", dto.getTourLogs().get(0).getComment());

        // Aufräumen (optional)
        newFile.delete();
    }
}

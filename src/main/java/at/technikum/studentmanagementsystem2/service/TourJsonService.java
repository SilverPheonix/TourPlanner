package at.technikum.studentmanagementsystem2.service;

import at.technikum.studentmanagementsystem2.dto.TourExportDTO;
import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.helpers.TourMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TourJsonService {

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private static final String EXPORT_FOLDER = "exports";

    public static void exportTour(Tour tour, List<TourLog> logs) throws IOException {
        // Ordner erstellen, falls nicht vorhanden
        Files.createDirectories(Path.of(EXPORT_FOLDER));

        // Dateiname generieren
        String timestamp = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = tour.getId() + "_" + timestamp + "_export.json";
        File outputFile = new File(EXPORT_FOLDER, filename);

        // Mapper verwenden
        TourExportDTO exportDTO = TourMapper.toDTO(tour, logs);

        // JSON schreiben
        mapper.writeValue(outputFile, exportDTO);
    }

    public static TourExportDTO importTour(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), TourExportDTO.class);
    }

    public static Tour importAndSaveTour(String filePath, TourService tourService, TourLogService tourLogService) throws IOException {
        TourExportDTO dto = importTour(filePath);
        Tour tour = TourMapper.fromDTO(dto);
        List<TourLog> logs = dto.getTourLogs()
                .stream()
                .map(logDTO -> TourMapper.fromDTO(logDTO, tour))
                .collect(Collectors.toList());

        tourService.createTour(tour);

        for (TourLog log : logs) {
            tourLogService.saveTourLog(log);
        }

        return tour;
    }
}

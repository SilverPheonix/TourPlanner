package at.technikum.studentmanagementsystem2.service;

import at.technikum.studentmanagementsystem2.dto.TourExportDTO;
import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import at.technikum.studentmanagementsystem2.helpers.TourMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TourJsonService {
    private static final Logger logger = LogManager.getLogger(TourJsonService.class);

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private static final String EXPORT_FOLDER = "exports";

    public static void exportTour(Tour tour, List<TourLog> logs) {
        try {
            Files.createDirectories(Path.of(EXPORT_FOLDER));
            logger.info("Ensured export folder exists: {}", EXPORT_FOLDER);

            String timestamp = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = tour.getId() + "_" + timestamp + "_export.json";
            File outputFile = new File(EXPORT_FOLDER, filename);

            TourExportDTO exportDTO = TourMapper.toDTO(tour, logs);

            mapper.writeValue(outputFile, exportDTO);
            logger.info("Exported tour '{}' to file: {}", tour.getName(), outputFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to export tour '{}' to JSON", tour.getName(), e);
        }
    }

    public static TourExportDTO importTour(String filePath) {
        try {
            TourExportDTO dto = mapper.readValue(new File(filePath), TourExportDTO.class);
            logger.info("Successfully imported tour from: {}", filePath);
            return dto;
        } catch (IOException e) {
            logger.error("Failed to import tour from: {}", filePath, e);
            return null;
        }
    }

    public static Tour importAndSaveTour(String filePath, TourService tourService, TourLogService tourLogService) {
        logger.info("Starting import and save for: {}", filePath);

        TourExportDTO dto = importTour(filePath);
        if (dto == null) {
            logger.warn("Aborted import — file could not be read: {}", filePath);
            return null;
        }

        Tour tour = TourMapper.fromDTO(dto);
        List<TourLog> logs = dto.getTourLogs()
                .stream()
                .map(logDTO -> TourMapper.fromDTO(logDTO, tour))
                .collect(Collectors.toList());

        tourService.createTour(tour);
        logger.info("Created imported tour: {}", tour.getName());

        for (TourLog log : logs) {
            tourLogService.saveTourLog(log);
        }

        logger.info("Saved {} logs for tour '{}'", logs.size(), tour.getName());

        return tour;
    }

}

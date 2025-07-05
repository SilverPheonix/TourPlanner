package at.technikum.studentmanagementsystem2.service;

import at.technikum.studentmanagementsystem2.helpers.AlertHelper;
import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class TourReportService {

    public static void exportTourToPDF(Tour tour, List<TourLog> logs) {
        try {
            // Erstelle Reports-Ordner falls nicht vorhanden
            File reportsDir = new File("reports");
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }

            // Dateiname mit Timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = String.format("tour_%s_%s_report.pdf", tour.getId(), timestamp);
            String outputPath = new File(reportsDir, fileName).getAbsolutePath();

            // PDF-Erstellung
            PdfWriter writer = new PdfWriter(new FileOutputStream(outputPath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Tour Report").setBold().setFontSize(18));
            document.add(new Paragraph("Name: " + tour.getName()));
            document.add(new Paragraph("Description: " + tour.getDescription()));
            document.add(new Paragraph("From: " + tour.getFrom()));
            document.add(new Paragraph("To: " + tour.getTo()));
            document.add(new Paragraph("Transport: " + tour.getTransportType()));
            document.add(new Paragraph("Distance: " + tour.getDistance() + " km"));
            document.add(new Paragraph("Estimated Time: " + tour.getEstimatedTime() + " hrs"));
            document.add(new Paragraph("Popularity: " + tour.getPopularity()));
            document.add(new Paragraph("Child Friendliness: " + tour.getChildFriendliness()));
            document.add(new Paragraph(""));

            if (tour.getImageUrl() != null && !tour.getImageUrl().isBlank()) {
                try {
                    Image map = new Image(ImageDataFactory.create(tour.getImageUrl()));
                    map.scaleToFit(400, 300);
                    document.add(map);
                } catch (Exception e) {
                    document.add(new Paragraph("⚠️ Failed to load image."));
                }
            }

            if (!logs.isEmpty()) {
                document.add(new Paragraph("\nTour Logs:").setBold().setFontSize(14));
                for (TourLog log : logs) {
                    document.add(new Paragraph("— " + log.getDateTime() +
                            " | Distance: " + log.getTotalDistance() +
                            " | Time: " + log.getTotalTime() +
                            " | Difficulty: " + log.getDifficulty() +
                            " | Rating: " + log.getRating() +
                            " | Comment: " + log.getComment()));
                }
            } else {
                document.add(new Paragraph("No logs available."));
            }

            document.close();

            System.out.println("✅ Report saved to: " + outputPath);
            AlertHelper.showAlert("Erfolg", "Report erfolgreich erstellt!", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void exportSummarizeReportToPDF(List<Tour> allTours, TourLogService tourLogService) {
        try {
            // Create reports directory if it doesn't exist
            File reportsDir = new File("reports");
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }

            // Timestamped file name
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = String.format("summary_report_%s.pdf", timestamp);
            String outputPath = new File(reportsDir, fileName).getAbsolutePath();

            // Setup PDF
            PdfWriter writer = new PdfWriter(new FileOutputStream(outputPath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Tour Summary Report").setBold().setFontSize(18));
            document.add(new Paragraph("Erstellt am: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))));
            document.add(new Paragraph("\n"));

            Table table = new Table(4); // 4 columns: Name, Avg Time, Avg Distance, Avg Rating
            table.addHeaderCell("Tour Name");
            table.addHeaderCell("Ø Zeit (h)");
            table.addHeaderCell("Ø Distanz (km)");
            table.addHeaderCell("Ø Bewertung");

            for (Tour tour : allTours) {
                List<TourLog> logs = tourLogService.getTourLogsByTourId(tour.getId());

                double avgTime = logs.stream().mapToDouble(TourLog::getTotalTime).average().orElse(0.0);
                double avgDist = logs.stream().mapToDouble(TourLog::getTotalDistance).average().orElse(0.0);
                double avgRating = logs.stream().mapToDouble(TourLog::getRating).average().orElse(0.0);

                table.addCell(tour.getName());
                table.addCell(String.format("%.2f", avgTime));
                table.addCell(String.format("%.2f", avgDist));
                table.addCell(String.format("%.1f", avgRating));
            }

            document.add(table);
            document.close();

            System.out.println("✅ Summary Report saved to: " + outputPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


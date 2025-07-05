package at.technikum.studentmanagementsystem2.helpers;

import at.technikum.studentmanagementsystem2.dto.TourExportDTO;
import at.technikum.studentmanagementsystem2.dto.TourLogDTO;
import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;

import java.util.List;
import java.util.stream.Collectors;

public class TourMapper {

    public static TourExportDTO toDTO(Tour tour, List<TourLog> logs) {
        TourExportDTO dto = new TourExportDTO();
        dto.setId(tour.getId());
        dto.setName(tour.getName());
        dto.setDescription(tour.getDescription());
        dto.setFrom(tour.getFrom());
        dto.setTo(tour.getTo());
        dto.setTransportType(tour.getTransportType());
        dto.setDistance(tour.getDistance());
        dto.setEstimatedTime(tour.getEstimatedTime());
        dto.setImageUrl(tour.getImageUrl());
        dto.setPopularity(tour.getPopularity());
        dto.setChildFriendliness(tour.getChildFriendliness());
        dto.setStartLat(tour.getStartLat());
        dto.setStartLon(tour.getStartLon());
        dto.setEndLat(tour.getEndLat());
        dto.setEndLon(tour.getEndLon());
        dto.setTourLogs(logs.stream().map(TourMapper::toDTO).collect(Collectors.toList()));
        return dto;
    }

    public static TourLogDTO toDTO(TourLog log) {
        TourLogDTO dto = new TourLogDTO();
        dto.setId(log.getId());
        dto.setDateTime(log.getDateTime());
        dto.setComment(log.getComment());
        dto.setDifficulty(log.getDifficulty());
        dto.setTotalDistance(log.getTotalDistance());
        dto.setTotalTime(log.getTotalTime());
        dto.setRating(log.getRating());
        return dto;
    }

}

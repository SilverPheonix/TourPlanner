package at.technikum.studentmanagementsystem2.helpers;

import at.technikum.studentmanagementsystem2.dto.TourExportDTO;
import at.technikum.studentmanagementsystem2.dto.TourLogDTO;
import at.technikum.studentmanagementsystem2.models.Tour;
import at.technikum.studentmanagementsystem2.models.TourLog;

import java.util.List;
import java.util.UUID;
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

    public static Tour fromDTO(TourExportDTO dto) {
        Tour tour = new Tour();
        tour.setId(UUID.randomUUID());
        tour.setName(dto.getName());
        tour.setDescription(dto.getDescription());
        tour.setFrom(dto.getFrom());
        tour.setTo(dto.getTo());
        tour.setTransportType(dto.getTransportType());
        tour.setDistance(dto.getDistance());
        tour.setEstimatedTime(dto.getEstimatedTime());
        tour.setImageUrl(dto.getImageUrl());
        tour.setPopularity(dto.getPopularity());
        tour.setChildFriendliness(dto.getChildFriendliness());
        tour.setStartLat(dto.getStartLat());
        tour.setStartLon(dto.getStartLon());
        tour.setEndLat(dto.getEndLat());
        tour.setEndLon(dto.getEndLon());
        return tour;
    }

    public static TourLog fromDTO(TourLogDTO dto, Tour tour) {
        TourLog log = new TourLog();
        log.setId(UUID.randomUUID());
        log.setTour(tour);
        log.setDateTime(dto.getDateTime());
        log.setComment(dto.getComment());
        log.setDifficulty(dto.getDifficulty());
        log.setTotalDistance(dto.getTotalDistance());
        log.setTotalTime(dto.getTotalTime());
        log.setRating(dto.getRating());
        return log;
    }


}

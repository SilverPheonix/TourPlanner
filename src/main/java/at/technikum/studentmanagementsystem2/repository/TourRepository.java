package at.technikum.studentmanagementsystem2.repository;

import at.technikum.studentmanagementsystem2.models.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TourRepository extends JpaRepository<Tour, UUID> {
    Tour save(Tour tour);
}

package at.technikum.studentmanagementsystem2.repository;

import at.technikum.studentmanagementsystem2.models.TourLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TourLogRepository extends JpaRepository<TourLog, UUID> {
}

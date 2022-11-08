package tennis.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tennis.project.domain.BoardLike;
import tennis.project.domain.BoardReport;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<BoardReport, Long> {

}

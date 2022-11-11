package tennis.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tennis.project.domain.BoardReport;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<BoardReport, Long> {

  @Query("select rp from BoardReport rp where rp.board.id = :boardId and rp.member.id = :memberId")
  Optional<BoardReport> find(@Param("boardId") Long boardId, @Param("memberId") Long memberId);

  void deleteAllByBoardId(Long id);
}

package tennis.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tennis.project.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryInterface {
}

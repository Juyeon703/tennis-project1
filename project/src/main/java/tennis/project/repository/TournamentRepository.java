package tennis.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tennis.project.domain.Board;
import tennis.project.domain.Local;
import tennis.project.domain.Member;
import tennis.project.domain.Tournament;

import java.util.List;
import java.util.Optional;

public interface TournamentRepository extends JpaRepository<Tournament, Long>, BoardRepositoryInterface {
}

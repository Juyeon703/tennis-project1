package tennis.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tennis.project.domain.Club;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubRepositoryInterface {



}

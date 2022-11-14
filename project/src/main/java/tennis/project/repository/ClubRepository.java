package tennis.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tennis.project.domain.Club;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubRepositoryInterface, JpaSpecificationExecutor<Club> {

  Page<Club> findByLocal_NameContainingOrNameContaining(String localName, String name, Pageable pageable);
}

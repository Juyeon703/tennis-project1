package tennis.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tennis.project.domain.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long>{

}

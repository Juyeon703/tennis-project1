package tennis.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tennis.project.domain.ClubMember;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long>, ClubMemberRepositoryInterface {

  @Query("select cm from ClubMember cm where cm.club.id = :clubId and cm.member.id = :memberId")
  Optional<ClubMember> exist(@Param("clubId") Long clubId, @Param("memberId") Long memberId);
}

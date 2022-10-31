package tennis.project.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tennis.project.domain.ClubMember;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class ClubMemberRepositoryImpl implements ClubMemberRepositoryInterface {

  @PersistenceContext
  private final EntityManager em;

  public ClubMember findOne(Long clubMemberId) {
    return em.find(ClubMember.class, clubMemberId);
  }
}



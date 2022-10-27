package tennis.project.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tennis.project.domain.Board;
import tennis.project.domain.ClubMember;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClubMemberRepositoryImpl implements ClubMemberRepositoryInterface {

  @PersistenceContext
  private final EntityManager em;
}



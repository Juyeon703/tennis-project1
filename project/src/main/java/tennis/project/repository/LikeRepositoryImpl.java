package tennis.project.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryInterface {

  @PersistenceContext
  private final EntityManager em;
}

package tennis.project.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class LocalRepositoryImpl implements LocalRepositoryInterface{

  @PersistenceContext
  private final EntityManager em;
}

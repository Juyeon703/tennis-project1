package tennis.project.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tennis.project.domain.Board;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryInterface{

  @PersistenceContext
  private final EntityManager em;

  // 게시글 단건 조회
  public Board findOne(Long boardId) {
    return em.find(Board.class, boardId);
  }
}

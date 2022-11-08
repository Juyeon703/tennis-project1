package tennis.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tennis.project.domain.BoardLike;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<BoardLike, Long>, LikeRepositoryInterface {

  @Query("select lk from BoardLike lk where lk.board.id = :boardId and lk.member.id = :memberId")
  Optional<BoardLike> find(@Param("boardId") Long boardId, @Param("memberId") Long memberId);
}

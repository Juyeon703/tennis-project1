package tennis.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tennis.project.domain.Board;
import tennis.project.domain.BoardLike;
import tennis.project.domain.Member;
import tennis.project.repository.LikeRepository;

import java.util.Collections;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {

  private final LikeRepository likeRepository;

  public int getLikeCount(Long boardId) {
    return likeRepository.findAll().stream().filter(like -> like.getBoard().getId() == boardId).toList().size();
  }

  public String checkLike(Long boardId, Long memberId) {
    Optional<BoardLike> likeCheck = likeRepository.find(boardId, memberId);

    if (likeCheck.isEmpty()) {
      return "not";
    } else {
      return "is";
    }
  }

  @Transactional
  public int clickLike(Board board, Member member) {
    BoardLike likeCheck = likeRepository.find(board.getId(), member.getId()).orElse(null);
    if (likeCheck == null) {
      BoardLike like = BoardLike.createLike(board, member);
      likeRepository.save(like);
      return 1;
    } else {
      likeRepository.delete(likeCheck);
      return 0;
    }
  }
}

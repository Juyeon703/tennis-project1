package tennis.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tennis.project.domain.Board;
import tennis.project.domain.Member;
import tennis.project.dto.BoardSaveForm;
import tennis.project.dto.BoardUpdateForm;
import tennis.project.repository.BoardRepository;
import tennis.project.repository.LikeRepository;
import tennis.project.repository.ReportRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;
  private final LikeRepository likeRepository;
  private final ReportRepository reportRepository;

  public Long addBoard(BoardSaveForm form, Member member) {
    Board board = Board.createBoard(form, member);
    boardRepository.save(board);
    return board.getId();
  }

  @Transactional
  public Page<Board> getBoardList(Pageable pageable) {
    return boardRepository.findAll(pageable);
  }

  public Board get(Long id) {
    return boardRepository.findById(id).orElse(null);
  }

  public void delete(Long id) {
    likeRepository.deleteAllByBoardId(id);
    reportRepository.deleteAllByBoardId(id);
    boardRepository.deleteById(id);
  }

  public void update(BoardUpdateForm form, Board board) {
    board.setTitle(form.getTitle());
    board.setContent(form.getContent());
    board.setModifiedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")));
    boardRepository.save(board);
  }
}

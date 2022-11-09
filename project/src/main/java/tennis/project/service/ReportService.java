package tennis.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tennis.project.domain.Board;
import tennis.project.domain.BoardLike;
import tennis.project.domain.BoardReport;
import tennis.project.domain.Member;
import tennis.project.repository.LikeRepository;
import tennis.project.repository.ReportRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

  private final ReportRepository reportRepository;

  @Transactional
  public void addReport(Board board, Member member, String content) {
    BoardReport report = BoardReport.createReport(board, member, content);
    reportRepository.save(report);
  }

  public String checkReport(Long boardId, Long memberId) {
    Optional<BoardReport> reportCheck = reportRepository.find(boardId, memberId);
    if (reportCheck.isEmpty()) {
      return "not";
    } else {
      return "is";
    }
  }
}

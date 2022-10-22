package tennis.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import tennis.project.dto.BoardSaveForm;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment | Sequence랑 비교?
  @Column(name = "board_id")
  private Long id;

  @NotNull
  @Column(length = 31)
  private String title;

  @NotNull
  @Column(length = 145)
  private String content;

  @NotNull
  @Column(length = 11)
  private String author;   //member nickname

  @Column(columnDefinition = "integer default 0")
  private Integer views;

  @CreatedDate
  @Column(length = 40)
  private String createdDate;

  @LastModifiedDate
  @Column(length = 40)
  private String modifiedDate;

  @ManyToOne(fetch = LAZY) // 지연로딩 -> 다대일 관계 매핑시 설정, 사용자가 많아질 경우에 속도를 우선시해서 안정성이 높아짐?
  @JoinColumn(name = "member_id")

  private Member member;

  public static Board createBoard(BoardSaveForm form, Member member) {
    Board board = new Board();

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    board.setTitle(form.getTitle());
    board.setAuthor(member.getNickname());
    board.setContent(form.getContent());
    board.setViews(0);
    board.setCreatedDate(LocalDateTime.now().format(dtf));
    board.setModifiedDate(LocalDateTime.now().format(dtf));
    board.setMember(member);
    return board;
  }
}

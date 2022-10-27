package tennis.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import tennis.project.dto.ClubForm;
import tennis.project.web.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Club implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "club_id")
  private Long id;

  @NotNull
  @Column(length = 16)
  private String name;

  @Column(length = 256)
  private String introduction;

  @NotNull
  @Column(length = 10)
  private int memberCount;

  @Column(length = 256)
  private String img;

  @NotNull
  @CreatedDate
  @Column(length = 40)
  private String createdDate;

  @NotNull
  @Column(length = 10)
  private String leader;

  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "local_id")
  private Local local;


  public static Club createClub(ClubForm clubForm) {
    Club club = new Club();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    club.setName(clubForm.getName());
    club.setIntroduction(clubForm.getIntroduction());
    club.setMemberCount(1);
    club.setImg(clubForm.getImg());
    club.setCreatedDate(LocalDateTime.now().format(dtf));
    club.setStatus(Status.RECRUITING);
    club.setLocal(clubForm.getLocal());
    club.setLeader(clubForm.getLeader());
    return club;
  }


}






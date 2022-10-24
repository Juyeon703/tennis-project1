package tennis.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tournament {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
  @Column(name = "tournament_id")
  private Long id;

  @NotNull
  @Column(length = 31)
  private String title;

  @NotNull
  @Column(length = 145)
  private String place;

  @NotNull
  @Column(length = 40)
  private Date applicationStartDate;

  @NotNull
  @Column(length = 40)
  private Date applicationEndDate;

  @NotNull
  @Column(length = 40)
  private Date CompStartDate;

  @NotNull
  @Column(length = 40)
  private Date CompEndDate;

  @Column(length = 256)
  private String image;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "local_id")
  private Local local;
}

package tennis.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Local {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
  @Column(name = "local_id")
  private Long id;

  @NotNull
  @Column(length = 11)
  private String name;
}
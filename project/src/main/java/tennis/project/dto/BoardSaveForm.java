package tennis.project.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class BoardSaveForm {

  @NotNull
  @Size(min = 1, max = 31, message = "제목은 1~31자 이내여야 합니다.")
  private String title;

  @NotNull
  @Size(min = 1, max = 145, message = "내용은 1~145자 이내여야 합니다.")
  private String content;

}

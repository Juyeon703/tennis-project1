package tennis.project.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginForm {

  @NotNull @Size(min = 8, max = 20, message = "아이디는 8~20자 이내여야 합니다.")
  private String loginId;

  @NotNull @Size(min = 8, max = 30, message = "비밀번호는 8~30자 이내여야 합니다.")
  private String password;
}

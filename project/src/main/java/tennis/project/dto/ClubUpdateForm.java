package tennis.project.dto;

import lombok.Getter;
import lombok.Setter;
import tennis.project.domain.Local;
import tennis.project.web.Status;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class ClubUpdateForm {

    private Long id;

    @NotNull @Size(min=1, max=16, message="동호회 이름은 1 ~ 16 자 이내여야 합니다." )
    private String name;

    private String imgPath;

    @Size(min=1, max=256, message = "소개글은 256자 이내여야합니다")
    private String introduction;

    private Local local;

    private Status status;

}

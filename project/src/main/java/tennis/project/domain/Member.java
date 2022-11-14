package tennis.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tennis.project.dto.MemberSaveForm;
import tennis.project.web.Provider;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @NotNull
  @Column(unique = true, length = 50)
  private String loginId;

  @Column(unique = true, length = 30)
  private String password;

  @NotNull
  @Column(unique = true, length = 10)
  private String nickname;

  @NotNull
  @Column(unique = true, length = 50)
  private String email;

  @Enumerated(EnumType.STRING)
  private Provider provider;

  private String accessToken;

  public static Member createMember(MemberSaveForm form) {
    Member member = new Member();
    member.setLoginId(form.getLoginId());
    member.setPassword(form.getPassword());
    member.setNickname(form.getNickname());
    member.setEmail(form.getEmail());
    member.setProvider(Provider.GOGOTENNIS);
    return member;
  }

  public static Member createGoogleMember(Map<String, String> userInfo, String access_token) {
    Member member = new Member();
    member.setLoginId(userInfo.get("email"));
    member.setNickname(userInfo.get("name"));
    member.setEmail(userInfo.get("email"));
    member.setProvider(Provider.GOGOTENNIS);
    member.setAccessToken(access_token);
    return member;
  }
}

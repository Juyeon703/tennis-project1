package tennis.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tennis.project.domain.Member;
import tennis.project.dto.LoginForm;
import tennis.project.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

  private final MemberRepository memberRepository;

  public Member loginCheck(LoginForm form) {
    String loginId = form.getLoginId();
    String password = form.getPassword();
    return memberRepository.findByLoginId(loginId).filter(m -> m.getPassword().equals(password)).orElse(null);
  }
}

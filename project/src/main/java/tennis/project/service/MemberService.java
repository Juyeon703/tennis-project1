package tennis.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tennis.project.domain.Member;
import tennis.project.dto.MemberSaveForm;
import tennis.project.repository.MemberRepository;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  @Transactional
  public Long memberAdd(MemberSaveForm form) {
    Member member = Member.createMember(form);
    memberRepository.save(member);
    return member.getId();
  }

  public Optional<Member> findByEmail(String email) {
    return memberRepository.findByEmail(email);
  }
  @Transactional
  public Member googleSignUp(Map<String, String> userInfo, String access_token) {
    Member member = Member.createGoogleMember(userInfo, access_token);
    memberRepository.save(member);
    return member;
  }

  @Transactional
  public void renewAccessToken(Member member, String access_token) {
    member.setAccessToken(access_token);
  }
}

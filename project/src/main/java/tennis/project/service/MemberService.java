package tennis.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tennis.project.domain.Member;
import tennis.project.dto.MemberSaveForm;
import tennis.project.repository.MemberRepository;

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
}

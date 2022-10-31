package tennis.project.repository;

import tennis.project.domain.Club;
import tennis.project.domain.ClubMember;

public interface ClubMemberRepositoryInterface {

  ClubMember findOne(Long id);
}

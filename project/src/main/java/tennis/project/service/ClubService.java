package tennis.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tennis.project.domain.Club;
import tennis.project.domain.ClubMember;
import tennis.project.domain.Member;
import tennis.project.dto.ClubForm;
import tennis.project.repository.ClubMemberRepository;
import tennis.project.repository.ClubRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubService {

  private final ClubRepository clubRepository;
  private final ClubMemberRepository clubMemberRepository;

  public List<Club> getClubList() {
    return clubRepository.findAll();
  }

  public Club findOne(Long clubId) {
    return clubRepository.findOne(clubId);
  }

  @Transactional
  public Long addClub(ClubForm form, Member member) {
    Club club = Club.createClub(form);
    clubRepository.save(club);
    ClubMember clubMember = ClubMember.createClubMember(club, member);
    clubMemberRepository.save(clubMember);
    return club.getId();
  }


  @Transactional
  public Long addClubMember(Club club, Member member) {
    ClubMember clubMember = ClubMember.createClubMember(club, member);
    clubMemberRepository.save(clubMember);
    return clubMember.getClub().getId();
  }

  public  List<ClubMember> getClubMemberList(Long clubId) {

    return clubMemberRepository.findAll()
      .stream()
      .filter(clubMember -> clubMember.getClub().getId() == clubId).toList();
  }


}

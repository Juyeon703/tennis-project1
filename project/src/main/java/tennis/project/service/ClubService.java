package tennis.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tennis.project.domain.Club;
import tennis.project.domain.ClubMember;
import tennis.project.domain.Member;
import tennis.project.dto.ClubForm;
import tennis.project.dto.ClubUpdateForm;
import tennis.project.repository.ClubMemberRepository;
import tennis.project.repository.ClubRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubService {

  private final ClubRepository clubRepository;
  private final ClubMemberRepository clubMemberRepository;

  public Page<Club> getClubList(Pageable pageable) {
    return clubRepository.findAll(pageable);
  }

  public Club findOne(Long clubId) {
    return clubRepository.findOne(clubId);
  }

  @Transactional
  public Club addClub(ClubForm form, Member member, MultipartFile file) throws IOException {
    if (!file.isEmpty()) {
      String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
      String dirPath = System.getProperty("user.dir") + "/src/main/resources/static/file/";
      file.transferTo(new File(dirPath + filename));
      form.setImgPath("/file/" + filename);
    } else {
      form.setImgPath("/img/logo.png");
    }

    Club club = Club.createClub(form);
    clubRepository.save(club);
    ClubMember clubMember = ClubMember.createClubMember(club, member);
    clubMemberRepository.save(clubMember);
    return club;
  }

  @Transactional
  public ClubMember addClubMember(Club club, Member member) {
    ClubMember clubMember = ClubMember.createClubMember(club, member);
    clubMemberRepository.save(clubMember);
    return clubMember;
  }

  public List<ClubMember> getClubMemberList(Long clubId) {

    return clubMemberRepository.findAll()
      .stream()
      .filter(clubMember -> clubMember.getClub().getId() == clubId).toList();
  }

  public ClubMember clubMemberCheck(Long clubId, Long memberId) {
    return clubMemberRepository.exist(clubId, memberId).orElse(null);
  }

  @Transactional
  public Long update(ClubUpdateForm form, MultipartFile file) throws IOException {
    if (!file.isEmpty()) {
      String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
      String dirPath = System.getProperty("user.dir") + "/src/main/resources/static/file/";
      file.transferTo(new File(dirPath + filename));
      form.setImgPath("/file/" + filename);
    }

    Club club = clubRepository.findOne(form.getId());
    club.updateClub(form, club);
    return club.getId();
  }

  @Transactional
  public void deleteClubMember(Long clubId, Long memberId) {
    ClubMember clubMember = clubMemberCheck(clubId, memberId);
    clubMemberRepository.delete(clubMember);
    Club club = clubRepository.findOne(clubId);
    club.setMemberCount(club.getMemberCount() - 1);
  }

  @Transactional
  public void deleteClub(Long clubId) {
    clubMemberRepository.deleteAllInBatch(getClubMemberList(clubId));
    clubRepository.deleteById(clubId);

  }

  @Transactional
  public Page<Club> searchClubs(String keyword) {
    // 검색 키워드가 지역 이름이랑 같거나, 클럽 이름에 포함되었을 때
    return (Page<Club>) clubRepository.findAll().stream()
      .filter(searchClubs -> searchClubs.getLocal().getName().contains(keyword)
        || searchClubs.getName().contains(keyword)).toList();
  }

  public Page<Club> searchClubs(String keyword, Pageable pageable) {
    String localName = keyword;
    String name = keyword;
    return clubRepository.findByLocal_NameContainingOrNameContaining(localName, name, pageable);
  }

  public ClubMember get(Long id) {
    return clubMemberRepository.findOne(id);
  }

  // 중복확인
  public Optional<Club> findByName(String clubName) {
    return clubRepository.findAll().stream().filter(club -> club.getName().equals(clubName)).findFirst();
  }
}

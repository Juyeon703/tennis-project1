package tennis.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tennis.project.domain.Club;
import tennis.project.domain.ClubMember;
import tennis.project.domain.Local;
import tennis.project.domain.Member;
import tennis.project.dto.ClubForm;
import tennis.project.dto.ClubUpdateForm;
import tennis.project.service.ClubService;
import tennis.project.service.TournamentService;
import tennis.project.web.SessionConst;
import tennis.project.web.Status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ClubController {

  private final ClubService clubService;
  private final TournamentService tournamentService;


  @GetMapping("/club")
  public String clubList(Model model) {

    List<Club> clubs = clubService.getClubList();
    model.addAttribute("clubs", clubs);

    return "/club/clubList"; // 동호회 리스트 페이지
  }

  @GetMapping("/club/detail/{clubId}")
  public String clubDetail(@PathVariable("clubId") Long clubId, Model model, HttpServletRequest request) {

    Club club = clubService.findOne(clubId);
    model.addAttribute("club", club);

    List<ClubMember> memberList = clubService.getClubMemberList(club.getId());
    model.addAttribute("memberList", memberList);

    HttpSession session = request.getSession(false);

    if (session != null) {
      Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
      ClubMember clubMemberCheck = clubService.ClubMemberCheck(clubId, member.getId());
      model.addAttribute("clubMemberCheck", clubMemberCheck);
    }

    return "/club/clubDetail";
  }

  @GetMapping("/club/form")
  public String clubForm(Model model) {
    ClubForm clubForm = new ClubForm();
    model.addAttribute("form", clubForm);

    List<Local> locals = tournamentService.getLocalList();
    model.addAttribute("locals", locals);
    return "/club/clubForm";
  }

  @PostMapping("/club/save")
  public String clubSave(@Validated @ModelAttribute("form") ClubForm form, BindingResult bindingResult,
                         HttpServletRequest request) {

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/club/clubForm";
    }

    Member member = (Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
    form.setLeader(member.getNickname());
    Long clubId = clubService.addClub(form, member).getId();
    return "redirect:/club/detail/" + clubId;
  }

  @PostMapping("/club/join")
  public String joinClub(HttpServletRequest request, Long id) {
    Member member = (Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
    Club club = clubService.findOne(id);
    club.setMemberCount(club.getMemberCount() + 1);
    Long clubId = clubService.addClubMember(club, member).getClub().getId();

    return "redirect:/club/detail/" + clubId;
  }

  @GetMapping("/club/update/{clubId}")
  public String clubUpdate(@PathVariable("clubId") Long clubId, Model model) {
    Club club = clubService.findOne(clubId);
    ClubUpdateForm form = new ClubUpdateForm();
    form.setId(club.getId());
    form.setImg(club.getImg());
    form.setIntroduction(club.getIntroduction());
    form.setName(club.getName());
    form.setStatus(club.getStatus());
    form.setLocal(club.getLocal());
    model.addAttribute("form", form);

    Status[] statuses = Status.values();
    model.addAttribute("statuses", statuses);

    List<Local> locals = tournamentService.getLocalList();
    model.addAttribute("locals", locals);

    return "/club/clubUpdateForm";
  }

  @PostMapping("/club/update")
  public String clubUpdate(@Validated @ModelAttribute("form") ClubUpdateForm form,
                           BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/club/clubUpdateForm";
    }

   Long clubId = clubService.update(form);

    return "redirect:/club/detail/" + clubId;
  }

}

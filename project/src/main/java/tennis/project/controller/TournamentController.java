package tennis.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tennis.project.domain.Local;
import tennis.project.domain.Tournament;
import tennis.project.service.TournamentService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TournamentController {

  private final TournamentService tournamentService;

  @GetMapping("/tournament")
  public String tournament(Model model) {
    List<Tournament> list = tournamentService.getTournamentList();
    model.addAttribute("list", list);
    List<Local> locals = tournamentService.getLocalList();
    model.addAttribute("locals", locals);
    return "/tournament/tournament";
  }
}

package tennis.project.controller;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tennis.project.domain.Club;
import tennis.project.service.ClubService;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/club")
public class ClubApiController {
  private final ClubService clubService;

  @GetMapping("/checkClub")
  public JSONObject checkClub(@RequestParam Map<String, Object> param) {
    JSONObject result = new JSONObject();
    int inputLength = param.get("clubName").toString().length();
    if (inputLength < 1 || inputLength > 16) {
      result.put("result", "validate");
      return result;
    }
    Optional<Club> club = clubService.findByName(param.get("clubName").toString());
    if (club.isEmpty()) {
      result.put("result", "ok");
      return result;
    }
    result.put("result", "duplicate");
    return result;
  }
}

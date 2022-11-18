package tennis.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tennis.project.dto.StarForm;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StarController {

  @GetMapping("/test1")
  public String test(Model model) {
    model.addAttribute("form", new StarForm());
    return "star";
  }

  @PostMapping("/test/star")
  public String star(@Validated @ModelAttribute("form") StarForm form, BindingResult bindingResult) {

    System.out.println("star = " + form.getStar());

    return "redirect:/test1";
  }
}

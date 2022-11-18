package tennis.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tennis.project.domain.Member;
import tennis.project.dto.BoardSaveForm;
import tennis.project.dto.TestForm;
import tennis.project.web.SessionConst;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TestController {

  @GetMapping("/test1")
  public String test(Model model) {
    model.addAttribute("form", new TestForm());
    return "/test";
  }

  @PostMapping("/test/star")
  public String star(@Validated @ModelAttribute("form")TestForm form,BindingResult bindingResult) {

    System.out.println("star = " + form.getStar());

    return "redirect:/test1";
  }
}

package tennis.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tennis.project.dto.MemberSaveForm;
import tennis.project.service.MemberService;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/signUp")
  public String signUPForm(Model model) {
    model.addAttribute("form", new MemberSaveForm());
    return "/members/signUpForm";
  }

  @PostMapping("/memberSave")
  public String memberAdd(@Validated @ModelAttribute("form") MemberSaveForm form,
                          BindingResult bindingResult) {
    // validation 뒤에 BindingResult가 와야하고 에러메시지 출력해줌
    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/members/signUpForm";
    }

    // 회원이 모든 값을 제대로 입력한 상태 -> 회원을 저장
    memberService.memberAdd(form);

    return "redirect:/";
  }
}


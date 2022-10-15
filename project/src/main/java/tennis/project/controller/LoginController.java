package tennis.project.controller;

import lombok.Getter;
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
import tennis.project.domain.Member;
import tennis.project.dto.LoginForm;
import tennis.project.service.LoginService;
import tennis.project.web.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;

  @GetMapping("/loginForm")
  public String loginFrom(Model model) {
    model.addAttribute("form", new LoginForm());
    return "/login/loginForm";
  }

  @PostMapping("/memberLogin")
  public String loginSuccess(@Validated @ModelAttribute("form") LoginForm form, BindingResult bindingResult,
                             HttpServletRequest request) {
    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/login/loginForm";
    }

    // 유저가 입력한 아이디와 그 아이디에 해당하는 패스워드를 가진 객체가 있는지 검사
    Member loginMember = loginService.loginCheck(form);

    if (loginMember == null) {
      bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
      return "/login/loginForm";
    }

    // 로그인 성공 로직
    request.getSession().setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

    return "redirect:/";
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/";
  }
}


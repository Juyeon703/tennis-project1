package tennis.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tennis.project.domain.Board;
import tennis.project.domain.Member;
import tennis.project.dto.BoardSaveForm;
import tennis.project.dto.BoardUpdateForm;
import tennis.project.service.BoardService;
import tennis.project.web.SessionConst;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  // 게시글 전체조회
  @GetMapping("/home")
  public String home(Model model) {
    List<Board> list = boardService.getBoardList();
    model.addAttribute("list", list);
    return "boards/boardHome";
  }

  @GetMapping("/boardAdd")
  public String boardAddForm(Model model) {
    model.addAttribute("form", new BoardSaveForm());
    return "/boards/boardAddForm";
  }

  @PostMapping("/boardSave")
  public String boardAdd(@Validated @ModelAttribute("form") BoardSaveForm form,
                         BindingResult bindingResult, HttpServletRequest request) {
    // 로그인 정보 받아오기
    Member member = (Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);

    // 검증
    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/boards/boardAddForm";
    }

    // 게시글 등록
    boardService.addBoard(form, member);

    return "redirect:/";
  }

  @GetMapping("/detail/{boardId}")
  public String boardDetail(@PathVariable("boardId") Long id, Model model) {
    Board board = boardService.get(id);
    model.addAttribute("board", board);
    return "/boards/boardDetail";
  }

  @GetMapping("/delete/{boardId}")
  public String boardDelete(@PathVariable("boardId") Long id) {
    boardService.delete(id);
    return "redirect:/boards/home";
  }

  @GetMapping("/update/{boardId}")
  public String boardUpdate(@PathVariable("boardId") Long id, Model model) {
    Board board = boardService.get(id);

    BoardUpdateForm form = new BoardUpdateForm();
    form.setId(board.getId());
    form.setTitle(board.getTitle());
    form.setContent(board.getContent());
    model.addAttribute("form", form);
    return "/boards/boardUpdateForm";
  }

  @PostMapping("/boardUpdate/{boardId}")
  public String boardUpdate(@PathVariable("boardId") Long id, @Validated @ModelAttribute("form") BoardUpdateForm form, BindingResult bindingResult) {

    // 검증
    if (bindingResult.hasErrors()) {
      log.info("errors = {}", bindingResult);
      return "/boards/boardUpdateForm";
    }

    Board board = boardService.get(id);
    boardService.update(form, board);
    return "redirect:/boards/detail/" + id;
  }
}

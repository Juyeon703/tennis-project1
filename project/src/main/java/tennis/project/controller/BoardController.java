package tennis.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import tennis.project.service.LikeService;
import tennis.project.service.ReportService;
import tennis.project.web.SessionConst;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;
  private final LikeService likeService;
  private final ReportService reportService;

  // 게시글 전체조회
  @GetMapping("/home")
  public String home(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
                     Pageable pageable, Model model) {
    Page<Board> list = boardService.getBoardList(pageable);
    int nowPage = list.getPageable().getPageNumber() + 1;
    int startPage = Math.max(nowPage - 4, 1);
    int endPage = Math.min(nowPage + 9, list.getTotalPages());

    model.addAttribute("nowPage", nowPage);
    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);

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
  public String boardDetail(@PathVariable("boardId") Long id, Model model, HttpServletRequest request) {
    Board board = boardService.get(id);
    model.addAttribute("board", board);

    Member member = (Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
    String checkLike = likeService.checkLike(board.getId(), member.getId());
    model.addAttribute("checkLike", checkLike);
    int likeCount = likeService.getLikeCount(board.getId());
    model.addAttribute("likeCount", likeCount);
    System.out.println(likeCount);
    return "/boards/boardDetail";
  }

  @PostMapping("/like")
  @ResponseBody
  public Map<String, Integer> like(Long boardId, HttpServletRequest request) {
    Board board = boardService.get(boardId);
    Member member = (Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
    Integer result = likeService.clickLike(board, member);
    Map<String, Integer> map = new HashMap<>();
    map.put("result", result);
    Integer count = likeService.getLikeCount(board.getId());
    map.put("count", count);
    return map;
  }

  @PostMapping("/report")
  @ResponseBody
  public Integer report(@RequestParam("boardId")Long boardId, @RequestParam("content") String content, HttpServletRequest request) {
    Board board = boardService.get(boardId);
    Member member = (Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER);
    if (reportService.checkReport(board.getId(), member.getId()) == "is") {
      return 1;
    } else {
      reportService.addReport(board, member, content);
      return 0;
    }
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

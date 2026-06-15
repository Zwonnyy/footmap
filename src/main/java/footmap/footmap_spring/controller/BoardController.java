package footmap.footmap_spring.controller;

import footmap.footmap_spring.dto.PageRequestDTO;
import footmap.footmap_spring.dto.PageResponseDTO;
import footmap.footmap_spring.dto.boardDto.Board;
import footmap.footmap_spring.dto.userDto.User;
import footmap.footmap_spring.service.aiService.AiService;
import footmap.footmap_spring.service.boardService.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/board")
@Log4j2
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private AiService aiService;

    @GetMapping("/list")
    public String boardList(@Valid PageRequestDTO pageRequestDTO, Model model){
        PageResponseDTO<Board> responseDTO = boardService.getList(pageRequestDTO);
        model.addAttribute("responseDTO",responseDTO);
        return "board/list_board";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/writeForm")
    public String boardWriteForm(Authentication authentication, Model model){
        model.addAttribute("user", authentication.getPrincipal());
        return "board/write_board";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/write")
    public String write(Board board, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        board.setU_code(Integer.parseInt(user.getU_code()));
        board.setB_nick(user.getU_nick());
        boardService.writeAdd(board);
        return "redirect:/board/list";
    }

    @RequestMapping("/view")
    public String boardView(@RequestParam("idx")Integer idx,
                            Model model,
                            PageRequestDTO pageRequestDTO,
                            Authentication authentication){
        boardService.boardReadCountUpdate(idx);
        Board board = boardService.getBoardView(idx);
        model.addAttribute("board",board);
        model.addAttribute("isOwner", isOwner(board, authentication));
        if (board != null) {
            model.addAttribute("aiSummary", aiService.summarizeBoard(board.getB_title(), board.getB_contents()));
            model.addAttribute("contentSafety", aiService.moderateText(board.getB_contents()));
        }
        return "/board/view_board";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/updateForm")
    public String updateFrom(@RequestParam("idx")Integer idx,
                             PageRequestDTO pageRequestDTO,
                             Model model,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes){
        Board board = boardService.getBoardView(idx);
        if (!isOwner(board, authentication)) {
            redirectAttributes.addFlashAttribute("error", "작성자만 수정할 수 있습니다.");
            return "redirect:/board/view?idx=" + idx;
        }
        model.addAttribute("board",board);
        return "board/update_board";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/update")
    public String update(@Valid Board board,
                         PageRequestDTO pageRequestDTO,
                         RedirectAttributes redirectAttributes,
                         BindingResult bindingResult,
                         Authentication authentication){
        Board origin = boardService.getBoardView(board.getIdx());
        if (!isOwner(origin, authentication)) {
            redirectAttributes.addFlashAttribute("error", "작성자만 수정할 수 있습니다.");
            redirectAttributes.addAttribute("idx", board.getIdx());
            return "redirect:/board/view";
        }

        if(bindingResult.hasErrors()){
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            redirectAttributes.addAttribute("idx",board.getIdx());
            return "redirect:/board/updateForm?"+link;
        }
        boardService.writeUpdate(board);
        redirectAttributes.addFlashAttribute("result","modified");
        redirectAttributes.addAttribute("idx",board.getIdx());
        return "redirect:/board/view";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/delete")
    public String delete(@RequestParam("idx")Integer idx,
                         Authentication authentication,
                         RedirectAttributes redirectAttributes){
        Board board = boardService.getBoardView(idx);
        if (!isOwner(board, authentication)) {
            redirectAttributes.addFlashAttribute("error", "작성자만 삭제할 수 있습니다.");
            return "redirect:/board/view?idx=" + idx;
        }
        boardService.deleteBoard(idx);
        redirectAttributes.addFlashAttribute("result", "deleted");
        return "redirect:/board/list";
    }

    private boolean isOwner(Board board, Authentication authentication) {
        if (board == null || authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return false;
        }
        User user = (User) authentication.getPrincipal();
        return String.valueOf(board.getU_code()).equals(user.getU_code());
    }
}

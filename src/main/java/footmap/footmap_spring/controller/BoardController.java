package footmap.footmap_spring.controller;

import footmap.footmap_spring.dto.PageRequestDTO;
import footmap.footmap_spring.dto.PageResponseDTO;
import footmap.footmap_spring.dto.boardDto.Board;
import footmap.footmap_spring.dto.userDto.User;
import footmap.footmap_spring.service.boardService.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Controller
@RequestMapping("/board")
@Log4j2
public class BoardController {

    @Autowired
    private BoardService boardService;

    //게시판 목록 전체 조회
//    @RequestMapping("/list2")
//    public String boardList(Model model){
//        List<Board> boardList = boardService.getBoardList();
//        model.addAttribute("board",boardList);
//        return "board/list_board2";
//    }
    @GetMapping("/list")
    public String boardList(@Valid PageRequestDTO pageRequestDTO, Model model){

        log.info("=========list==========");

        PageResponseDTO<Board> responseDTO = boardService.getList(pageRequestDTO);
        model.addAttribute("responseDTO",responseDTO);
        log.info(model);
//        pageRequestDTO=PageRequestDTO(page=1, size=10, types=null, keyword=, link=page=1&size=10&keyword=), org.springframework.validation.BindingResult.pageRequestDTO=org.springframework.validation.BeanPropertyBindingResult: 0 errors,
//        responseDTO=PageResponseDTO(page=1, size=10, total=103, start=1, end=10, prev=false, next=true,
//        dtoList=[Board(idx=105, b_title=이제 잘되지롱 second textsssss, b_contents=                effffffffffssss, b_cnt=21, del_chk=null, reg_date=2023-01-04 07:01:14, b_nick=부산대뒤돌려차기, u_code=2, t_name=null, u_id=null),
//                Board(idx=96, b_title=title95, b_contents=contents95, b_cnt=1, del_chk=null, reg_date=2023-01-01 18:00:07, b_nick=부산대뒤돌려차기, u_code=2, t_name=null, u_id=null), Board(idx=95, b_title=title94, b_contents=contents94, b_cnt=2, del_chk=null, reg_date=2023-01-01 18:00:07, b_nick=부산대뒤돌려차기, u_code=2, t_name=null, u_id=null)])}

        log.info(responseDTO);
        //PageResponseDTO(page=1, size=10, total=103, start=1, end=10, prev=false, next=true,
        // dtoList=[Board(idx=105, b_title=이제 잘되지롱 second textsssss, b_contents=                effffffffffssss , b_cnt=21, del_chk=null, reg_date=2023-01-04 07:01:14, b_nick=부산대뒤돌려차기, u_code=2, t_name=null, u_id=null),
        //              Board(idx=96, b_title=title95, b_contents=contents95, b_cnt=1, del_chk=null, reg_date=2023-01-01 18:00:07, b_nick=부산대뒤돌려차기, u_code=2, t_name=null, u_id=null),
        //              Board(idx=95, b_title=title94, b_contents=contents94, b_cnt=2, del_chk=null, reg_date=2023-01-01 18:00:07, b_nick=부산대뒤돌려차기, u_code=2, t_name=null, u_id=null)])
        log.info(pageRequestDTO); //PageRequestDTO(page=1, size=10, types=null, keyword=, link=page=1&size=10&keyword=)


//        if(bindingResult.hasErrors()){
//            pageRequestDTO = PageRequestDTO.builder().build();
//        }
//
//        model.addAttribute("responseDTO",boardService.getList(pageRequestDTO));
//        log.info(pageRequestDTO); //PageRequestDTO(page=1, size=10, types=null, keyword=null, link=page=1&size=10)
//        log.info(model);
        //{pageRequestDTO=PageRequestDTO(page=1, size=10, link=page=1&size=10),
        // org.springframework.validation.BindingResult.pageRequestDTO=org.springframework.validation.BeanPropertyBindingResult: 0 errors,
        // responseDTO=PageResponseDTO(page=1, size=10, total=104, start=1, end=10, prev=false, next=true,
        // dtoList=[
        // Board(idx=105, b_title=second textsssss, b_contents=effffffffffssss, b_cnt=10, del_chk=null, reg_date=07:01, b_nick=부산대뒤돌려차기, u_code=2, t_name=null, u_id=null),
        // Board(idx=96, b_title=title95, b_contents=contents95, b_cnt=1, del_chk=null, reg_date=2023-01-01, b_nick=부산대뒤돌려차기, u_code=2, t_name=null, u_id=null),
        // Board(idx=95, b_title=title94, b_contents=contents94, b_cnt=2, del_chk=null, reg_date=2023-01-01, b_nick=부산대뒤돌려차기, u_code=2, t_name=null, u_id=null)])}
        return "board/list_board";
    }

    //게시글쓰기 폼
    @RequestMapping("/writeForm")
    public String boardWriteForm(Authentication authentication, Model model){
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return "redirect:/login";
        }
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "board/write_board";
    }

    //게시글 쓰기
    @RequestMapping("/write")
    public String write(Board board, Authentication authentication){
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return "redirect:/login";
        }
        User user = (User) authentication.getPrincipal();
        board.setU_code(Integer.parseInt(user.getU_code()));
        board.setB_nick(user.getU_nick());

        boardService.writeAdd(board);

        return "redirect:/board/list";
    }

    //게시글 내용 보기
    @RequestMapping("/view")
    public String boardView(@RequestParam("idx")Integer idx,Model model,PageRequestDTO pageRequestDTO){

        log.info("=========view=============");

        //조회수 증가
        boardService.boardReadCountUpdate(idx);
        //내용보기위한 게시글 조회
        Board board = boardService.getBoardView(idx);

        log.info(board);
        log.info(board.getReg_date());
        log.info(board.getReg_date().getClass().getName());
        //Board(idx=61, b_title=title60, b_contents=contents60, b_cnt=8, del_chk=null, reg_date=null, b_nick=부산대뒤돌려차기, u_code=2, t_name=부울경바르셀, u_id=gkstkdgml)
        log.info(pageRequestDTO);//PageRequestDTO(page=5, size=10, link=page=5&size=10)
        model.addAttribute("board",board);
        log.info("view============================="+model);
        //{pageRequestDTO
        // =PageRequestDTO(page=1, size=10, link=page=1&size=10),
        // org.springframework.validation.BindingResult.pageRequestDTO=org.springframework.validation.BeanPropertyBindingResult: 0 errors,
        // board=Board(idx=99, b_title=title98, b_contents=contents98, b_cnt=3, del_chk=null, reg_date=null, b_nick=부산대뒤돌려차기, u_code=2, t_name=부울경바르셀, u_id=gkstkdgml), idx=99}
        return "/board/view_board";
    }


    //게시글 수정하기 폼
    @RequestMapping("/updateForm")
    public String updateFrom(@RequestParam("idx")Integer idx, PageRequestDTO pageRequestDTO,Model model){

        log.info("==============수정하기폼");
        Board board = boardService.getBoardView(idx);

        model.addAttribute("board",board);

        log.info(pageRequestDTO);//PageRequestDTO(page=5, size=10, link=page=5&size=10)
        log.info(model);
        return "board/update_board";
    }

    //게시글 수정하기
    @PostMapping("/update")
    public String update(@Valid Board board, BindingResult bindingResult, PageRequestDTO pageRequestDTO,
                         RedirectAttributes redirectAttributes){
        log.info("=============수정=============");
        log.info(board);
        log.info(pageRequestDTO);

        if(bindingResult.hasErrors()){
            log.info("has Error....");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            redirectAttributes.addAttribute("idx",board.getIdx());
            return "redirect:/board/updateForm?"+link;
        }
        boardService.writeUpdate(board);
        redirectAttributes.addFlashAttribute("result","modified");
        redirectAttributes.addAttribute("idx",board.getIdx());
        //redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        //redirectAttributes.addAttribute("size",pageRequestDTO.getSize());
       // log.info(redirectAttributes);
        return "redirect:/board/view";
    }

    //게시글 삭제하기
    @RequestMapping("/delete")
    public String delete(@RequestParam("idx")Integer idx){
        System.out.println("삭제시작");
        boardService.deleteBoard(idx);
        System.out.println("삭제!");
        return "redirect:/board/list";
    }

//    @RequestMapping("/delete")
//    public ModelAndView delete(Integer idx,Model model){
//        boardService.deleteBoard(idx);
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("/board/list_board");
//        return mv;
//    }

}

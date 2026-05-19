package footmap.footmap_spring.controller;

import footmap.footmap_spring.dto.gameDto.game;
import footmap.footmap_spring.service.gameService.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;
    //게임등록페이지
    @RequestMapping("/registerForm")
    public String gameRegForm(){
        System.out.println("게임등록페이지");
        return "match_game/reg_game";
    }

    //게임등록
    @RequestMapping("/register")
    public String   gameAdd( game game) {

        gameService.gameAdd( game );

        return "redirect:/game/search";
    }

    //게임찾기
    @PreAuthorize("hasRole('USER')")//로그인없이 페이지 접속가능하지만 필요한페이지에 어노테이션 추가
    //@PreAutohorize(접근제한 표현식)뒤에 들어가는 문자열은 표현식 따라서 상황에맞게 작성해야함
    //현재쓰는 (hasRole(표현식)은 특정한 권한이 있는 사용자 를 허용시켜줌 ex)우리페이지 게시판
    @GetMapping("/search")
    public String gameSearch(Model model){
        List<game> gameList = gameService.getGameList();
        model.addAttribute("game",gameList);
        System.out.println(model);
        return "match_game/search_game";
    }

    //내 게임내역
    @RequestMapping("/list")
    public String gameList(){
        return "match_game/list_game";
    }

}

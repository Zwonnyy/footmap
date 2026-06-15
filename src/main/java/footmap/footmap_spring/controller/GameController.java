package footmap.footmap_spring.controller;

import footmap.footmap_spring.dto.noticeDto.notice;
import footmap.footmap_spring.dto.teamDto.team;
import footmap.footmap_spring.dto.userDto.User;
import footmap.footmap_spring.service.noticeService.NoticeService;
import footmap.footmap_spring.service.teamService.TeamService;
import footmap.footmap_spring.dto.gameDto.game;
import footmap.footmap_spring.service.gameService.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private NoticeService noticeService;

    //게임등록페이지
    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/registerForm")
    public String gameRegForm(Authentication authentication, Model model){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("myTeams", teamService.getTeaminUser(user.getU_code()));
        model.addAttribute("fields", noticeService.finfo3());
        return "match_game/reg_game";
    }

    //게임등록
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/register")
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
        return "match_game/search_game";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/apply")
    public String applyGame(@RequestParam("g_code") int g_code,
                            @RequestParam("g_search") int g_search) {
        gameService.applyGame(g_code, g_search);
        return "redirect:/game/search";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/approve")
    public String approveGame(@RequestParam("g_code") int g_code) {
        gameService.approveGame(g_code);
        return "redirect:/game/list";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/reject")
    public String rejectGame(@RequestParam("g_code") int g_code) {
        gameService.rejectGame(g_code);
        return "redirect:/game/list";
    }

    //내 게임내역
    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/list")
    public String gameList(Authentication authentication, Model model){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("games", gameService.getMyGameList(user.getU_code()));
        return "match_game/list_game";
    }

}

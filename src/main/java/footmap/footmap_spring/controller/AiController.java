package footmap.footmap_spring.controller;

import footmap.footmap_spring.dto.teamDto.team;
import footmap.footmap_spring.dto.userDto.User;
import footmap.footmap_spring.service.aiService.AiService;
import footmap.footmap_spring.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;
    private final UserService userService;

    @GetMapping("/player-analysis")
    public String playerAnalysis(@RequestParam("u_code") String u_code) {
        User player = userService.getUserByCode(u_code);
        return aiService.analyzePlayer(player);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/board-summary")
    public String boardSummary(@RequestParam("title") String title,
                               @RequestParam("contents") String contents) {
        return aiService.summarizeBoard(title, contents);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/moderate")
    public String moderate(@RequestParam("text") String text) {
        return aiService.moderateText(text);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/game-intro")
    public String gameIntro(@RequestParam("teamName") String teamName,
                            @RequestParam("fieldName") String fieldName,
                            @RequestParam("people") String people,
                            @RequestParam("date") String date,
                            @RequestParam("time") String time) {
        return aiService.generateGameIntro(teamName, fieldName, people, date, time);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/team-intro")
    public String teamIntro(@RequestParam("teamName") String teamName,
                            @RequestParam("stadium") String stadium) {
        return aiService.generateTeamIntro(teamName, stadium);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/match-recommend")
    public String matchRecommend(@RequestParam("teamName") String teamName,
                                 @RequestParam(value = "stadium", defaultValue = "") String stadium,
                                 @RequestParam(value = "vic", defaultValue = "0") int vic,
                                 @RequestParam(value = "draw", defaultValue = "0") int draw,
                                 @RequestParam(value = "lose", defaultValue = "0") int lose) {
        team team = new team();
        team.setT_name(teamName);
        team.setT_stadium(stadium);
        team.setT_vic(vic);
        team.setT_draw(draw);
        team.setT_lose(lose);
        return aiService.recommendMatch(team);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/review")
    public String review(@RequestParam("homeTeam") String homeTeam,
                         @RequestParam("awayTeam") String awayTeam,
                         @RequestParam("score") String score,
                         @RequestParam("mvp") String mvp) {
        return aiService.generateReview(homeTeam, awayTeam, score, mvp);
    }
}

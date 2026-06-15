package footmap.footmap_spring.controller;

import footmap.footmap_spring.dto.teamDto.team;
import footmap.footmap_spring.dto.userDto.User;
import footmap.footmap_spring.service.teamService.TeamService;
import footmap.footmap_spring.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/Team")
@Log4j2
@RequiredArgsConstructor
public class MatchController {

    private final TeamService teamService;
    private final UserService userService;

    @RequestMapping("/t_search")
    public String t_search(Model model){
        List<team> teamList = teamService.getTeamList();
        model.addAttribute("team", teamList);
        return "Team/t_search";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/t_commit")
    public String createForm(){
        return "Team/t_commit";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public String createTeam(team team, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        teamService.createTeam(team, user.getU_code());
        return "redirect:/Team/myteam";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/join")
    public String joinTeam(@RequestParam("t_code") int t_code, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        teamService.joinTeam(user.getU_code(), t_code);
        return "redirect:/Team/myteam";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/myteam")
    public String myTeam(Authentication authentication, Model model){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("teams", teamService.getTeaminUser(user.getU_code()));
        return "Team/myteam";
    }

    @RequestMapping("/detail")
    public String teamDetail(@RequestParam("t_code") int t_code, Model model){
        model.addAttribute("team", teamService.getTeamByCode(t_code));
        model.addAttribute("members", userService.getTeamMembers(t_code));
        return "Team/team";
    }
}

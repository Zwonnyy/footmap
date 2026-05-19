package footmap.footmap_spring.controller;

import footmap.footmap_spring.dto.teamDto.team;
import footmap.footmap_spring.service.FileStorageService;
import footmap.footmap_spring.service.teamService.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/Team")
@Log4j2
@RequiredArgsConstructor
public class MatchController {

    private final TeamService teamService;
    private final FileStorageService fileStorageService;

    @RequestMapping("/t_search")
    public String t_search(Model model) {
        List<team> teamList = teamService.getTeamList();
        model.addAttribute("teams", teamList);
        return "Team/t_search";
    }

    @RequestMapping("/team")
    public String teamDetail(int t_code, Model model) {
        model.addAttribute("team", teamService.getTeam(t_code));
        return "Team/team";
    }

    @RequestMapping("/t_commit")
    public String teamCreateForm() {
        return "Team/t_commit";
    }

    @RequestMapping("/create")
    public String createTeam(team team, MultipartFile imageFile) {
        String imagePath = fileStorageService.saveImage(imageFile, "team");
        team.setT_img(imagePath);
        teamService.addTeam(team);
        return "redirect:/Team/t_search";
    }
}

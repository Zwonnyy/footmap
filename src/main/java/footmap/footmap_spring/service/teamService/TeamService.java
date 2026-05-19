package footmap.footmap_spring.service.teamService;

import footmap.footmap_spring.dto.teamDto.team;

import java.util.List;

public interface TeamService {
    List<team> getTeamList();

    List<team> getTopTeam();

    List<team> getTeaminUser(String u_code);

    team getTeam(int t_code);

    void addTeam(team team);
}

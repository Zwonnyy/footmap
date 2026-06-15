package footmap.footmap_spring.service.teamService;

import footmap.footmap_spring.dto.teamDto.team;

import java.util.List;

public interface TeamService {
    List<team> getTeamList();

    List<team> getTopTeam();

    team getTeamByCode(int t_code);

    List<team> getTeaminUser(String u_code);

    void createTeam(team team, String u_code);

    void joinTeam(String u_code, int t_code);
}

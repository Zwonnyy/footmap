package footmap.footmap_spring.service.teamService;

import footmap.footmap_spring.dao.teamDao.TeamMapper;
import footmap.footmap_spring.dto.teamDto.team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamMapper teamMapper;

    @Override
    public List<team> getTeamList() {

        return teamMapper.getTeamList();
    }

    @Override
    public List<team> getTopTeam() {
        return teamMapper.getTopTeam();
    }

    @Override
    public List<team> getTeaminUser(String u_code) {
        List<team> getTeaminUser = teamMapper.getTeaminUser(u_code);
        return getTeaminUser;
    }

    @Override
    public team getTeam(int t_code) {
        return teamMapper.getTeam(t_code);
    }

    @Override
    public void addTeam(team team) {
        teamMapper.addTeam(team);
    }
}

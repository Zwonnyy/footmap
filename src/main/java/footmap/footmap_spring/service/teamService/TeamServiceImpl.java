package footmap.footmap_spring.service.teamService;

import footmap.footmap_spring.dao.teamDao.TeamMapper;
import footmap.footmap_spring.dto.teamDto.team;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class TeamServiceImpl implements TeamService {
    private final TeamMapper teamMapper;

    @Override
    public List<team> getTeamList() {
        try {
            return teamMapper.getTeamList();
        } catch (DataAccessException e) {
            log.warn("Team list is unavailable. Check TEAM table schema.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<team> getTopTeam() {
        try {
            return teamMapper.getTopTeam();
        } catch (DataAccessException e) {
            log.warn("Top team list is unavailable. Check TEAM table schema.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public team getTeamByCode(int t_code) {
        try {
            return teamMapper.getTeamByCode(t_code);
        } catch (DataAccessException e) {
            log.warn("Team detail is unavailable. Check TEAM table schema.", e);
            return null;
        }
    }

    @Override
    public List<team> getTeaminUser(String u_code) {
        try {
            return teamMapper.getTeaminUser(u_code);
        } catch (DataAccessException e) {
            log.warn("User team list is unavailable. Check TEAM and TEAM_MANAGEMENT schema.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public void createTeam(team team, String u_code) {
        teamMapper.createTeam(team);
        teamMapper.joinTeam(u_code, team.getT_code(), "LEADER");
    }

    @Override
    public void joinTeam(String u_code, int t_code) {
        teamMapper.joinTeam(u_code, t_code, "MEMBER");
    }
}

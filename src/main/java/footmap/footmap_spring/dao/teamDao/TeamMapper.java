package footmap.footmap_spring.dao.teamDao;

import footmap.footmap_spring.dto.teamDto.team;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeamMapper {
    List<team> getTeamList();

    List<team> getTopTeam();

    team getTeamByCode(@Param("t_code") int t_code);

    List<team> getTeaminUser(@Param("u_code") String u_code);

    void createTeam(team team);

    void joinTeam(@Param("u_code") String u_code, @Param("t_code") int t_code, @Param("role") String role);
}

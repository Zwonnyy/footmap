package footmap.footmap_spring.dao.userDao;

import footmap.footmap_spring.dto.userDto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> getUserList();

    List<User> getTopuserList();

    List<User> getGoalRanking();

    List<User> getAssistRanking();

    List<User> getCutRanking();

    List<User> getOverallRanking();

    User getUserByCode(@Param("u_code") String u_code);

    List<User> getTeamMembers(@Param("t_code") int t_code);

    void saveUser(User user);

    User getUserAccount(String u_id);//이친구는 CustomUserDetailsService로 이동

    void UpdateUser(User user);


}

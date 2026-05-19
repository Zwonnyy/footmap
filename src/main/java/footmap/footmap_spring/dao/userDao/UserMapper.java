package footmap.footmap_spring.dao.userDao;

import footmap.footmap_spring.dto.userDto.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> getUserList();

    List<User> getTopuserList();

    void saveUser(User user);

    User getUserAccount(String u_id);//이친구는 CustomUserDetailsService로 이동

    void UpdateUser(User user);


}
package footmap.footmap_spring.service.userService;

import footmap.footmap_spring.dto.userDto.User;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> getUserList();

    List<User> getTopuserList();

    boolean certificationupdate(String u_pw, User user, Authentication authenticationr);

    void saveUser(User user);


    Map<String, String> validateHandling(Errors errors);

    void UpdateUser(User user);

}

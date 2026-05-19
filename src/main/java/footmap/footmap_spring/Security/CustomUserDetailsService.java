package footmap.footmap_spring.Security;

import footmap.footmap_spring.dao.userDao.UserMapper;
import footmap.footmap_spring.dto.userDto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Override//아이디란에 입력한 데이터를 가져와서 인증
    public UserDetails loadUserByUsername(String u_id) throws UsernameNotFoundException {
        log.info("-------------------loadUserByUsername:----------------------- " + u_id);

        User user = userMapper.getUserAccount(u_id);
        if(user == null){
            throw new UsernameNotFoundException("User not authorized");
        }
        return user;
    }

}
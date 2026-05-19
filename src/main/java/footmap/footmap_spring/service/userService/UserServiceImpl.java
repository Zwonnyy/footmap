package footmap.footmap_spring.service.userService;


import footmap.footmap_spring.dao.userDao.UserMapper;
import footmap.footmap_spring.dto.userDto.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    @Override
    public List<User> getTopuserList() {
        return userMapper.getTopuserList();
    }

    @Transactional
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
    @Override
    public void UpdateUser(User user) {
        log.info("현재 로그인된 아이디=========" + user.getU_id());
        log.info("바뀐 비밀번호" + user.getU_pw());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setU_pw(passwordEncoder.encode(user.getU_pw()));
        user.setU_nick(user.getU_nick());
        user.setU_tel(user.getU_tel());
        user.setU_mail(user.getU_mail());
        log.info("혹시모르니깐" + passwordEncoder.encode(user.getU_pw()));
        log.info("유조ㅇ비니당ㄴ운야즃ㅇ류@@@@@@@@@@" + user);
        userMapper.UpdateUser(user);
    }
    @Override
    public boolean certificationupdate(String u_pw, User user, Authentication authentication){
        User users = (User) authentication.getPrincipal();
        log.info("현재비밀번호" + users.getU_pw());
        log.info("받은 비밀번호" +u_pw);
        log.info("패스워드 채크" + passwordEncoder.matches(u_pw,users.getU_pw()));
        if (passwordEncoder.matches(u_pw, user.getU_pw())){
            return true;
        }else {
            return false;
        }
    }


    @Transactional
    public void saveUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setU_pw(passwordEncoder.encode(user.getPassword()));
        user.setUser_auth("ROLE_USER");

        userMapper.saveUser(user);
    }






}
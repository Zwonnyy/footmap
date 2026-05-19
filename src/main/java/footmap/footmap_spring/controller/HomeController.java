package footmap.footmap_spring.controller;

import footmap.footmap_spring.Security.CustomUserDetailsService;
import footmap.footmap_spring.dao.userDao.UserMapper;
import footmap.footmap_spring.dto.teamDto.team;
import footmap.footmap_spring.dto.userDto.User;
import footmap.footmap_spring.service.teamService.TeamService;
import footmap.footmap_spring.service.userService.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private TeamService teamService;

    @RequestMapping("/")//메인페이지 userService의 getuserList와 getTopuserLIst로 선수전체의 목록과 top3선수의 목록을 가져옴
    public String home(Model model) {
        List<User> userList = userService.getUserList();
        List<User> getTopuserList = userService.getTopuserList();
        model.addAttribute("user", userList);
        model.addAttribute("Topuser", getTopuserList);

        List<team> teamList = teamService.getTeamList();
        List<team> getTopTeamList = teamService.getTopTeam();
        model.addAttribute("team", teamList);
        model.addAttribute("topteam", getTopTeamList);
        return "home/home";
    }



    @RequestMapping("/signUp")//회원가입페이지로 이동
    public String signup() {
        return "home/signup";
    }




    @PostMapping("/Write")//회원가입 폼
    public String saveUser(@ModelAttribute @Valid User user, Errors errors, Model model) {//UserMapper에서 오류문구 적용함
        if(errors.hasErrors()){
            /* 회원가입 실패시 입력 데이터 값을 유지 */
            model.addAttribute("user", user);

            /* 유효성 통과 못한 필드와 메시지를 핸들링 */
            Map<String, String> validatorResult = userService.validateHandling(errors);//에러메세지 받는곳
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "home/signup";//유효성검사를 통과하지못하면 다시 회원가입페이지로 이동하며 동시에 오류메세지 전달
        }
        userService.saveUser(user);//유효성검사를 통과하면 데이터 insert 후 로그인페이지로 redirect
        return "redirect:/login";
    }


    @GetMapping("/updateForm")
    public String updateForm(Authentication authentication, Model model) {

        User users= (User) authentication.getPrincipal();
        model.addAttribute("users", users);
        return "home/updateform";
    }

    @PostMapping("/userupdateForm")
    public String updateuser(User user,Authentication authentication) {
        User users = (User) authentication.getPrincipal();
        userService.UpdateUser(user);
        System.out.println("이전꺼" +  users.getU_pw());
        System.out.println("바뀐거" + user.getU_pw());
        return"redirect:/logout";
    }

    @RequestMapping("/Certificationupdate")
    public String certificationupdateform(String u_pw,User user,Authentication authentication,Model model){
        User users = (User) authentication.getPrincipal();
        model.addAttribute("users",users);
        model.addAttribute("cerification",userService.certificationupdate(u_pw,users,authentication));
        userService.certificationupdate(u_pw,users,authentication);
        System.out.println("여기까지오긴함~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("서비스 임플에서 받아온 값" + model);
        System.out.println("체크" + userService.certificationupdate(u_pw,users,authentication));
        if ( userService.certificationupdate(u_pw,users,authentication)){
            return "home/updateform";

        }else {
            return "/home/certificationupdateForm";
        }
    }
    @RequestMapping("/CertificationupdateForm")
    public String certificationupdateform(){
        return "home/certificationupdateForm";
    }




    @GetMapping("/login")//로그인페이지
    public String loginGET(@RequestParam(value = "error", required = false)String error,
                         @RequestParam(value = "exception", required = false)String exception,//@RequestParam로 json으로 넘어오는 오류문구를 받음
                         Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);//오류종류와 오류문구를 가지고 로그인 페이지로 이동
        System.out.println(model);
        return "/login";
    }
    @GetMapping("/Mypage")
    public String userAccess(Model model, Authentication authentication){
        User users = (User) authentication.getPrincipal();
        model.addAttribute("users", users);
        List<team> getTeaminUser = teamService.getTeaminUser(users.getU_code());
        model.addAttribute("userinteam", getTeaminUser);
        System.out.println(model);

        return "home/mypage";
    }
}

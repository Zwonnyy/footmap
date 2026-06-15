package footmap.footmap_spring.config;

import footmap.footmap_spring.Security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log4j2
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//일정한 페이지만 볼수 있도록
@RequiredArgsConstructor
//원하는곳에 @PreAutho-rize 혹은 @PostAuthorize 어노테이션을 사용해서 사전 혹은 사후권한 체크
//현재 위치"/game/serch"
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
    //자동로그인
    private final DataSource dataSource;
    private final CustomUserDetailsService customUserDetailsService;
    /* 로그인 실패 핸들러 의존성 주입 */
    private final AuthenticationFailureHandler customFailureHandler;


    @Bean//비밀번호 일시적으로 받아줌
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/login","/signUp","/resources/**").permitAll()//입력된 사이트 들은 security가 검사하지 않음
                .and()
                .formLogin()
                .loginPage("/login")//이폼을 설정하지 않으면 커스텀 로그인 페이지로 이동불가
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")//로그인 성공 시
                .failureHandler(customFailureHandler) // 로그인 실패 핸들러

                .and()
                .csrf().disable(); //CSRF 토큰 비활성화//필수!

        http.rememberMe()//자동로그인
                .key("12345678")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(customUserDetailsService)
                .tokenValiditySeconds(60*60*24*30);
    }

    @Bean//정적 자원의 처리(페이지 이동때마다 뜨는 너무많은 log를 제어)
    public WebSecurityCustomizer webSecurityCustomizer(){
        log.info("----------------web configure-----------------");

        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }

    @Bean//rememberme()에 필요한persistentTokenRepository 생성
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }
    @Override    /* 시큐리티가 로그인 과정에서 password를 가로챌때 어떤 해쉬로 암호화 했는지 확인 */
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}

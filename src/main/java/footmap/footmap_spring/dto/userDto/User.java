package footmap.footmap_spring.dto.userDto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Collections;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    private String  u_code;
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String  u_id;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String  u_pw;

    @NotNull(message = "이름을 입력해주세요.")
    @Size(min = 1, max = 4, message = "이름은 최대 4글자 입니다.")
    private String  u_name;
    @NotNull(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String  u_nick;
    @NotBlank(message = "생년월일은 필수 입력 값입니다.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "올바른 주민등록번호 형식이 아닙니다 ex)1999-11-11")

    private String     u_birth;
    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "올바른 전화번호 형식이 아닙니다")
    private String  u_tel;
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    private String  u_mail;
    private int     u_assi;
    private int     u_goal;
    private int     u_cut;
    @Positive(message = "성별은 필수 입력값입니다.")
    private int     u_sex;
    private String  user_auth;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.user_auth));
    }

    @Override
    public String getPassword() {
        return this.u_pw;
    }

    @Override
    public String getUsername() {
        return this.u_id;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Builder
    public User(String u_id, String u_pw, String u_name, String u_nick, String u_birth, String u_tel, String u_mail, int u_sex){
        this.u_id = u_id;
        this.u_pw = u_pw;
        this.u_name = u_name;
        this.u_nick = u_nick;
        this.u_birth = u_birth;
        this.u_tel = u_tel;
        this.u_mail = u_mail;
        this.u_sex = u_sex;
    }
}

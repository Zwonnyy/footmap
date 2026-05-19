package footmap.footmap_spring.dto.boardDto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Board {

    private int idx; // 게시글번호
    private String b_title; //글 제목
    private String b_contents; //글 내용
    private int b_cnt; //조회수
    private String del_chk; //삭제여부 Y: 삭제 , N:삭제x
    private String reg_date; //등록일
    private String b_nick; //작성자 닉네임 =user.u_nick
    private int u_code; //작성자 코드 (회원코드)
    private String t_name; //팀이름
    private String u_id;

}

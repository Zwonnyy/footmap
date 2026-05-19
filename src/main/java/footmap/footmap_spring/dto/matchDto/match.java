package footmap.footmap_spring.dto.matchDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class match {

    private int T_CODE;
    private String T_IMG;
    private String T_NAME;
    private String  T_STADIUM;
    private int T_VIC;
    private int T_DRAW;
    private int T_LOSE;
    private String T_INTRO;
}

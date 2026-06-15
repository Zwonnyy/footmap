package footmap.footmap_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class PageRequestDTO {
    //페이지처리위한 DTO
    //페이지번호(page), 한페이지당 갯수(size), limit에서 사용하는 건너뛰기(skip)의 수getskip()

    @Builder.Default
    @Min(value=1)
    @Positive
    private int page = 1;

    @Builder.Default
    @Min(value = 10)
    @Max(value=100)
    @Positive
    private int size = 10;

    private String types; //검색종류 제목(t) b_title or 내용(c) b_contents or 작성자(w) b_nick 로 조회

    private String keyword; //검색어

    private String link;

    public int getSkip(){
        return (page-1)*10;
    }

    public String[] getTypes(){
        if(types == null || types.isEmpty()){
            return null;
        }
        return types.split("");
    }

    public String getLink() {

        if(link == null){
            StringBuilder builder = new StringBuilder();

            builder.append("page=" + this.page);

            builder.append("&size=" + this.size);


            if(types != null && types.length() > 0){
                builder.append("&type=" + types);
            }

            if(keyword != null){
                try {
                    builder.append("&keyword=" + URLEncoder.encode(keyword,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                }
            }
            link = builder.toString();
        }

        return link;
    }




}

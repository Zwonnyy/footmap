package footmap.footmap_spring.controller;

import footmap.footmap_spring.dto.noticeDto.notice;

import footmap.footmap_spring.service.noticeService.NoticeService;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/Page")
@Log4j2
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    //공지사항
    @RequestMapping("/Notice")
    public String notice(){
        log.info("-----------------------------------------노티스시작--------");
            return "Page/Notice/notice";
    }
    //이벤트
    @RequestMapping("/Event")
    public String Event(){
          System.out.println("EVENT");
        return "Page/Notice/Event";
    }
    //FAQ
    @RequestMapping("/FAQ")
    public String FAQ(){

        System.out.println("FAQ");
        return "Page/Notice/FAQ";
    }
    @RequestMapping("/FINFO")
    public String FINFO(Model model){
        List<notice> noticeList = noticeService.finfo3();
        model.addAttribute("field", noticeList);
        System.out.println("풋살장 정보");
        return "Page/Notice/FINFO/FINFO";

    }
    //풋살장 리스트
    @RequestMapping("/FLIST")
    public String FLIST(Model model){

        List<notice> noticeList = noticeService.finfo3();
        model.addAttribute("field", noticeList);
        System.out.println("데이터" + noticeList);
        return "Page/Notice/FINFO/FLIST";

    }
    //사이트 소개
    @RequestMapping("/s_intro")
    public String S_intro(){
        System.out.println("사이트 소개");
        return "Page/S_intro";

    }
    //사이트 준수 사항
    @RequestMapping("/s_rule")
    public String S_rule(){
        System.out.println("사이트 준수사항");
        return "Page/S_rule";

    }

}

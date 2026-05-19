package footmap.footmap_spring.service.noticeService;

import footmap.footmap_spring.dao.noticeDao.NoticeMapper;
import footmap.footmap_spring.dto.noticeDto.notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;

    @Override
    public List<notice> finfo3() {

        return noticeMapper.finfo3();
    }


}




package footmap.footmap_spring.service.noticeService;

import footmap.footmap_spring.dao.noticeDao.NoticeMapper;
import footmap.footmap_spring.dto.noticeDto.notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
@Log4j2
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;

    @Override
    public List<notice> finfo3() {
        try {
            return noticeMapper.finfo3();
        } catch (DataAccessException e) {
            log.warn("Field info list is unavailable. Check FINFO table schema.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public notice getFieldDetail(int f_code) {
        try {
            return noticeMapper.getFieldDetail(f_code);
        } catch (DataAccessException e) {
            log.warn("Field detail is unavailable. Check FINFO table schema.", e);
            return null;
        }
    }


}




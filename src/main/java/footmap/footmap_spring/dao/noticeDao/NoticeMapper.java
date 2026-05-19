package footmap.footmap_spring.dao.noticeDao;

import footmap.footmap_spring.dto.noticeDto.notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeMapper {

    List<notice> finfo3();

}

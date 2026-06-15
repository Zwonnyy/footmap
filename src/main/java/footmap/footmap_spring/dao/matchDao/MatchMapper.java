package footmap.footmap_spring.dao.matchDao;

import footmap.footmap_spring.dto.matchDto.match;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MatchMapper {

    List<match> match();
}

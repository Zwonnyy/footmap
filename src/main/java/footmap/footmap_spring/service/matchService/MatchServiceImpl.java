package footmap.footmap_spring.service.matchService;

import footmap.footmap_spring.dao.matchDao.MatchMapper;
import footmap.footmap_spring.dto.matchDto.match;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
@Log4j2

public class MatchServiceImpl implements MatchService{

    private final MatchMapper matchMapper;
    @Override
    public List<match> match() {
        try {
            return matchMapper.match();
        } catch (DataAccessException e) {
            log.warn("Match team list is unavailable. Check TEAM table schema.", e);
            return Collections.emptyList();
        }
    }
}

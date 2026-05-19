package footmap.footmap_spring.service.matchService;

import footmap.footmap_spring.dao.matchDao.MatchMapper;
import footmap.footmap_spring.dto.matchDto.match;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

public class MatchServiceImpl implements MatchService{

    private final MatchMapper matchMapper;
    @Override
    public List<match> match() {
        return matchMapper.match();
    }
}

package footmap.footmap_spring.service.gameService;

import footmap.footmap_spring.dao.gameDao.GameMapper;
import footmap.footmap_spring.dto.gameDto.game;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class GameServiceImpl implements GameService{
    private final GameMapper gameMapper;
    @Override
    public List<game> getGameList() {
        try {
            return gameMapper.getGameList();
        } catch (DataAccessException e) {
            log.warn("Game list is unavailable. Check GAMELIST, FINFO and TEAM schema.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<game> getMyGameList(String u_code) {
        try {
            return gameMapper.getMyGameList(u_code);
        } catch (DataAccessException e) {
            log.warn("My game list is unavailable. Check game related schema.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public void gameAdd(game game) {
        gameMapper.addGame(game);
    }

    @Override
    public void applyGame(int g_code, int g_search) {
        gameMapper.applyGame(g_code, g_search);
    }

    @Override
    public void approveGame(int g_code) {
        gameMapper.approveGame(g_code);
    }

    @Override
    public void rejectGame(int g_code) {
        gameMapper.rejectGame(g_code);
    }
}

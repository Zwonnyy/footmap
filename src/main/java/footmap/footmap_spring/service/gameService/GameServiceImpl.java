package footmap.footmap_spring.service.gameService;

import footmap.footmap_spring.dao.gameDao.GameMapper;
import footmap.footmap_spring.dto.gameDto.game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{
    private final GameMapper gameMapper;
    @Override
    public List<game> getGameList() {
        return gameMapper.getGameList();
    }

    @Override
    public void gameAdd(game game) {
        gameMapper.addGame(game);
    }
}

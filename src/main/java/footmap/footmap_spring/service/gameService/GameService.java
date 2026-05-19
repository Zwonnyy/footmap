package footmap.footmap_spring.service.gameService;

import footmap.footmap_spring.dto.gameDto.game;

import java.util.List;

public interface GameService {
    List<game> getGameList();

    void gameAdd(game game);
}

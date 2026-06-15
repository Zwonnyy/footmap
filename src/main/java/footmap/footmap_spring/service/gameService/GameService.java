package footmap.footmap_spring.service.gameService;

import footmap.footmap_spring.dto.gameDto.game;

import java.util.List;

public interface GameService {
    List<game> getGameList();

    List<game> getMyGameList(String u_code);

    void gameAdd(game game);

    void applyGame(int g_code, int g_search);

    void approveGame(int g_code);

    void rejectGame(int g_code);
}

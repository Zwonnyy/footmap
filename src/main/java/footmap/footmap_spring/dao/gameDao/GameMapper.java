package footmap.footmap_spring.dao.gameDao;

import footmap.footmap_spring.dto.gameDto.game;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GameMapper {
    List<game> getGameList();

    List<game> getMyGameList(String u_code);

    void addGame(game game);

    void applyGame(@Param("g_code") int g_code, @Param("g_search") int g_search);

    void approveGame(int g_code);

    void rejectGame(int g_code);

}

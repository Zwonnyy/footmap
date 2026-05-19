package footmap.footmap_spring.dao.gameDao;

import footmap.footmap_spring.dto.gameDto.game;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GameMapper {
    List<game> getGameList();

    void addGame(game game);


}

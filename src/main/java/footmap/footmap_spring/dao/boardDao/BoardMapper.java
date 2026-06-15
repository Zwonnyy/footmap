package footmap.footmap_spring.dao.boardDao;

import footmap.footmap_spring.dto.PageRequestDTO;
import footmap.footmap_spring.dto.boardDto.Board;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface BoardMapper {

    //List<Board> getBoardList();

    void writeAdd(Board board);

    Board getBoardView(int idx);

    void readCountUpdate(int idx);

    void writeUpdate(Board board);

    void deleteBoard(int idx);

    List<Board> selectList(PageRequestDTO pageRequestDTO);

    int getCount(PageRequestDTO pageRequestDTO);
}

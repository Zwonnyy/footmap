package footmap.footmap_spring.service.boardService;

import footmap.footmap_spring.dto.PageRequestDTO;
import footmap.footmap_spring.dto.PageResponseDTO;
import footmap.footmap_spring.dto.boardDto.Board;

import java.util.List;

public interface BoardService {

    //List<Board> getBoardList();

    PageResponseDTO<Board> getList(PageRequestDTO pageRequestDTO); //기존 getBoardList()대체

    void writeAdd(Board board);

    Board getBoardView(int idx);

    void boardReadCountUpdate(int idx);

    void writeUpdate(Board board);

    Board deleteBoard(int idx);


}

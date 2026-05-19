package footmap.footmap_spring.service.boardService;

import footmap.footmap_spring.dao.boardDao.BoardMapper;
import footmap.footmap_spring.dto.PageRequestDTO;
import footmap.footmap_spring.dto.PageResponseDTO;
import footmap.footmap_spring.dto.boardDto.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardMapper boardMapper;
//    @Override
//    public List<Board> getBoardList() {
//
//        return boardMapper.getBoardList();
//    }

    @Override
    public PageResponseDTO<Board> getList(PageRequestDTO pageRequestDTO) {

        log.info("================서비스임플 getList===================");

        List<Board> dtoList = boardMapper.selectList(pageRequestDTO);

        log.info(dtoList);
        //[Board(idx=105, b_title=second textsssss, b_contents=effffffffffssss, b_cnt=10, del_chk=null, reg_date=07:01, b_nick=부산대뒤돌려차기, u_code=2, t_name=null, u_id=null),
        // Board(idx=95, b_title=title94, b_contents=contents94, b_cnt=2, del_chk=null, reg_date=2023-01-01, b_nick=부산대뒤돌려차기, u_code=2, t_name=null, u_id=null)]

        int total = boardMapper.getCount(pageRequestDTO);

        //log.info(pageRequestDTO); //PageRequestDTO(page=5, size=10, link=page=5&size=10)

        PageResponseDTO<Board> pageResponseDTO = PageResponseDTO.<Board>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();

        //log.info(pageRequestDTO); //PageRequestDTO(page=5, size=10, link=page=5&size=10)

        log.info(pageResponseDTO);
        //PageResponseDTO
        // (page=1, size=10, total=104, start=1, end=10, prev=false, next=true,
        // dtoList=[Board(idx=105, b_title=second textsssss, b_contents=effffffffffssss, b_cnt=10, del_chk=null, reg_date=07:01, b_nick=부산대뒤돌려차기, u_code=2, t_name=null, u_id=null),
        // Board(idx=96, b_title=title95, b_contents=contents95, b_cnt=1, del_chk=null, reg_date=2023-01-01, b_nick=부산대뒤돌려차기, u_code=2, t_name=null, u_id=null),
        // Board(idx=95, b_title=title94, b_contents=contents94, b_cnt=2, del_chk=null, reg_date=2023-01-01, b_nick=부산대뒤돌려차기, u_code=2, t_name=null, u_id=null)])

        return pageResponseDTO;
    }

    @Override
    public void writeAdd(Board board) {

        boardMapper.writeAdd(board);
    }

    @Override
    public Board getBoardView(int idx) {

        Board board = boardMapper.getBoardView(idx);

        log.info(board);
        return board;
    }

    @Override
    public void boardReadCountUpdate(int idx) {
        boardMapper.readCountUpdate(idx);
    }

    @Override
    public void writeUpdate(Board board) {

        log.info(board);

        boardMapper.writeUpdate(board);
    }

    @Override
    public Board deleteBoard(int idx) {
        boardMapper.deleteBoard(idx);
        return null;
    }






}

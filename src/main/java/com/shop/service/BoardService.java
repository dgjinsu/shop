package com.shop.service;

import com.shop.dto.BoardFormDto;
import com.shop.entity.Board;
import com.shop.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    public Long saveBoard(BoardFormDto boardFormDto) {
        Board board = BoardFormDto.createBoard(boardFormDto);
        boardRepository.save(board);
        return board.getId();
    }

    public List<Board> boardList() {

    }
}

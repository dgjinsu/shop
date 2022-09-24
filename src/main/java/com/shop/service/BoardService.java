package com.shop.service;

import com.shop.dto.BoardFormDto;
import com.shop.entity.Board;
import com.shop.entity.Member;
import com.shop.repository.BoardRepository;
import com.shop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, MemberRepository memberRepository) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }


    public Long saveBoard(BoardFormDto boardFormDto) {
        Board board = BoardFormDto.createBoard(boardFormDto);
        boardRepository.save(board);
        return board.getId();
    }

    public BoardFormDto boardDtl(Long boardId) {
        Board savedBoard = boardRepository.findById(boardId)
                .orElseThrow(EntityNotFoundException::new);

        BoardFormDto boardFormDto = BoardFormDto.of(savedBoard);
        return boardFormDto;
    }

    public List<BoardFormDto> boardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardFormDto> boardFormDtoList = new ArrayList<>();

        for(Board board : boardList) {
            boardFormDtoList.add(BoardFormDto.of(board));
        }

        return boardFormDtoList;
    }

    public Page<Board> boardPage(Pageable pageable) {
        return boardRepository.findAll(pageable);

    }

    @Transactional
    public Long updateBoard(BoardFormDto boardFormDto) {
        Board board = boardRepository.findById(boardFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        board.updateBoard(boardFormDto);
        boardRepository.save(board);
        return board.getId();
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}

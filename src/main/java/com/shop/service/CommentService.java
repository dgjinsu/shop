package com.shop.service;

import com.shop.dto.CommentFormDto;
import com.shop.entity.Board;
import com.shop.entity.Comment;
import com.shop.entity.Member;
import com.shop.repository.BoardRepository;
import com.shop.repository.CommentRepository;
import com.shop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;


    @Autowired
    public CommentService(CommentRepository commentRepository, BoardRepository boardRepository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }


    public Long saveComment(CommentFormDto commentFormDto, Long boardId, String name) {

        Board board = boardRepository.findById(boardId)
                        .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(name);

        commentFormDto.setBoard(board);
        commentFormDto.setMember(member);

        Comment comment = CommentFormDto.createBoard(commentFormDto);
        Comment savedComment = commentRepository.save(comment);

        return savedComment.getId();

        //저장하는거 까지 다 만듦. 이어서 저장한 커맨트 보이는 구현부 만들면 됨
    }
}

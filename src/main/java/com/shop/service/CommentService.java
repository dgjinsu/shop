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
import java.util.ArrayList;
import java.util.List;

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

        Comment comment = CommentFormDto.createComment(commentFormDto);
        Comment savedComment = commentRepository.save(comment);

        return savedComment.getId();
    }

    public List<CommentFormDto> findByBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(EntityNotFoundException::new);

        List<CommentFormDto> commentFormDtoList = new ArrayList<>();

        //commentList -> commentFormDtoList
        List<Comment> commentList = commentRepository.findByBoardOrderByIdAsc(board);
        for(Comment comment : commentList) {
            commentFormDtoList.add(CommentFormDto.of(comment));
        }
        return commentFormDtoList;
    }

    public void deleteComment(Long commentId) {
        boardRepository.deleteById(commentId);
        
        //삭제 구현 하면 됨
    }
}

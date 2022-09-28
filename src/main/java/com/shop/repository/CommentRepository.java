package com.shop.repository;

import com.shop.entity.Board;
import com.shop.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //게시판에 해당하는 댓글 가져오기
    List<Comment> findByBoardOrderByIdAsc(Board board);
}

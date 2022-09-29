package com.shop.controller;

import com.shop.dto.CommentFormDto;
import com.shop.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/write/{boardId}")
    public String writeComment(@Valid CommentFormDto commentFormDto, BindingResult bindingResult,
                                             @PathVariable Long boardId, Principal principal, Model model) {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@");
        if (bindingResult.hasErrors()) {
            return "board/boardForm";
        }

        try {
            commentService.saveComment(commentFormDto, boardId, principal.getName());
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "board/boardForm";
        }


        return "redirect:/board/{boardId}";
    }

    //댓글 삭제
    ///comment/delete/'+${comment.id}+'/'+${boardFormDto.id}
    @GetMapping("/delete/{commentId}/{boardId}")
    public String delete(@PathVariable Long commentId, @PathVariable Long boardId) {
        commentService.deleteComment(commentId);
        return "redirect:/board/" + boardId;
    }

    /**
     * 스프링 시큐리티를 사용할 경우 모든 POST 방식의 데이터 전송에는 CSRF 토큰 값이 있어야 함!!!!!
     */
    @PostMapping("/delete/{commentId}/{boardId}")
    public String delete1(@PathVariable Long commentId, @PathVariable Long boardId) {
        commentService.deleteComment(commentId);
        return "redirect:/board/" + boardId;
    }
}

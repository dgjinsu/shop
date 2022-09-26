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
    public @ResponseBody String writeComment(@Valid CommentFormDto commentFormDto, BindingResult bindingResult,
                                             @PathVariable Long boardId, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            return "board/boardForm";
        }

        try {
            commentService.saveComment(commentFormDto, boardId, principal.getName());
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "board/boardForm";
        }
        System.out.println(commentFormDto.getComment());


        return "board/boardDtl";
    }

}

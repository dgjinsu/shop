package com.shop.controller;

import com.shop.dto.BoardFormDto;
import com.shop.entity.Board;
import com.shop.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board/new")
    public String boardForm(Model model) {
        model.addAttribute("boardFormDto", new BoardFormDto());
        return "board/boardForm";
    }

    @PostMapping("/board/new")
    public String boardNew(@Valid BoardFormDto boardFormDto, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "board/boardForm";
        }

        try {
            boardService.saveBoard(boardFormDto);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/";
    }

    @GetMapping("/board")
    public String boardList(Model model) {
        List<BoardFormDto> boardList = boardService.boardList();
        System.out.println(boardList.get(0));
        model.addAttribute("boardList", boardList);
        return "board/boardList";
    }

}

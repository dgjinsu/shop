package com.shop.controller;

import com.shop.dto.BoardFormDto;
import com.shop.entity.Board;
import com.shop.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String boardNew(Model model) {
        model.addAttribute("boardFormDto", new BoardFormDto());
        return "board/boardForm";
    }

    @PostMapping("/board/new")
    public String boardNew(@Valid BoardFormDto boardFormDto, BindingResult bindingResult, Model model) {
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

    //@PageableDefault 의 기본 size 는 10이다
    @GetMapping("/board")
    public String boardList(Model model, @PageableDefault(page = 0, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
//        List<BoardFormDto> boardList = boardService.boardList();
//        model.addAttribute("boardList", boardList);

        Page<Board> boardPage = boardService.boardPage(pageable);

        int nowPage = boardPage.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, boardPage.getTotalPages());

        model.addAttribute("boardList", boardPage);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/boardList";
    }

    @GetMapping("/board/{boardId}")
    public String boardDetail(@PathVariable Long boardId, Model model) {
        BoardFormDto boardFormDto = boardService.boardDtl(boardId);
        model.addAttribute(boardFormDto);
        return "board/boardDtl";
    }

}

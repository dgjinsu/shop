package com.shop.dto;

import com.shop.entity.Board;
import com.shop.entity.Comment;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class BoardFormDto {

    private Long id;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    private String contents;

    private String createdBy;

    private LocalDateTime regTime;      // 등록시간

    //게시글에 해당하는 댓글 목록
    private List<Comment> commentList;

    private static ModelMapper modelMapper = new ModelMapper();

    public static Board createBoard(BoardFormDto boardFormDto) {
        return modelMapper.map(boardFormDto, Board.class);
    }

    public static BoardFormDto of(Board board) {
        return modelMapper.map(board, BoardFormDto.class);
    }

}

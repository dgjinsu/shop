package com.shop.dto;

import com.shop.entity.Board;
import com.shop.entity.Comment;
import com.shop.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter @Setter
public class CommentFormDto {

    private Long id;

    @NotBlank(message = "내용을 입력해주세요.")
    private String comment;

    private Board board;

    private Member member;

    private LocalDateTime regTime; //등록시간

    private static ModelMapper modelMapper = new ModelMapper();

    public static Comment createBoard(CommentFormDto commentFormDto) {
        return modelMapper.map(commentFormDto, Comment.class);
    }
}

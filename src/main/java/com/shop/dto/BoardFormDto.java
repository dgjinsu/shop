package com.shop.dto;

import com.shop.entity.Board;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class BoardFormDto {

    private Long id;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    private String contents;

    private static ModelMapper modelMapper = new ModelMapper();

    public static Board createBoard(BoardFormDto boardFormDto) {
        return modelMapper.map(boardFormDto, Board.class);
    }

}

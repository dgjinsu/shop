package com.shop.entity;

import com.shop.dto.BoardFormDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "board")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @OneToMany
    @JoinColumn()
    private List<Comment> commentList;

    public void updateBoard(BoardFormDto boardFormDto) {
        this.title = boardFormDto.getTitle();
        this.contents = boardFormDto.getContents();
    }


}
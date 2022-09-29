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

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE) //고아객체 제거 (게시판이 사라졌을 때 그에 달린 댓글도 함께 지움)
    private List<Comment> commentList;

    public void updateBoard(BoardFormDto boardFormDto) {
        this.title = boardFormDto.getTitle();
        this.contents = boardFormDto.getContents();
    }


}

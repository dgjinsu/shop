package com.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainItemDto {

    private Long id;

    private String itemNm;

    private String itemDetail;

    private String imgUrl;

    private Integer price;

    private int countVisit;

    @QueryProjection //Querydsl 로 결과 조회 시 엔티티 로 값을 받는것이 아닌 MainItemDto 객체로 바로 받아 오도록 활용
    public MainItemDto(Long id, String itemNm, String itemDetail, String imgUrl, Integer price, int countVisit) {
        this.id = id;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
        this.countVisit = countVisit;
    }
}

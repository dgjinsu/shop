package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
@Entity
@Table(name = "item") //엔티티와 매핑할 테이블을 지정
public class Item {

    @Id
    @Column(name="item_id") //필드와 컬럼 매핑
    @GeneratedValue(strategy = GenerationType.AUTO)     //키 값을 생성하는 전략(순서대로, 자동으로 등등)
    private Long id;                        //상품코드

    @Column(nullable = false, length = 50)  //null 값 불허, 길이 50제한
    private String itemNm;                  //상품명

    @Column(name = "price", nullable = false)
    private int price;                      //가격

    @Column(nullable = false)
    private int stockNumber;                //재고수량

    @Lob //BLOB,CLOB 타입 매핑(문자형 대용량 파일을 저장하는데 사용)
    @Column(nullable = false)
    private String itemDetail;              //상품상세설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;  //상품 판매 상태

    private LocalDateTime regTime;          //등록 시간

    private LocalDateTime updateTime;       //수정 시간
}

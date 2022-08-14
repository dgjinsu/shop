package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
@Entity
@Table(name = "item") //엔티티와 매핑할 테이블을 지정
public class Item extends BaseEntity{

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
    
    public void updateItem(ItemFormDto itemFormDto) { //엔티티에 비즈니스 로직을 추가하여 조금 더 객체지향적으로 코딩 가능
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;
        if(restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량: " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;
    }

    //주문 취소 시 수량 늘림
    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }
}

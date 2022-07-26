package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartOrderDto {

    private Long cartItemId;

    //장바구니에서 여러개의 상품을 주문하므로 자기 자신을 List 로 가짐
    private List<CartOrderDto> cartOrderDtoList;
}

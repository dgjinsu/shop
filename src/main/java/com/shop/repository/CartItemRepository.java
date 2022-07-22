package com.shop.repository;

import com.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    //상품이 장바구니에 들어있는지 확인
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);
}

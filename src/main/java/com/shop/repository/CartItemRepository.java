package com.shop.repository;

import com.shop.dto.CartDetailDto;
import com.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    //상품이 장바구니에 들어있는지 확인
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    /*
    CartDetailDto 리스트를 쿼리 하나로 조회하는 JPQL 문
    DTO 의 생성자를 이용하여 DTO 를 반환할 때는 new + 해당 DTO 패키지, 클래스명을 적음
    장바구니에 담겨있는 item.id 와 itemImg.item.id 와 같고
    대표 이미지인 것
    */
    @Query("select new com.shop.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) "
    + "from CartItem ci, ItemImg im "
    + "join ci.item i "
    + "where ci.cart.id = :cartId "
    + "and im.item.id = ci.item.id "
    + "and im.repimgYn = 'Y' "
    + "order by ci.regTime desc"
    )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}

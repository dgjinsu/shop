package com.shop.repository;

import com.shop.dto.ItemSearchDto;
import com.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    //상품 조회 조건을 담고 있는 Dto 와 페이징 정보를 담고 있는 pageable 객체를 파라미터로 받고 Page<Item> 객체 반환
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}

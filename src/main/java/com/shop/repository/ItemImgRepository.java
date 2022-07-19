package com.shop.repository;

import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    List<ItemImg> findByItemOrderByIdAsc(Item item);

    //상품의 대표 이미지를 찾음
    ItemImg findByItemIdAndRepImgYn(Long itemId, String repImgYn);
}

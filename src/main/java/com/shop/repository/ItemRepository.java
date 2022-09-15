package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>,
        ItemRepositoryCustom{
    /**
     * 쿼리 메소드 findBy~~~
     */

    //이름으로 상품 찾기
    List<Item> findByItemNm(String itemNM);

    //이름이나 상품 상세 설명으로 상품 찾기
    List<Item> findByItemNmOrItemDetail(String itemNM, String itemDetail);

    //price 변수보다 값이 작은 상품 찾기
    List<Item> findByPriceLessThan(Integer price);

    //price 변수보다 값이 작은 상품을 찾고 가격이 높은 순으로 조회
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    //JPQL은 테이블을 대상으로 하는것이 아니라 엔티티 객체를 대상으로 쿼리를 수행한다.
    //select 변수 from (어디에서) where (조건) like
    //추후에 공부 더하기
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    @Query(value = "select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);

    @Modifying
    @Query("update Item i set i.countVisit = i.countVisit + 1 where i.id = :id")
    int updateView(Long id);

}

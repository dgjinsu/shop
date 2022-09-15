package com.shop.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemSearchDto;
import com.shop.dto.MainItemDto;
import com.shop.dto.QMainItemDto;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import com.shop.entity.QItemImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 이부분 다시 복습하자
 */

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

    private JPAQueryFactory queryFactory; // 동적으로 쿼리를 생성하기 위해 JPAQueryFactory 사용

    public ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    //searchSellStatus 가 null 이라면 null 을 return 그렇지 않다면 QItem.item.itemSellStatus.eq(searchSellStatus) 를 리턴
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType){

        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        return QItem.item.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("itemNm", searchBy)){
            return QItem.item.itemNm.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)){
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

//        List<Item> content = queryFactory
//                .selectFrom(QItem.item)
//                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
//                        //searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus)
//                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
//                        searchByLike(itemSearchDto.getSearchBy(),
//                                itemSearchDto.getSearchQuery()))
//                .orderBy(QItem.item.id.desc()) //내림차순으로 정렬
//                .offset(pageable.getOffset()) //데이터를 가져올 시작 인덱스
//                .limit(pageable.getPageSize()) //한번에 가지고 올 개수
//                .fetch();
//        long total = queryFactory.select(Wildcard.count).from(QItem.item)
//                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
//                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
//                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
//                .fetchOne()
//                ;
            QueryResults<Item> results = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        //searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus)
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(),
                                itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc()) //내림차순으로 정렬
                .offset(pageable.getOffset()) //데이터를 가져올 시작 인덱스
                .limit(pageable.getPageSize()) //한번에 가지고 올 개수
                .fetchResults();
            List<Item> content = results.getResults();
            long total = results.getTotal();


        return new PageImpl<>(content, pageable, total);

    }

    private BooleanExpression itemNmLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%"+searchQuery + "%");
    }

    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        QueryResults<MainItemDto> results = queryFactory
                .select(
                        new QMainItemDto(item.id, item.itemNm, item.itemDetail, itemImg.imgUrl, item.price, item.countVisit)
                )
                .from(itemImg)
                .join(itemImg.item, item) //itemImg 와 item 을 내부 조인
                .where(itemImg.repImgYn.eq("Y")) // 상품 이미지의 경우 대표 상품 이미지만 불러옴
                .where(itemNmLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc()) //내림차순으로 정렬
                .offset(pageable.getOffset()) //데이터를 가져올 시작 인덱스
                .limit(pageable.getPageSize()) //한번에 가지고 올 개수
                .fetchResults();

        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content,pageable,total);


    }
}
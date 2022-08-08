package com.shop.service;

import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemImgDto;
import com.shop.dto.ItemSearchDto;
import com.shop.dto.MainItemDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, ItemImgService itemImgService, ItemImgRepository itemImgRepository, ItemImgRepository itemImgRepository1) {
        this.itemRepository = itemRepository;
        this.itemImgService = itemImgService;
        this.itemImgRepository = itemImgRepository1;
    }

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{

        //상품 등록
        Item item = itemFormDto.createItem(); //상품 등록 폼으로부터 받은 데이터를 이용하여 item 객체 생성
        itemRepository.save(item);
        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            if(i == 0) //첫번째 이미지를 대표 이미지로
                itemImg.setRepImgYn("Y");
            else //나머지는 대표 이미지 x
                itemImg.setRepImgYn("N");

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }
    
    //트랜잭션을 읽기 전용으로 설정할 경우 JPA가 더티체킹(변경감지)을 수행하지 않아서 성능 향상 가능
    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId){

        Item item = itemRepository.findById(itemId) //itemRepository 에서 조회
                .orElseThrow(EntityNotFoundException::new);

        List<ItemImg> itemImgList = itemImgRepository.findByItemOrderByIdAsc(item);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        //itemImgList -> itemImgDtoList
        for(ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList); //itemFormDto 를 생성하여 itemImgDtoList 세팅
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);

        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        //기존에 저장되어 있던 이미지들, 새로 수정하거나 추가한 이미지들을 하나씩 파라미터로 넘겨서 update
        for(int i = 0; i<itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));

        }

        return item.getId();
    }

    //아이템 관리 페이징 처리
    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDto,pageable);
    }

    //메인 페이징 처리
    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }
}

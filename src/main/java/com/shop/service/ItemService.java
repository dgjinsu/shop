package com.shop.service;

import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemImgDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
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
    //itemid : 30
    @Transactional(readOnly = true)
    public ItemFormDto getItemDt1(Long itemId){

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
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }
}

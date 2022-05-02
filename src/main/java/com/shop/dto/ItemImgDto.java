package com.shop.dto;

import com.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    //멤버변수로 ModelMapper 객체를 추가
    //구글링 해보니 @Bean 으로 등록하고 사용해도 될듯.
    private static ModelMapper modelMapper = new ModelMapper();

    //ItemImg 엔티티 객체를 파라미터로 받고, ItemImg 객체의 자료형과 멤버변수의 이름이 같으면 ItemImgDto 로 값을 복사해서 반환
    //static 메소드로 선언 -> ItemImgDto 객체를 생성하지 않아도 호출할 수 있다.
    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg,ItemImgDto.class);
    }
}

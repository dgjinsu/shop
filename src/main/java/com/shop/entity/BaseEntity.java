package com.shop.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})   //Auditing을 적용하기 위해
@MappedSuperclass  //JPA Entity 클래스들이 해당 추상 클래스를 상속할 경우 createdBy, modifiedBy 를 컬럼으로 인식
@Getter
public class BaseEntity extends BaseTimeEntity{

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;   // 등록자
    
    @LastModifiedBy
    private String modifiedBy;  // 수정자
}

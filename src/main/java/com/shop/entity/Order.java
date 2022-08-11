package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;    //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;    //주문상태

    // 연관 관계의 주인은 OrderItem 이다.
    // 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하는 옵션 CascadeType.ALL
    // 고아 객체 제거 orphanRemoval = true
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member);
        for(OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice(); //하나의 아이템 주문 엔티티의 총 가격을 전체 주문 엔티티 전체가격에 더함
        }
        return totalPrice;
    }

    //주문 취소, 수량 복구
    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
}

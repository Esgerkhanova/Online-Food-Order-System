package com.example.foodordersystem.model.dto.response;

import com.example.foodordersystem.model.entity.Order;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
public class OrderResponse {
    private Long id;
    private String customerName;
    private LocalDateTime orderDate;
    private Order.OrderStatus status;
    private BigDecimal totalAmount;
    private String deliveryAddress;
    private List<OrderItemResponse> items;

    @Data
    @Getter
    @Setter
    public static class OrderItemResponse {
        private Long id;
        private String menuItemName;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal subtotal;


    }
}
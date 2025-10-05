package com.example.foodordersystem.mapper;

import com.example.foodordersystem.model.dto.response.OrderResponse;
import com.example.foodordersystem.model.entity.Order;
import com.example.foodordersystem.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mappings({
            @Mapping(source = "user.username", target = "customerName"),
            @Mapping(source = "orderItems", target = "items")
    })
    OrderResponse toResponse(Order order);

    @Mappings({
            @Mapping(source = "menuItem.id", target = "id"),
            @Mapping(source = "menuItem.name", target = "menuItemName")
    })
    OrderResponse.OrderItemResponse toOrderItemResponse(OrderItem orderItem);

    List<OrderResponse> toResponseList(List<Order> orders);
}

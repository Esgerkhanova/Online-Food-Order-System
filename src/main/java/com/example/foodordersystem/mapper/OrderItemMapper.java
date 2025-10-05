package com.example.foodordersystem.mapper;

import com.example.foodordersystem.model.dto.response.OrderResponse;
import com.example.foodordersystem.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MenuItemMapper.class})
public interface OrderItemMapper {

    @Mapping(target = "menuItemName", source = "menuItem.name")
    @Mapping(target = "price", source = "menuItem.price")
    OrderResponse.OrderItemResponse toResponse(OrderItem orderItem);
}

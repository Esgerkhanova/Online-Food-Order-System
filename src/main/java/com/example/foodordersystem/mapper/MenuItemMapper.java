package com.example.foodordersystem.mapper;

import com.example.foodordersystem.model.dto.MenuItemDTO;
import com.example.foodordersystem.model.dto.request.MenuItemRequest;
import com.example.foodordersystem.model.entity.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    MenuItemMapper INSTANCE = Mappers.getMapper(MenuItemMapper.class);

    MenuItem toEntity(MenuItemRequest request);
    MenuItemDTO toDTO(MenuItem entity);

    void updateMenuItemFromRequest(MenuItemRequest request, @MappingTarget MenuItem menuItem);
}

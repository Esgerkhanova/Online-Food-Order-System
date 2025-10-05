package com.example.foodordersystem.service;

import com.example.foodordersystem.model.entity.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface MenuService {

    Page<MenuItem> getAllMenuItems(Pageable pageable, String category,
                                   BigDecimal minPrice, BigDecimal maxPrice, String search);

    MenuItem getMenuItemById(Long id);

    List<MenuItem> getMenuItemsByCategory(String category, Pageable pageable);

    List<MenuItem> getMenuItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<MenuItem> searchMenuItems(String searchTerm);
}

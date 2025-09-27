package com.example.foodordersystem.service;

import com.example.foodordersystem.model.entity.MenuItem;
import com.example.foodordersystem.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class MenuService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public Page<MenuItem> getAllMenuItems(Pageable pageable, String category,
                                          BigDecimal minPrice, BigDecimal maxPrice, String search) {

        // Multiple criteria ilə search
        if (search != null && !search.trim().isEmpty()) {
            return menuItemRepository.findBySearchCriteria(search, pageable);
        }

        if (category != null && minPrice != null && maxPrice != null) {
            return menuItemRepository.findByCategoryAndPriceRange(category, minPrice, maxPrice, pageable);
        }

        if (category != null) {
            return menuItemRepository.findByCategoryAndAvailableTrue(category, pageable);
        }

        if (minPrice != null && maxPrice != null) {
            return menuItemRepository.findByPriceRangeAndAvailableTrue(minPrice, maxPrice, pageable);
        }

        return menuItemRepository.findByAvailableTrue(pageable);
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
    }

    public List<MenuItem> getMenuItemsByCategory(String category,Pageable pageable) {
        return menuItemRepository.findByCategory(category, pageable);
    }

    public List<MenuItem> getMenuItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return menuItemRepository.findByPriceRange(minPrice, maxPrice);
    }

    public List<MenuItem> searchMenuItems(String searchTerm) {
        return menuItemRepository.findByNameContainingIgnoreCase(searchTerm);
    }
}
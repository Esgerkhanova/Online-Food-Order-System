package com.example.foodordersystem.controller;

import com.example.foodordersystem.model.entity.MenuItem;
import com.example.foodordersystem.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<MenuItem> getAllMenuItems(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String search) {

        if (category != null) {
            return menuService.getMenuItemsByCategory(category);
        }
        if (minPrice != null && maxPrice != null) {
            return menuService.getMenuItemsByPriceRange(minPrice, maxPrice);
        }
        if (search != null) {
            return menuService.searchMenuItems(search);
        }

        return menuService.getAllMenuItems();
    }

    @GetMapping("/{id}")
    public MenuItem getMenuItem(@PathVariable Long id) {
        return menuService.getMenuItemById(id);
    }
}
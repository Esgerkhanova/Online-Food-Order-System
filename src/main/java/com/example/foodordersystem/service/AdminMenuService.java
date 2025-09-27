package com.example.foodordersystem.service;

import com.example.foodordersystem.model.dto.request.MenuItemRequest;
import com.example.foodordersystem.model.entity.MenuItem;
import com.example.foodordersystem.repository.MenuItemRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class AdminMenuService {

    private final MenuItemRepository menuItemRepository;

    public AdminMenuService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem createMenuItem(@Valid MenuItemRequest request, String username) {
        // Burada request → entity mapping edilməlidir
        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.getName());
        menuItem.setPrice(request.getPrice());
        menuItem.setCategory(request.getCategory());
        menuItem.setAvailable(true);
        return menuItemRepository.save(menuItem);
    }

    public MenuItem updateMenuItem(Long id, @Valid MenuItemRequest request, String username) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        menuItem.setName(request.getName());
        menuItem.setPrice(request.getPrice());
        menuItem.setCategory(request.getCategory());
        return menuItemRepository.save(menuItem);
    }

    public void deleteMenuItem(Long id, String username) {
        if (!menuItemRepository.existsById(id)) {
            throw new RuntimeException("Menu item not found");
        }
        menuItemRepository.deleteById(id);
    }

    public MenuItem toggleAvailability(Long id, boolean available, String username) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
        menuItem.setAvailable(available);
        return menuItemRepository.save(menuItem);
    }

    public Page<MenuItem> getAllMenuItemsForAdmin(Pageable pageable, String category, Boolean available, String username) {
        if (category != null && available != null) {
            return menuItemRepository.findByCategoryAndAvailable(category, available, pageable);
        } else if (category != null) {
            @SuppressWarnings("unchecked")
            Page<MenuItem> result = (Page<MenuItem>) menuItemRepository.findByCategory(category, pageable);
            return result;
        } else if (available != null) {
            @SuppressWarnings("unchecked")
            Page<MenuItem> result = (Page<MenuItem>) menuItemRepository.findByAvailable(available, pageable);
            return result;
        }
        return menuItemRepository.findAll(pageable);
    }

    public List<String> getAllCategories() {
        List<MenuItem> allItems = menuItemRepository.findAll();
        return allItems.stream()
                .map(MenuItem::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<MenuItem> bulkUpdateAvailability(List<com.example.foodordersystem.controller.AdminMenuController.BulkAvailabilityRequest> requests,
                                                 String username) {
        List<Long> ids = requests.stream().map(r -> r.getId()).toList();
        List<MenuItem> items = menuItemRepository.findAllById(ids);

        items.forEach(item -> {
            requests.stream()
                    .filter(r -> r.getId().equals(item.getId()))
                    .findFirst()
                    .ifPresent(r -> item.setAvailable(r.isAvailable()));
        });

        return menuItemRepository.saveAll(items);
    }
}

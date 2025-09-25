package com.example.foodordersystem.model.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MenuItemDtO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private boolean available;
    private String imageUrl;
}
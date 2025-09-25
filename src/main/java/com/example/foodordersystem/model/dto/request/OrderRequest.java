package com.example.foodordersystem.model.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class OrderRequest {
    @NotNull(message = "Items cannot be null")
    @Size(min = 1, message = "At least one item is required")
    List<OrderItemRequest> orderItems;


    public void setItems(List<OrderItemRequest> orderItems) {
        this.orderItems = orderItems != null ? orderItems : new ArrayList<>();
    }

}

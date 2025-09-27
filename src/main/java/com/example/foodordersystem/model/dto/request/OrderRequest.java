package com.example.foodordersystem.model.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema(description = "Sifariş yaratmaq üçün məlumatlar")
public class OrderRequest {

    @NotNull(message = "Sifariş maddələri null ola bilməz")
    @NotEmpty(message = "Sifariş ən azı bir maddə içərməlidir")
    @Size(min = 1, max = 50, message = "Sifariş 1-50 maddə arasında ola bilər")
    @Valid
    @Schema(description = "Sifariş ediləcək maddələrin siyahısı", required = true)
    private List<OrderItemRequest> orderItems;

    @Size(max = 500, message = "Çatdırılma ünvanı 500 simvoldan çox ola bilməz")
    @Schema(description = "Çatdırılma ünvanı", example = "Bakı ş., Nəsimi r., Azadlıq pr. 123")
    private String deliveryAddress;

    @Size(max = 200, message = "Qeyd 200 simvoldan çox ola bilməz")
    @Schema(description = "Sifariş üçün xüsusi qeydlər", example = "Extra acı olmasın")
    private String notes;

    @Schema(description = "Çatdırılma növü", example = "DELIVERY", allowableValues = {"PICKUP", "DELIVERY"})
    @Builder.Default
    private String deliveryType = "DELIVERY";



    // Validation helper methods
    public boolean hasValidItems() {
        return orderItems != null && !orderItems.isEmpty() &&
                orderItems.stream().allMatch(item -> item.getMenuItemId() != null && item.getQuantity() > 0);
    }
}


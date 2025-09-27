package com.example.foodordersystem.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Sifariş maddəsi məlumatları")
public class OrderItemRequest {

    @NotNull(message = "Menyu maddəsi ID-si boş ola bilməz")
    @Positive(message = "Menyu maddəsi ID-si müsbət rəqəm olmalıdır")
    @Schema(description = "Menyu maddəsinin ID-si", example = "1", required = true)
    private Long menuItemId;

    @NotNull(message = "Miqdar boş ola bilməz")
    @Min(value = 1, message = "Miqdar ən azı 1 olmalıdır")
    @Max(value = 50, message = "Miqdar maksimum 50 ola bilər")
    @Schema(description = "Sifariş ediləcək miqdar", example = "2", required = true)
    private Integer quantity;

    @Size(max = 100, message = "Xüsusi qeyd 100 simvoldan çox ola bilməz")
    @Schema(description = "Bu maddə üçün xüsusi qeydlər", example = "Az duzlu olsun")
    private String specialInstructions;

    @AssertTrue(message = "Miqdar və menyu ID-si müsbət olmalıdır")
    public boolean isValidOrderItem() {
        return menuItemId != null && menuItemId > 0 &&
                quantity != null && quantity > 0;
    }
}


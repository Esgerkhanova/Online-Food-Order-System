package com.example.foodordersystem.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "İstifadəçi giriş məlumatları")
public class AuthRequest {
    @NotBlank(message = "İstifadəçi adı boş ola bilməz")
    @Size(min = 3, max = 50, message = "İstifadəçi adı 3-50 simvol arasında olmalıdır")
    @Schema(description = "İstifadəçi adı", example = "john_doe", required = true)
    private String username;


    @NotBlank(message = "Parol boş ola bilməz")
    @Size(min = 6, max = 100, message = "Parol ən azı 6 simvol olmalıdır")
    @Schema(description = "İstifadəçi parolu", example = "password123", required = true)
    private String password;

}

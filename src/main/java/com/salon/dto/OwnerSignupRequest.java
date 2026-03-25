package com.salon.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerSignupRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String phone;

    @NotBlank(message = "Salon name is required")
    private String salonName;

    @NotBlank(message = "Salon type is required")
    @Pattern(regexp = "^(unisex|male|female)$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Salon type must be unisex, male, or female")
    private String salonType;

    @NotBlank(message = "Address is required")
    private String address;
}

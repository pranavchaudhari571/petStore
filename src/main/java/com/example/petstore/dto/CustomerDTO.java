package com.example.petstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomerDTO {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Password is required")
    private String customerPassword;

    @NotBlank(message = "Customer address is required")
    private String customerAddress;

    @Email(message = "Invalid email format")
    private String customerEmail;

    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
    private String customerPhone;
}

package com.example.canteenmgmtsys.customer.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomerRequestDto {
    private String customerName;
    @Pattern(regexp = "[a-zA-Z0-9]+@gmail\\.com", message = "Invalid email format")
    private String email;
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number format. It should be a 10-digit number.")
    private String phoneNumber;

    private String password;
}

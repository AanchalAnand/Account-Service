package com.eazybytes.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class CustomerDto {
    @NotNull(message = "Name should not be empty")
    private String name;

    @Email(message = "Please enter the valid email")
    private String email;

    @NotNull(message = "Mobile number should not be null")
    @Size(min = 10, max = 10, message = "Length of mobile number should be 10")
    private String mobileNumber;

    private AccountsDto accountsDto;
}

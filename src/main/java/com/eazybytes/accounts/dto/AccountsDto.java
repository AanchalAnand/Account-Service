package com.eazybytes.accounts.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data
public class AccountsDto {

    private Long accountNumber;

    @NotNull(message = "Account type cannot be null")
    private String accountType;

    @NotNull(message = "Branch address cannot be null")
    private String branchAddress;
}

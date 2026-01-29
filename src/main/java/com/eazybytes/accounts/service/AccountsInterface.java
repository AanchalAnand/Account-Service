package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;

public interface AccountsInterface {

     void createAccount(CustomerDto customerDto);

     CustomerDto getAccountDetails(String mobileNumber);
}

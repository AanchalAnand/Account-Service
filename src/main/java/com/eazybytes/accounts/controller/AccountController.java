package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.AccountsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountController {

    @Autowired
     AccountsInterface accountsInterface;

    @PostMapping("/createAccount")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {

            accountsInterface.createAccount(customerDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
        }
        @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber) {
        CustomerDto customerDto = accountsInterface.getAccountDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
        }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccounts(@RequestBody CustomerDto customerDto) {

        boolean isUpdated = accountsInterface.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/mobileNumber")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam String mobileNumber) {
        boolean isDeleted = accountsInterface.deleteCustomer(mobileNumber);

        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

}

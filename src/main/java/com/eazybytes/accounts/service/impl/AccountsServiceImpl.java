package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exceptions.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exceptions.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.AccountsInterface;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


@Service
@AllArgsConstructor
public class AccountsServiceImpl implements AccountsInterface {

    @Autowired
     AccountsRepository accountsRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("This mobile number already exists.");
        }
        customer.setCreatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);

        accountsRepository.save(createNewAccount(savedCustomer));
    }


    private Accounts createNewAccount(Customer customer) {

        Accounts newAccounts = new Accounts();
        newAccounts.setAccountNumber(new Random().nextLong());
        newAccounts.setAccountType(AccountsConstants.SAVINGS);
        newAccounts.setBranchAddress(AccountsConstants.ADDRESS);
        newAccounts.setCustomerId(new Random().nextLong());
        newAccounts.setCreatedAt(LocalDateTime.now());
        newAccounts.setCreatedBy("Aanchal");

        return newAccounts;
    }

    @Override
    public CustomerDto getAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found")
        );

        Long customerId = customer.getCustomerId();
        Accounts accountDetails = accountsRepository.findByCustomerId(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Account not found")
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accountDetails, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;

        AccountsDto accountsDto = customerDto.getAccountsDto();

        if(accountsDto != null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account not found")
            );
            AccountsMapper.maptToAccounts(accountsDto, accounts);
            accounts =  accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer not found")
            );

            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;


        }
        return isUpdated;
    }

    @Override
    public boolean deleteCustomer(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found")
        );
        Long customerId = customer.getCustomerId();
        accountsRepository.deleteById(customerId);
        customerRepository.deleteById(customerId);

        return true;
    }


}

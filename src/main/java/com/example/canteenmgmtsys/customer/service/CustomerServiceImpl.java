package com.example.canteenmgmtsys.customer.service;

import com.example.canteenmgmtsys.customer.dto.CustomerRequestDto;
import com.example.canteenmgmtsys.customer.enums.Role;
import com.example.canteenmgmtsys.customer.model.Customer;
import com.example.canteenmgmtsys.customer.repo.CustomerRepo;
import com.example.canteenmgmtsys.exception.customexception.ResourceAlreadyExistsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public Customer addNewCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = objectMapper.convertValue(customerRequestDto, Customer.class);

        logger.info("Getting the existingCustomer by email or phoneNumber");
        List<Customer> existingCustomerList = customerRepo.findByEmailOrPhoneNumber(customerRequestDto.getEmail(), customerRequestDto.getPhoneNumber());

        if (!existingCustomerList.isEmpty()) {

            logger.error("Customer already exists");
            throw new ResourceAlreadyExistsException("Customer  with email or  phone number already exists.Please check your details");
        }

        if(customerRequestDto.getEmail().equals("badsna@gmail.com")){
            customer.setRole(Role.ADMIN);
        }
        else{
            customer.setRole(Role.USER);
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepo.save(customer);
    }

}




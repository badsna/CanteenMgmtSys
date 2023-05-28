package com.example.canteenmgmtsys.customer.service;

import com.example.canteenmgmtsys.customer.dto.CustomerRequestDto;
import com.example.canteenmgmtsys.customer.dto.CustomerResponseDto;
import com.example.canteenmgmtsys.customer.model.Customer;

import java.util.List;

public interface CustomerService {
    public Customer addNewCustomer(CustomerRequestDto customerRequestDto) ;
}

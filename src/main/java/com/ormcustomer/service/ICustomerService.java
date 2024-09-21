package com.ormcustomer.service;

import com.ormcustomer.model.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> findAll();

    Customer findById(Long id);

    void save(Customer customer);

    void remove(Long id);
}

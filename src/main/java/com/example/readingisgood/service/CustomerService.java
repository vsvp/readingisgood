package com.example.readingisgood.service;

import com.example.readingisgood.entity.Customer;
import com.example.readingisgood.entity.Order;
import com.example.readingisgood.model.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {

    ResponseEntity<Response<Customer>> createCustomer(Customer customer);

    Customer findByEmail(String email);

    ResponseEntity<Response<List<Order>>> listAllOrdersByCustomerMail(String mail, Integer pageNo, Integer pageSize, String sortBy);

}

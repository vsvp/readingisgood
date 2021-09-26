package com.example.readingisgood.controller;

import com.example.readingisgood.entity.Customer;
import com.example.readingisgood.entity.Order;
import com.example.readingisgood.model.Response;
import com.example.readingisgood.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/createCustomer", method = RequestMethod.POST)
    public ResponseEntity<Response<Customer>> createCustomer(@RequestBody Customer customer) {

        return customerService.createCustomer(customer);
    }

    @RequestMapping(value = "/listOrders/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<Response<List<Order>>> getAllOrdersOfCustomer(@PathVariable(value ="customerId" , required = true) String customerId,
                                                        @RequestParam(defaultValue = "0") Integer pageNo,
                                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                                        @RequestParam(defaultValue = "startDate") String sortBy) {

        return customerService.listAllOrdersByCustomerId(customerId,pageNo, pageSize, sortBy);
    }
}

package com.example.readingisgood.controller;


import com.example.readingisgood.entity.Customer;
import com.example.readingisgood.entity.Order;
import com.example.readingisgood.model.OrderRequest;
import com.example.readingisgood.model.Response;
import com.example.readingisgood.model.Result;
import com.example.readingisgood.service.OrderService;
import com.example.readingisgood.util.ReadingUtil;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;


    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public ResponseEntity<Response<Order>> createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @RequestMapping(value = "/getOrder/{id]", method = RequestMethod.GET)
    public ResponseEntity<Response<Order>> getOrderById(@PathVariable(value = "id", required = true) String id) {
        return orderService.getOrderById(id);
    }
}

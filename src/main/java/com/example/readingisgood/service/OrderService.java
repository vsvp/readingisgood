package com.example.readingisgood.service;

import com.example.readingisgood.entity.Order;
import com.example.readingisgood.model.OrderRequest;
import com.example.readingisgood.model.Response;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity<Response<Order>> createOrder(OrderRequest orderRequest);

    ResponseEntity<Response<Order>> getOrderById(String id);
}

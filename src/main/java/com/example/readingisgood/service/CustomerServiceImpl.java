package com.example.readingisgood.service;

import com.example.readingisgood.controller.CustomerController;
import com.example.readingisgood.entity.Book;
import com.example.readingisgood.entity.Customer;
import com.example.readingisgood.entity.Order;
import com.example.readingisgood.model.Response;
import com.example.readingisgood.model.Result;
import com.example.readingisgood.repository.CustomerRepository;
import com.example.readingisgood.repository.OrderRepository;
import com.example.readingisgood.util.ReadingUtil;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ResponseEntity<Response<Customer>> createCustomer(Customer customer) {

        Response<Customer> resp = new Response<>();
        ResponseEntity<Response<Customer>> response;
        Customer customerRef;

        try {

            if (Objects.isNull(customer) || Strings.isEmpty(customer.getEmail()) || Strings.isEmpty(customer.getName()) || Strings.isEmpty(customer.getSurname())) {
                logger.warn("null references");
                response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                response.getBody().setResult(ReadingUtil.buildGeneralFailResult());
                return response;
            }

            customerRef = customerRepository.findByEmail(customer.getEmail());

            if (customerRef != null) {
                logger.warn("[createCustomer()] this email is used by another user");
                response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                response.getBody().setResult(ReadingUtil.buildResult(false, ReadingUtil.ERROR_CODE_FAIL_DUPLICATE_ENTITY));
                return response;
            }

            customer.setId(UUID.randomUUID().toString());
            customerRef = customerRepository.save(customer);

        } catch (Exception e) {
            logger.warn("[createCustomer()] Exception occured while the process. Exception = {} ", e);
            response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
            response.getBody().setResult(ReadingUtil.buildGeneralFailResult());
            return response;
        }

        response = new ResponseEntity<>(resp, HttpStatus.OK);
        response.getBody().setResult(ReadingUtil.buildGeneralSuccessResult());
        response.getBody().setResultObj(customerRef);

        return response;
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public ResponseEntity<Response<List<Order>>> listAllOrdersByCustomerMail(String mail, Integer pageNo, Integer pageSize, String sortBy) {

        Response<List<Order>> resp = new Response<>();
        ResponseEntity<Response<List<Order>>> response;
        List<Order> orderList = null;

        try {

            if (Strings.isEmpty(mail)) {
                logger.warn("[listAllOrdersByCustomerMail()] field 'mail' can not be null");
                response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                response.getBody().setResult(ReadingUtil.buildGeneralFailResult());
                return response;
            }

            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
            Page<Order> pagedResult = orderRepository.findOrdersByCustomerMail(mail, paging);

            if (pagedResult.hasContent()) {
                orderList = pagedResult.getContent();
            }

//            orderList = orderRepository.findOrdersByCustomerMail(mail, pageNo, pageSize, sortBy);

            if (orderList == null || orderList.isEmpty()) {
                logger.warn("[listAllOrdersByCustomerMail()] no order found for customer. Customer mail");
                response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                response.getBody().setResult(ReadingUtil.buildResult(false, ReadingUtil.ERROR_CODE_FAIL_NO_MATCH));
                return response;
            }

        } catch (Exception e) {
            logger.warn("[listAllOrdersByCustomerMail()] Exception occured while operation. Exception = {} ", e);
            response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
            response.getBody().setResult(ReadingUtil.buildGeneralFailResult());
            return response;
        }

        response = new ResponseEntity<>(resp, HttpStatus.OK);
        response.getBody().setResult(ReadingUtil.buildGeneralSuccessResult());
        response.getBody().setResultObj(orderList);

        return response;
    }
}

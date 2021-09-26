package com.example.readingisgood.service;

import com.example.readingisgood.entity.Book;
import com.example.readingisgood.entity.Customer;
import com.example.readingisgood.entity.Order;
import com.example.readingisgood.model.OrderRequest;
import com.example.readingisgood.model.Response;
import com.example.readingisgood.repository.BookRepository;
import com.example.readingisgood.repository.CustomerRepository;
import com.example.readingisgood.repository.OrderRepository;
import com.example.readingisgood.util.ReadingUtil;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public ResponseEntity<Response<Order>> createOrder(OrderRequest orderRequest) {

        Response<Order> resp = new Response<>();
        ResponseEntity<Response<Order>> response;

        if (Objects.isNull(orderRequest) || Strings.isEmpty(orderRequest.getCustomerId()) || Strings.isEmpty(orderRequest.getBookId())) {
            logger.warn("[createOrder()] request failed. Fields can not be empty");
            response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
            response.getBody().setResult(ReadingUtil.buildGeneralFailResult());
            return response;
        }

        if (orderRequest.getBookOrderSize() < 1) {
            logger.warn("[createOrder()] Can not order 0 or negative number amount of book");
            response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
            response.getBody().setResult(ReadingUtil.buildResult(false, ReadingUtil.ERROR_CODE_FAIL_NO_NEGATIVE_INTEGER_ALLOWED));
            return response;
        }

        try {

            Customer customer = customerRepository.findById(orderRequest.getCustomerId());

            if (Objects.isNull(customer)) {
                logger.warn("[createOrder()] no matching customer found with id= {}", orderRequest.getCustomerId());
                response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                response.getBody().setResult(ReadingUtil.buildResult(false, ReadingUtil.ERROR_CODE_FAIL_NO_MATCH));
                return response;
            }

            Book bookToBeOrdered = bookRepository.findById(orderRequest.getBookId());

            if (Objects.isNull(bookToBeOrdered)) {
                logger.warn("[createOrder()] no matching book found with id={}", orderRequest.getBookId());
                response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                response.getBody().setResult(ReadingUtil.buildResult(false, ReadingUtil.ERROR_CODE_FAIL_NO_MATCH));
                return response;
            }

            if (bookToBeOrdered.getStock() == 0) {
                logger.warn("[createOrder()] no stock left for book with id = {} ", orderRequest.getBookId());
                response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                response.getBody().setResult(ReadingUtil.buildResult(false, ReadingUtil.ERROR_CODE_FAIL_NO_STOCK_LEFT));
                return response;
            }

            Order newOrder = new Order();
            newOrder.setId(UUID.randomUUID().toString());
            newOrder.setBookId(bookToBeOrdered.getId());
            newOrder.setCustomerId(orderRequest.getCustomerId());
            newOrder.setStartDate(Calendar.getInstance().getTime());
            newOrder.setCost(bookToBeOrdered.getPrice() * orderRequest.getBookOrderSize());
            newOrder.setBookCount(orderRequest.getBookOrderSize());

            Order order = orderRepository.save(newOrder);

            if (Objects.isNull(order)) {
                logger.error("[createOrder()] operation failed");
                response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                response.getBody().setResult(ReadingUtil.buildGeneralFailResult());
                return response;
            }

            bookToBeOrdered.decreaseStockNumber(orderRequest.getBookOrderSize());

            bookRepository.save(bookToBeOrdered);

        } catch (Exception e) {
            logger.error("[createOrder()] Exception occured while operation. Exception = {} ", e);
            response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
            response.getBody().setResult(ReadingUtil.buildGeneralFailResult());
            return response;
        }

        response = new ResponseEntity<>(resp, HttpStatus.OK);
        response.getBody().setResult(ReadingUtil.buildGeneralSuccessResult());
        return response;
    }

    @Override
    public ResponseEntity<Response<Order>> getOrderById(String id) {

        Response<Order> resp = new Response<>();
        ResponseEntity<Response<Order>> response;

        Order order = null;

        if (Strings.isEmpty(id)) {
            logger.warn("[getOrderById()] request failed. Field id can not be empty");
            response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
            response.getBody().setResult(ReadingUtil.buildGeneralFailResult());
            return response;
        }

        try {

            order = orderRepository.findById(id);

            if (Objects.isNull(order)) {
                logger.warn("[getOrderById()] no matching order found with id ={}", id);
                response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                response.getBody().setResult(ReadingUtil.buildResult(false, ReadingUtil.ERROR_CODE_FAIL_NO_MATCH));
                return response;
            }

        } catch (Exception e) {
            logger.error("[createOrder()] Exception occured while operation. Exception = {} ", e);
            response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
            response.getBody().setResult(ReadingUtil.buildGeneralFailResult());
            return response;
        }

        response = new ResponseEntity<>(resp, HttpStatus.OK);
        response.getBody().setResult(ReadingUtil.buildGeneralSuccessResult());
        response.getBody().setResultObj(order);
        return response;
    }
}

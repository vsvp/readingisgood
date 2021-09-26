package com.example.readingisgood;

import com.example.readingisgood.controller.CustomerController;
import com.example.readingisgood.entity.Customer;
import com.example.readingisgood.entity.Order;
import com.example.readingisgood.model.Response;
import com.example.readingisgood.repository.CustomerRepository;
import com.example.readingisgood.service.CustomerService;
import com.example.readingisgood.util.ReadingUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void shouldReturnSuccessWhenCreatingCustomer(){

        Response<Customer> resp = new Response<>();

        String email = "guneycandemir@hotmail.com";
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setEmail(email);
        customer.setName("guneycan");
        customer.setSurname("demir");

        resp.setResultObj(customer);
        resp.setResult(ReadingUtil.buildGeneralSuccessResult());

        ResponseEntity<Response<Customer>> result = new ResponseEntity<>(resp, HttpStatus.OK);

        when(customerService.createCustomer(customer)).thenReturn(result);
//        when(customerRepository.findByEmail(email)).thenReturn(null);

        final ResponseEntity<Response<Customer>> actualResult = customerService.createCustomer(customer);
        assertEquals(actualResult, result);

    }

    @Test
    public void shouldReturnFailWhenCustomerTriesToRegisterWithSameEmailAdress(){

        Response<Customer> resp = new Response<>();

        String email = "guneycandemir@hotmail.com";
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setEmail(email);
        customer.setName("guneycan");
        customer.setSurname("demir");

        resp.setResultObj(customer);
        resp.setResult(ReadingUtil.buildResult(false, ReadingUtil.ERROR_CODE_FAIL_DUPLICATE_ENTITY));

        ResponseEntity<Response<Customer>> result = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);

        when(customerService.createCustomer(customer)).thenReturn(result);
//        when(customerRepository.findByEmail(email)).thenReturn(customer);

        final ResponseEntity<Response<Customer>> actualResult = customerService.createCustomer(customer);
        assertEquals(actualResult, result);

    }

    @Test
    public void shouldReturnFailIfCreateCustomerBodyParametersMissing(){
        Response<Customer> resp = new Response<>();

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setName("guneycan");
        customer.setSurname("demir");

        resp.setResultObj(customer);
        resp.setResult(ReadingUtil.buildGeneralFailResult());

        ResponseEntity<Response<Customer>> result = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);

        when(customerService.createCustomer(customer)).thenReturn(result);

        final ResponseEntity<Response<Customer>> actualResult = customerService.createCustomer(customer);
        assertEquals(actualResult, result);
    }

    @Test
    public void shouldReturnSuccessWhenGetAllOrdersOfCustomer(){
        Response<List<Order>> resp = new Response<>();
        ResponseEntity<Response<List<Order>>> response;


        String customerId = "123456";
        List<Order> orderList = new ArrayList<>();

        Order order = new Order();
        order.setBookCount(2);
        order.setCost(66);
        order.setStartDate(new Date());
        order.setBookId("123");
        order.setCustomerId("123456");
        order.setId(UUID.randomUUID().toString());

        orderList.add(order);

        resp.setResultObj(orderList);
        resp.setResult(ReadingUtil.buildGeneralSuccessResult());

        ResponseEntity<Response<List<Order>>> result = new ResponseEntity<>(resp, HttpStatus.OK);

        when(customerService.listAllOrdersByCustomerId(customerId,1 , 10 , null)).thenReturn(result);

        final ResponseEntity<Response<List<Order>>> actualResult = customerService.listAllOrdersByCustomerId(customerId,1 , 10 , null);
        assertEquals(actualResult, result);

    }

    @Test
    public void shouldReturnFailWhenParameterMissing(){
        Response<List<Order>> resp = new Response<>();
        ResponseEntity<Response<List<Order>>> response;

        resp.setResult(ReadingUtil.buildGeneralFailResult());

        ResponseEntity<Response<List<Order>>> result = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);

        when(customerService.listAllOrdersByCustomerId(anyString(),anyInt() , anyInt() , anyString())).thenReturn(result);

        final ResponseEntity<Response<List<Order>>> actualResult = customerService.listAllOrdersByCustomerId(anyString(),anyInt() , anyInt() , anyString());
        assertEquals(actualResult, result);

    }

}

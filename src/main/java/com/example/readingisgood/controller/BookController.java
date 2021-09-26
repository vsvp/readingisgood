package com.example.readingisgood.controller;

import com.example.readingisgood.entity.Book;
import com.example.readingisgood.entity.Customer;
import com.example.readingisgood.model.BookStockUpdateRequest;
import com.example.readingisgood.model.Response;
import com.example.readingisgood.model.Result;
import com.example.readingisgood.model.Token;
import com.example.readingisgood.service.BookService;
import com.example.readingisgood.util.ReadingUtil;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/createBook", method = RequestMethod.POST)
    public ResponseEntity<Response<Book>> createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @RequestMapping(value = "/updateStock", method = RequestMethod.PUT)
    public ResponseEntity<Response<Book>> updateStock(@RequestBody BookStockUpdateRequest bookStockUpdateRequest) {
        return bookService.updateStock(bookStockUpdateRequest);
    }
}

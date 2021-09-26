package com.example.readingisgood.service;

import com.example.readingisgood.controller.BookController;
import com.example.readingisgood.entity.Book;
import com.example.readingisgood.model.BookStockUpdateRequest;
import com.example.readingisgood.model.Response;
import com.example.readingisgood.repository.BookRepository;
import com.example.readingisgood.util.ReadingUtil;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookRepository bookRepository;

    @Override
    public ResponseEntity<Response<Book>> createBook(Book book) {

        Response<Book> resp = new Response<>();
        ResponseEntity<Response<Book>> response;

        if (Objects.isNull(book) || Strings.isEmpty(book.getName()) || Strings.isEmpty(book.getAuthor())) {
            logger.warn("[createBook()] null references not allowed for method");
            response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
            response.getBody().setResult(ReadingUtil.buildGeneralFailResult());
            return response;
        }

        Book bookRef;

        try {

            bookRef = bookRepository.findByNameAndAuthor(book.getName(), book.getAuthor(), book.getYear());

            if (Objects.nonNull(bookRef)) {
                logger.warn("[createBook()] this entity has already registered");
                response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                response.getBody().setResult(ReadingUtil.buildResult(false, ReadingUtil.ERROR_CODE_FAIL_DUPLICATE_ENTITY));
                return response;
            }

            book.setId(UUID.randomUUID().toString());
            book = bookRepository.save(book);

        } catch (Exception e) {
            logger.error("[createBook()] error occured while creatBook operation. Exception = {} ", e);
            response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
            response.getBody().setResult(ReadingUtil.buildGeneralFailResult());
            return response;
        }

        response = new ResponseEntity<>(resp, HttpStatus.OK);
        response.getBody().setResult(ReadingUtil.buildGeneralSuccessResult());
        response.getBody().setResultObj(book);

        return response;
    }

    @Override
    public Book findByNameAndAuthor(String bookName, String authorName, int year) {

        return bookRepository.findByNameAndAuthor(bookName, authorName, year);
    }

    @Override
    public ResponseEntity<Response<Book>> updateStock(BookStockUpdateRequest bookStockUpdateRequest) {

        Response<Book> resp = new Response<>();
        ResponseEntity<Response<Book>> response;

        Book book;

        try {
            book = bookRepository.findById(bookStockUpdateRequest.getBookId());

            if (Objects.isNull(book)) {
                logger.warn("[updatePage()] no matching book found with id", bookStockUpdateRequest.getBookId());
                response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                response.getBody().setResult(ReadingUtil.buildResult(false, ReadingUtil.ERROR_CODE_FAIL_NO_MATCH));
                return response;
            }

            if (bookStockUpdateRequest.getStock() < 0) {
                logger.warn("Can't assign negative integer for stock number");
                response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
                response.getBody().setResult(ReadingUtil.buildResult(false, ReadingUtil.ERROR_CODE_FAIL_NO_NEGATIVE_INTEGER_ALLOWED));
                return response;
            }

            book.setStock(bookStockUpdateRequest.getStock());

            logger.info("[updatePage()] updated book stock. new stock size = {} ", bookStockUpdateRequest.getStock());

            book = bookRepository.save(book);

        } catch (Exception e) {
            logger.error("[createBook()] error occured while updateStock operation. Exception = {} ", e);
            response = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
            response.getBody().setResult(ReadingUtil.buildGeneralFailResult());
            return response;
        }
        response = new ResponseEntity<>(resp, HttpStatus.OK);
        response.getBody().setResult(ReadingUtil.buildGeneralSuccessResult());
        response.getBody().setResultObj(book);

        return response;
    }
}

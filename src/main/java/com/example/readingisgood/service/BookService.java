package com.example.readingisgood.service;

import com.example.readingisgood.entity.Book;
import com.example.readingisgood.model.BookStockUpdateRequest;
import com.example.readingisgood.model.Response;
import org.springframework.http.ResponseEntity;

public interface BookService {

    ResponseEntity<Response<Book>> createBook(Book book);

    Book findByNameAndAuthor(String bookName, String authorName, int year);

    ResponseEntity<Response<Book>> updateStock(BookStockUpdateRequest bookStockUpdateRequest);

}

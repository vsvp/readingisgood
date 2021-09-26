package com.example.readingisgood;

import com.example.readingisgood.entity.Book;
import com.example.readingisgood.model.BookStockUpdateRequest;
import com.example.readingisgood.model.Response;
import com.example.readingisgood.service.BookService;
import com.example.readingisgood.util.ReadingUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @Test
    public void shouldCreateBookSuccessFully(){
        Response<Book> resp = new Response<>();

        Book book = new Book();
        book.setId(UUID.randomUUID().toString());
        book.setAuthor("dostoyevski");
        book.setName("sefiller");
        book.setPrice(23);
        book.setYear(1862);

        resp.setResultObj(book);
        resp.setResult(ReadingUtil.buildGeneralSuccessResult());

        ResponseEntity<Response<Book>> result = new ResponseEntity<>(resp, HttpStatus.OK);

        when(bookService.createBook(book)).thenReturn(result);

        final ResponseEntity<Response<Book>> actualResult = bookService.createBook(book);
        assertEquals(actualResult, result);
    }

    @Test
    public void shouldFailIfBookAlreadyExists(){
        Response<Book> resp = new Response<>();

        Book book = new Book();
        book.setId(UUID.randomUUID().toString());
        book.setAuthor("dostoyevski");
        book.setName("sefiller");
        book.setPrice(23);
        book.setYear(1862);

        resp.setResultObj(book);
        resp.setResult(ReadingUtil.buildResult(false, ReadingUtil.ERROR_CODE_FAIL_DUPLICATE_ENTITY));

        ResponseEntity<Response<Book>> result = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);

        when(bookService.createBook(book)).thenReturn(result);

        final ResponseEntity<Response<Book>> actualResult = bookService.createBook(book);
        assertEquals(actualResult, result);
    }

    @Test
    public void shouldUpdateBookStockSize(){
        Response<Book> resp = new Response<>();

        BookStockUpdateRequest bookStockUpdateRequest = new BookStockUpdateRequest();
        bookStockUpdateRequest.setBookId(UUID.randomUUID().toString());
        bookStockUpdateRequest.setStock(50);

        resp.setResult(ReadingUtil.buildResult(false, ReadingUtil.ERROR_CODE_FAIL_DUPLICATE_ENTITY));

        ResponseEntity<Response<Book>> result = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);

        when(bookService.updateStock(bookStockUpdateRequest)).thenReturn(result);

        final ResponseEntity<Response<Book>> actualResult = bookService.updateStock(bookStockUpdateRequest);
        assertEquals(actualResult, result);
    }

    @Test
    public void shouldFUpdateBookStockSize(){
        Response<Book> resp = new Response<>();

        BookStockUpdateRequest bookStockUpdateRequest = new BookStockUpdateRequest();
        bookStockUpdateRequest.setBookId(UUID.randomUUID().toString());
        bookStockUpdateRequest.setStock(-5);

        resp.setResult(ReadingUtil.buildResult(false, ReadingUtil.ERROR_CODE_FAIL_NO_NEGATIVE_INTEGER_ALLOWED));

        ResponseEntity<Response<Book>> result = new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);

        when(bookService.updateStock(bookStockUpdateRequest)).thenReturn(result);

        final ResponseEntity<Response<Book>> actualResult = bookService.updateStock(bookStockUpdateRequest);
        assertEquals(actualResult, result);
    }
}

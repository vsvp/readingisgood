package com.example.readingisgood.model;

public class BookStockUpdateRequest {

    private String bookId;

    private int stock;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

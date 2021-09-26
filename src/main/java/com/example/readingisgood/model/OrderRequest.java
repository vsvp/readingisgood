package com.example.readingisgood.model;

public class OrderRequest {

    private String customerId;

    private String bookId;

    private int bookOrderSize;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getBookOrderSize() {
        return bookOrderSize;
    }

    public void setBookOrderSize(int bookOrderSize) {
        this.bookOrderSize = bookOrderSize;
    }
}

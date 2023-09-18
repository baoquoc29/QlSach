package com.example.qlsach;

public class Books {
    private int bookId;
    private String Bookname;
    private int page;
    private float price;
    private String description;

    public Books(int bookId, String bookname, int page, float price, String description) {
        this.bookId = bookId;
        Bookname = bookname;
        this.page = page;
        this.price = price;
        this.description = description;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookname() {
        return Bookname;
    }

    public void setBookname(String bookname) {
        Bookname = bookname;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
       return bookId + "/" + Bookname + "/" + page + "/" + price + "/" + description;
    }
}

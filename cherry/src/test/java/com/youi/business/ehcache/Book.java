package com.youi.business.ehcache;

import java.io.Serializable;

/**
 * Created by GWCheng on 2016/3/3.
 */
public class Book implements Serializable{
    private String bookISBN;
    private String bookName;

    public Book(String bookISBN, String bookName) {
        this.bookISBN = bookISBN;
        this.bookName = bookName;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

}
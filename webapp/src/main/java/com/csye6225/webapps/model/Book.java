package com.csye6225.webapps.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookID;

    private String ISBN;

    @Lob
    private String title;

    @Lob
    private String authors;

    private String publicationDate;

    private Integer quantity;

    private Double price;

    private Date bookAddedTime;

    private Date bookUpdateTime;

    @ManyToOne
    @JoinColumn
    private User user;

    public long getBookID() {
        return bookID;
    }

    public void setBookID(long bookID) {
        this.bookID = bookID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getBookAddedTime() {
        return bookAddedTime;
    }

    public void setBookAddedTime(Date bookAddedTime) {
        this.bookAddedTime = bookAddedTime;
    }

    public Date getBookUpdateTime() {
        return bookUpdateTime;
    }

    public void setBookUpdateTime(Date bookUpdateTime) {
        this.bookUpdateTime = bookUpdateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
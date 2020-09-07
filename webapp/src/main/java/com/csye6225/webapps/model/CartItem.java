package com.csye6225.webapps.model;

import javax.persistence.*;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartItemID;

    private Integer quantityAdd;

    @ManyToOne
    @JoinColumn
    private Book book;

    @ManyToOne
    @JoinColumn
    private ShoppingCart shoppinhCart;

    public Long getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(Long cartItemID) {
        this.cartItemID = cartItemID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getQuantityAdd() {
        return quantityAdd;
    }

    public void setQuantityAdd(Integer quantityAdd) {
        this.quantityAdd = quantityAdd;
    }

    public ShoppingCart getShoppinhCart() {
        return shoppinhCart;
    }

    public void setShoppinhCart(ShoppingCart shoppinhCart) {
        this.shoppinhCart = shoppinhCart;
    }
}

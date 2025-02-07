package com.hust.seller.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Cart_Items")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Cart_itemID")
    private int cartItemID;
    @Column(name="CartID")
    private int cartID;
    @Column(name="ProductID")
    private int productID;
    @Column(name="QuantityID")
    private int quantity;

    public CartItems(int cartID, int productID, int quantity) {
        this.cartID = cartID;
        this.productID = productID;
        this.quantity = quantity;
    }
    public CartItems(){}

    public int getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(int cartItemID) {
        this.cartItemID = cartItemID;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantityID) {
        this.quantity = quantityID;
    }
}

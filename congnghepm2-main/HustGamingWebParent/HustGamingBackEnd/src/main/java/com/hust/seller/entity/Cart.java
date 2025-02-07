package com.hust.seller.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CartID")
    private int cartID;
    @Column(name="CustomerID")
    private int customerID;
    @Column(name="Created_at")
    private LocalDateTime created_at;
    public Cart(){};
    public Cart(int customerID, LocalDateTime created_at) {
        this.customerID = customerID;
        this.created_at = created_at;
    }
    public int getCartID() {
        return cartID;
    }
    public void setCartID(int cartID) {
        this.cartID = cartID;
    }
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}

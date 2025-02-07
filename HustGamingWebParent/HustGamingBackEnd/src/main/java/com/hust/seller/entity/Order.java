package com.hust.seller.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="OrderID")
    private int orderID;
    @Column(name="BuyerID")
    private int buyerID;
    @Column(name="Order_date")
    private LocalDateTime orderDate;
    @Column(name="Total_amount")
    private int totalAmount;
    @Column(name="Status")
    private String status;
    @Column(name="Shipping_address")
    private String shippingAddress;
    @Column(name="Payment")
    private String payment;

    public Order(int buyerID, LocalDateTime orderDate, int totalAmount, String status,String payment) {
        this.buyerID = buyerID;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.payment=payment;
    }
    public Order(){}
    public int getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(int buyerID) {
        this.buyerID = buyerID;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}

package com.hust.seller.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    private int orderID;
    @Column(name = "BuyerID")
    private int buyerID;
    @Column(name = "Order_date")
    private LocalDateTime orderDate;
    @Column(name = "Total_amount")
    private int totalAmount;
    @Column(name = "Status")
    private int status;
    @Column(name = "Shipping_address")
    private String shippingAddress;
    @Column(name = "Payment")
    private String payment;
    @Column(name = "ShopID")
    private int shopID;
    @Column(name = "ProductID")
    private int productID;
    @Column(name = "Quantity")
    private int quantity;
    @Column(name = "Price")
    private int price;
    public Order(){};


    public Order(int orderID, int buyerID, LocalDateTime orderDate, int totalAmount, int status, String shippingAddress, String payment, int shopID, int productID, int quantity, int price) {
        this.orderID = orderID;
        this.buyerID = buyerID;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.payment = payment;
        this.shopID = shopID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
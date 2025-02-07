package com.hust.seller.entity;

import jakarta.persistence.*;

@Entity

@Table(name="Order_Details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="Order_detailID")
    private int orderDetailID;
    @Column(name="OrderID")
    private int orderID;
    @Column(name = "ProductID")
    private int productID;
    @Column(name="Quantity")
    private int quantity;
    @Column(name = "Price")
    private float price;
    @Column(name="Totalprice")
     private float totalPrice;

    public OrderDetail(int orderID, int productID, int quantity, float price, float totalPrice) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}

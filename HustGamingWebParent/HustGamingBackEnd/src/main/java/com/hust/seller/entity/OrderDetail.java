package com.hust.seller.entity;

import jakarta.persistence.*;

@Entity

@Table(name="OrderDetails")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="OrderDetailID")
    private int orderDetailID;
    @Column(name="OrderID")
    private int orderID;

    @Column(name="Totalprice")
     private float totalPrice;
    @Column (name="Status")
    private String status;
    @Column(name="ShopID")
    private int shopID;

    public OrderDetail(int orderID, float totalPrice, String status, int shopID) {
        this.orderID = orderID;
        this.totalPrice = totalPrice;
        this.status = status;
        this.shopID = shopID;
    }
    public OrderDetail(){};

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

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }
}

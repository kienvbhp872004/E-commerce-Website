package com.hust.seller.entity;

import jakarta.persistence.*;

@Entity
@Table(name="OrderDetailsShop")
public class OrderDetailShop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="OrderdetailshopID")
    private int orderDetailShopID;
    @Column(name="OrderdetailID")
    private int orderDetailID;
    @Column(name="ShopID")
    private int shopID;
    @Column(name = "ProductID")
    private int productID;
    @Column(name = "Price")
    private float price;
    @Column(name="Quantity")
    private int quantity;

    public OrderDetailShop(int orderDetailID, int shopID, int productID, float price, int quantity) {
        this.orderDetailID = orderDetailID;
        this.shopID = shopID;
        this.productID = productID;
        this.price = price;
        this.quantity = quantity;
    }

    public int getOrderDetailShopID() {
        return orderDetailShopID;
    }

    public void setOrderDetailShopID(int orderDetailShopID) {
        this.orderDetailShopID = orderDetailShopID;
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

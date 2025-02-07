package com.hust.seller.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="ProductID")
    private int productID;
    @Column(name="Product_name")
    private String productName;
    @Column(name="Description")
    private String description;
    @Column(name="Price")
    private int price;
    @Column(name="Quantity")
    private int quantity;
    @Column(name="CategoryID")
    private int categoryID;
    @Column(name="ShopID")
    private int shopID;
    @Column(name="Created_date")
    private LocalDateTime createdDate;
    @Column(name="Status")
    private boolean status;
    @Column(name="Image")
    private String image;


    public Product(String productName, String description, int price, int quantity, int categoryID, int shopID, LocalDateTime createdDate, boolean status) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.categoryID = categoryID;
        this.shopID = shopID;
        this.createdDate = createdDate;
        this.status = status;
    }
    public Product(){}

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

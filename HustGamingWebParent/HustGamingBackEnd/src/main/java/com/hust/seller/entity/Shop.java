package com.hust.seller.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ShopID")
    private int shopID;
    @Column(name="Name")
    private String name;
    @Column(name="SellerID")
    private int sellerID;
    @Column(name="Address")
    private String address;
    @Column(name = "Status")
    private boolean status;

    public Shop(String name, int sellerID, String address) {
        this.name = name;
        this.sellerID = sellerID;
        this.address = address;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}

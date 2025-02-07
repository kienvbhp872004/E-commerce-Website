package com.hust.seller.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Image_Product")
public class ImageProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Image_productID")
    private int imageProductID;
    @Column(name = "ProductID")
    private int productID;
    @Column(name = "Image")
    private String image;
    public ImageProduct(){}

    public int getImageProductID() {
        return imageProductID;
    }

    public void setImageProductID(int imageProductID) {
        this.imageProductID = imageProductID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

package com.hust.seller.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Categories")

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CategoryID")
    private int categoryId;
    @Column(name="Category_name")
    private String categoryName;
    @Column(name = "Image")
    private String image;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
    public Category(){}

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

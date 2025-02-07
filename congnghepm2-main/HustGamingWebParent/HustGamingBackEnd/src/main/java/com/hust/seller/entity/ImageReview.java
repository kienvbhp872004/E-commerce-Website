package com.hust.seller.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Image_Review")
public class ImageReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Image_reviewID")
    private int imageReviewID;
    @Column(name="ReviewID")
    private int reviewID;
    @Column(name="Image")
    private String image;
    public ImageReview(){}

    public int getImageReviewID() {
        return imageReviewID;
    }

    public void setImageReviewID(int imageReviewID) {
        this.imageReviewID = imageReviewID;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

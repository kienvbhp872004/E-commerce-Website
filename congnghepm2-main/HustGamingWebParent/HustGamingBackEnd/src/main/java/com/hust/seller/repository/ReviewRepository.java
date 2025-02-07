package com.hust.seller.repository;

import com.hust.seller.entity.Product;
import com.hust.seller.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductID(int productID);
}

package com.hust.seller.repository;

import com.hust.seller.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findByProductID(int productID) ;
}

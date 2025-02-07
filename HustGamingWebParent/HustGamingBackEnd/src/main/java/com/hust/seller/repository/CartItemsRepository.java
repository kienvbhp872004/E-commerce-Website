package com.hust.seller.repository;

import com.hust.seller.entity.CartItems;
import com.hust.seller.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {
    List<CartItems> findAllByCartID(int cartID) ;

    Product findCartItemsByProductID(int productID) ;
}

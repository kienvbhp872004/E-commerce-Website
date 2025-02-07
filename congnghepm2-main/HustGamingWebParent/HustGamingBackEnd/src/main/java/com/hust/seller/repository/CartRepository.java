package com.hust.seller.repository;

import com.hust.seller.entity.Cart;
import com.hust.seller.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository  extends JpaRepository<Cart, Integer> {
    Cart findByCustomerID(int customerID);


}

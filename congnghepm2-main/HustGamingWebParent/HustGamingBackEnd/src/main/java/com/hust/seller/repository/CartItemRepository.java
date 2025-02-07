package com.hust.seller.repository;

import com.hust.seller.entity.Cart;
import com.hust.seller.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface CartItemRepository  extends JpaRepository<CartItems, Integer> {
    List<CartItems> findByCartID(int cartID);
    Optional<CartItems> findByProductID(int productID);
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItems c WHERE c.cartID = :cartID")
    void deleteByCartID(@Param("cartID") int cartID);
}

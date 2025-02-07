package com.hust.seller.repository;

import com.hust.seller.entity.Product;
import com.hust.seller.entity.Shop;
import com.hust.seller.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    Optional<Shop> findBySellerID(int sellerID);

Optional<Shop> findByShopID(int shopID);
    @Query("SELECT p FROM Product p WHERE p.shopID = :id")
    List<Product> findAllProductByShopId(@Param("id") int id);
    @Query("SELECT s FROM Shop s WHERE s.shopID = :id")
    Shop findShopByShopId(@Param("id") int id);
    @Query("SELECT u.image FROM User u,Shop s WHERE s.sellerID = u.userID AND s.shopID = :id" )
    String findImageByShopId(@Param("id") int id) ;

}

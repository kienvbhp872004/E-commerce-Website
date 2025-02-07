package com.hust.seller.repository;

import com.hust.seller.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByShopID(int shopID);

    Product findByProductID(int productID);
    List<Product> findByCategoryID(int categoryID);

    @Query("SELECT p FROM Product p " +
            "WHERE p.productName LIKE %:keyword% " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "ORDER BY " +
            "CASE WHEN :sortField = 'popularity' THEN p.productName END ASC, " +
            "CASE WHEN :sortField = 'priceASC' THEN p.price END ASC, " +
            "CASE WHEN :sortField = 'priceDESC' THEN p.price END DESC, " +
            "CASE WHEN :sortField = 'date' THEN p.createdDate END ASC")
    List<Product> searchAndSortProducts(
            String keyword,
            String sortField,
            BigDecimal minPrice,
            BigDecimal maxPrice
    );
}

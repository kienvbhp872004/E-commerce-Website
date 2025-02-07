package com.hust.seller.repository;

import com.hust.seller.entity.ImageProduct;
import com.hust.seller.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Integer> {
List<Order> findByBuyerID(int buyerID);
Order findByOrderID(int orderID);
List<Order> findByShopID(int shopID);
}

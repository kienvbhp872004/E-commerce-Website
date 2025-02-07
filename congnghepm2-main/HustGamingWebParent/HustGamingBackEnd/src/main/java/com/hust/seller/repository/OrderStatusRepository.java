package com.hust.seller.repository;

import com.hust.seller.entity.Order;
import com.hust.seller.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository  extends JpaRepository<OrderStatus, Integer> {
OrderStatus findByStatusID(int id);
}

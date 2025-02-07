package com.hust.seller.order;

import com.hust.seller.entity.Order;

public class OrderDTO {
    public Order order;
    public String Status_name;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getStatus_name() {
        return Status_name;
    }

    public void setStatus_name(String status_name) {
        Status_name = status_name;
    }
}

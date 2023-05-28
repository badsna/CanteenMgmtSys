package com.example.canteenmgmtsys.order.repo;

import com.example.canteenmgmtsys.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByCustomer_CustomerId(String customerId);
}

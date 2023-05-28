package com.example.canteenmgmtsys.order.model;

import com.example.canteenmgmtsys.customer.model.Customer;
import com.example.canteenmgmtsys.order_foodItems.model.OrderedFoodItems;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private double total;
    @CreationTimestamp
    private LocalDateTime orderDate;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<OrderedFoodItems> orderFoodItems;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}

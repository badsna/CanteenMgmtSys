package com.example.canteenmgmtsys.order_foodItems.model;

import com.example.canteenmgmtsys.category.model.Category;
import com.example.canteenmgmtsys.foodItems.model.FoodItems;
import com.example.canteenmgmtsys.order.model.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "order_food_items")
public class OrderedFoodItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "food_item_id")
    private FoodItems foodItem;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    private int quantity;

}

package com.example.canteenmgmtsys.order.utils;

import com.example.canteenmgmtsys.foodItems.model.FoodItems;
import org.springframework.stereotype.Component;

@Component
public class TotalPriceOfOrderList {
    public double calculateTotalPrice(FoodItems foodItems, int quantity) {
        double sum = 0.0;
        sum = sum + foodItems.getPrice() * quantity;
        return sum;
    }
}

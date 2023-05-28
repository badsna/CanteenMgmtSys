package com.example.canteenmgmtsys.order_foodItems.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodItemOrderedResponseDto {
    private String foodItemName;
    private int quantity;
    private double pricePerQuantity;
    private String categoryName;

}

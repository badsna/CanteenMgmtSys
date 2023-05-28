package com.example.canteenmgmtsys.order_foodItems.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodItemOrderedRequestDto {
    private String categoryName;
    private String foodItemName;
    private int quantity;

}

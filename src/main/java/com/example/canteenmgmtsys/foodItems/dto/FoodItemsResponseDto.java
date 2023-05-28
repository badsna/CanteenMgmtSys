package com.example.canteenmgmtsys.foodItems.dto;

import com.example.canteenmgmtsys.foodItems.enums.FoodItemsStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter

public class FoodItemsResponseDto implements Serializable {
    private String foodItemName;
    private double price;
    private FoodItemsStatus status;
    private int stock;

}

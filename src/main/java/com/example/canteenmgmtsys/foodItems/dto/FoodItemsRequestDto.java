package com.example.canteenmgmtsys.foodItems.dto;

import com.example.canteenmgmtsys.category.model.Category;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class FoodItemsRequestDto implements Serializable {
    private String foodItemName;
    private double price;
    private Category category;
    private int stock;


}

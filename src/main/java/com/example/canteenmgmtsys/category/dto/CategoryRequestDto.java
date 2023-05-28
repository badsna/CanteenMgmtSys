package com.example.canteenmgmtsys.category.dto;

import com.example.canteenmgmtsys.foodItems.model.FoodItems;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter

public class CategoryRequestDto implements Serializable {
    private String categoryName;
    private List<FoodItems> foodItems;
}

package com.example.canteenmgmtsys.foodItems.service;

import com.example.canteenmgmtsys.foodItems.dto.FoodItemsRequestDto;
import com.example.canteenmgmtsys.foodItems.dto.FoodItemsResponseDto;

import java.util.List;

public interface FoodItemsService {
    void addNewFoodItems(FoodItemsRequestDto foodItemsRequestDto);

    List<FoodItemsResponseDto> getAllFoodItemsById(String categoryName);
}

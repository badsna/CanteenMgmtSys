package com.example.canteenmgmtsys.foodItems.controller;

import com.example.canteenmgmtsys.foodItems.dto.FoodItemsRequestDto;
import com.example.canteenmgmtsys.foodItems.dto.FoodItemsResponseDto;
import com.example.canteenmgmtsys.foodItems.service.FoodItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/food_items")
public class FoodItemsController {
    private final FoodItemsService foodItemsService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add_new_food_items")
    public String addNewFoodItems(@RequestBody FoodItemsRequestDto foodItemsRequestDto) {

        foodItemsService.addNewFoodItems(foodItemsRequestDto);

        return "New Food Items Added Successfully";
    }

    @GetMapping("/get_all_food_items_by_category_name/{categoryName}")
    public List<FoodItemsResponseDto> getAllFoodItemsById(@PathVariable("categoryName") String categoryName) {
        return foodItemsService.getAllFoodItemsById(categoryName);
    }
}

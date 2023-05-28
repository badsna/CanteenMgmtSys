package com.example.canteenmgmtsys.foodItems.repo;

import com.example.canteenmgmtsys.foodItems.model.FoodItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodItemsRepo extends JpaRepository<FoodItems, Long> {

    @Query(value = "SELECT * FROM food_items  WHERE food_item_name = ?1 AND category_id = ?2",nativeQuery = true)
    FoodItems findByFoodItemNameAndCategoryId(String foodItemName, Long categoryId);
    @Query(value = "select * from food_items where category_id=?1", nativeQuery = true)
    List<FoodItems> findByCategoryId(Long categoryId);
}

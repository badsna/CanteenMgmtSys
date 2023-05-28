package com.example.canteenmgmtsys.category.service;

import com.example.canteenmgmtsys.category.dto.CategoryRequestDto;
import com.example.canteenmgmtsys.category.dto.CategoryResponseDto;
import com.example.canteenmgmtsys.category.model.Category;
import com.example.canteenmgmtsys.category.repo.CategoryRepo;
import com.example.canteenmgmtsys.exception.customexception.ResourceAlreadyExistsException;
import com.example.canteenmgmtsys.foodItems.enums.FoodItemsStatus;
import com.example.canteenmgmtsys.foodItems.model.FoodItems;
import com.example.canteenmgmtsys.foodItems.repo.FoodItemsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CategoryServiceImpl implements CategoryService {
    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepo categoryRepo;
    private final ObjectMapper objectMapper;
    private final FoodItemsRepo foodItemsRepo;

    @Override
    public void addNewCategory(CategoryRequestDto categoryRequestDto) {

        logger.info("Converting categoryRequestDto in Category.class using  objectMapper");
        Category category = objectMapper.convertValue(categoryRequestDto, Category.class);

        category.setCategoryName(categoryRequestDto.getCategoryName().toLowerCase());

        logger.info("Getting only list of foodItemsList from categoryRequestDto");
        List<FoodItems> foodItemsList = categoryRequestDto.getFoodItems();
        String categoryName = categoryRequestDto.getCategoryName().toLowerCase();

        logger.info("Getting existingCategory using categoryName");
        Optional<Category> existingCategory = Optional.ofNullable(categoryRepo.findByCategoryName(categoryName));

        List<String> foodItemsName = new ArrayList<>();

        if (existingCategory.isPresent()) {
            category = existingCategory.get();

            Long categoryId = existingCategory.get().getCategoryId();

            logger.info("Getting list of foodItemsList by categoryId");
            List<FoodItems> foodItems = foodItemsRepo.findByCategoryId(categoryId);

            for (FoodItems foodItem : foodItems) {
                logger.info("Adding into foodItemsName");
                foodItemsName.add(foodItem.getFoodItemName());
            }

        }

        for (FoodItems foodItem : foodItemsList) {

            if (foodItemsName.contains(foodItem.getFoodItemName())) {
                logger.error("FoodItemsName already exists");
                throw new ResourceAlreadyExistsException(foodItem.getFoodItemName() + " already exists");
            } else {
                if(foodItem.getStock()<2){
                    foodItem.setStatus(FoodItemsStatus.CURRENTLY_UNAVAILABLE);
                }
                else{
                    logger.info("Setting foodItemsStatus as available");
                    foodItem.setStatus(FoodItemsStatus.AVAILABLE);
                }

                logger.info("Setting category to set value of category_id in food_Items");
                foodItem.setCategory(category);
            }
        }

        logger.info("Setting foodItemsList in category");
        category.setFoodItems(foodItemsList);

        logger.info("Adding new category");
        categoryRepo.save(category);

    }
    public List<CategoryResponseDto> getAllCategory() {

        logger.info("Getting all the category list from database table");
        List<Category> categoryList = categoryRepo.findAll();

        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();

        for (Category category : categoryList) {
            CategoryResponseDto categoryResponseDto = new CategoryResponseDto();

            categoryResponseDto.setCategoryName(category.getCategoryName());
            categoryResponseDtoList.add(categoryResponseDto);
        }

        logger.info("Returning list of category");
        return categoryResponseDtoList;
    }

   /* @Override
    public List<CategoryResponseDto> getAllCategory() {

        return categoryRepo.findAll()
                .stream()
                .map(CategoryResponseDto::of)
                .collect(Collectors.toList());
    }*/
}

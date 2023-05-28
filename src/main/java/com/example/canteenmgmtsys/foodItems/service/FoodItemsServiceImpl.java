package com.example.canteenmgmtsys.foodItems.service;

import com.example.canteenmgmtsys.category.model.Category;
import com.example.canteenmgmtsys.category.repo.CategoryRepo;
import com.example.canteenmgmtsys.exception.customexception.ResourceAlreadyExistsException;
import com.example.canteenmgmtsys.exception.customexception.ResourceNotFoundException;
import com.example.canteenmgmtsys.foodItems.dto.FoodItemsRequestDto;
import com.example.canteenmgmtsys.foodItems.dto.FoodItemsResponseDto;
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
public class FoodItemsServiceImpl implements FoodItemsService {
    Logger logger = LoggerFactory.getLogger(FoodItemsServiceImpl.class);
    private final FoodItemsRepo foodItemsRepo;
    private final ObjectMapper objectMapper;
    private final CategoryRepo categoryRepo;

    @Override
    public void addNewFoodItems(FoodItemsRequestDto foodItemsRequestDto) {

        logger.info("Converting foodItemsRequestDto in FoodItems.class");
        FoodItems foodItems = objectMapper.convertValue(foodItemsRequestDto, FoodItems.class);

        String categoryName = foodItemsRequestDto.getCategory().getCategoryName().toLowerCase();

        logger.info("Getting existingCategory with categoryName ");
        Optional<Category> existingCategory = Optional.ofNullable(categoryRepo.findByCategoryName(categoryName));

        if (existingCategory.isPresent()) {

            logger.info("Setting category_id as existingCategory if categoryName already exists ");
            foodItems.setCategory(existingCategory.get());

            Long categoryId = existingCategory.get().getCategoryId();

            logger.info("Getting list of foodItemsList by categoryId");
            List<FoodItems> foodItemsList = foodItemsRepo.findByCategoryId(categoryId);

            List<String> foodItemsName = new ArrayList<>();

            for (FoodItems foodItem : foodItemsList) {
                logger.info("Adding into foodItemsName");
                foodItemsName.add(foodItem.getFoodItemName());
            }

            if (foodItemsName.contains(foodItemsRequestDto.getFoodItemName())) {
                logger.error("Check if food item already exists");
                throw new ResourceAlreadyExistsException(foodItemsRequestDto.getFoodItemName() + " already exists");
            }
        } else {
            logger.info("Setting foodItem's category if categoryName doesn't exists");
            foodItems.setCategory(foodItems.getCategory());
        }

        if (foodItems.getStock() == 0) {
            logger.info("Setting foodItemsStatus to currently_unavailable if stock is 0");
            foodItems.setStatus(FoodItemsStatus.CURRENTLY_UNAVAILABLE);
        } else {
            logger.info("Setting foodItemsStatus as available");
            foodItems.setStatus(FoodItemsStatus.AVAILABLE);
        }

        logger.info("Adding new foodItems");
        foodItemsRepo.save(foodItems);
    }

    @Override
    public List<FoodItemsResponseDto> getAllFoodItemsById(String categoryName) {

        logger.info("Getting Category by categoryName");
        Optional<Category> category = Optional.ofNullable(categoryRepo.findByCategoryName(categoryName.toLowerCase()));

        if (!category.isPresent()) {
            logger.error("Category doesn't exists");
            throw new ResourceNotFoundException("Category with categoryName " + categoryName + " doesn't exists");
        }

        Long categoryId = category.get().getCategoryId();
        logger.info("Getting list of foodItemsList by categoryId");

        List<FoodItems> foodItemsList = foodItemsRepo.findByCategoryId(categoryId);

        List<FoodItemsResponseDto> foodItemsResponseDtoList = new ArrayList<>();

        for (FoodItems foodItems : foodItemsList) {

            FoodItemsResponseDto foodItemsResponseDto = new FoodItemsResponseDto();

            foodItemsResponseDto.setFoodItemName(foodItems.getFoodItemName());
            foodItemsResponseDto.setPrice(foodItems.getPrice());
            foodItemsResponseDto.setStatus(foodItems.getStatus());
            foodItemsResponseDto.setStock(foodItems.getStock());

            logger.info("Adding foodItemsResponseDto in foodItemsResponseDtoList ");
            foodItemsResponseDtoList.add(foodItemsResponseDto);
        }

        logger.info("Returning  list of foodItemsList from database");
        return foodItemsResponseDtoList;
    }
}

package com.example.canteenmgmtsys.order.dto;

import com.example.canteenmgmtsys.order_foodItems.dto.FoodItemOrderedResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderByCustomerIdResponseDto implements Serializable {
    private double total;
    private LocalDateTime orderDate;
    private List<FoodItemOrderedResponseDto> foodItemsList;
}

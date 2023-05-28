package com.example.canteenmgmtsys.order.dto;

import com.example.canteenmgmtsys.order_foodItems.dto.FoodItemOrderedRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OrderRequestDto implements Serializable {
    private String customerId;
    private List<FoodItemOrderedRequestDto> foodItems;
}

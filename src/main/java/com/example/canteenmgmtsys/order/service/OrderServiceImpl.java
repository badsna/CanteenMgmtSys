package com.example.canteenmgmtsys.order.service;

import com.example.canteenmgmtsys.category.model.Category;
import com.example.canteenmgmtsys.category.repo.CategoryRepo;
import com.example.canteenmgmtsys.customer.model.Customer;
import com.example.canteenmgmtsys.customer.repo.CustomerRepo;
import com.example.canteenmgmtsys.exception.customexception.InsufficientResourcesException;
import com.example.canteenmgmtsys.exception.customexception.ResourceNotFoundException;
import com.example.canteenmgmtsys.foodItems.enums.FoodItemsStatus;
import com.example.canteenmgmtsys.foodItems.model.FoodItems;
import com.example.canteenmgmtsys.foodItems.repo.FoodItemsRepo;
import com.example.canteenmgmtsys.order.dto.OrderByCustomerIdResponseDto;
import com.example.canteenmgmtsys.order.dto.OrderRequestDto;
import com.example.canteenmgmtsys.order.dto.OrderResponseDto;
import com.example.canteenmgmtsys.order.model.Order;
import com.example.canteenmgmtsys.order.repo.OrderRepo;
import com.example.canteenmgmtsys.order.utils.TotalPriceOfOrderList;
import com.example.canteenmgmtsys.order_foodItems.dto.FoodItemOrderedRequestDto;
import com.example.canteenmgmtsys.order_foodItems.dto.FoodItemOrderedResponseDto;
import com.example.canteenmgmtsys.order_foodItems.model.OrderedFoodItems;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final FoodItemsRepo foodItemsRepo;
    private final TotalPriceOfOrderList totalPriceOfOrderList;
    private final CategoryRepo categoryRepo;
    private final CustomerRepo customerRepo;
    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public OrderResponseDto getOrderInfoById(Long orderId) {

        logger.info("Getting order Information with orderId");
        Optional<Order> order = orderRepo.findById(orderId);

        if (!order.isPresent()) {
            logger.error("Order doesn't exists");
            throw new ResourceNotFoundException("Order with orderId " + orderId + " doesn't exists");
        }

        logger.info("Setting value of Order in OrderResponseDto ");
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderDate(order.get().getOrderDate());
        orderResponseDto.setTotal(order.get().getTotal());
        orderResponseDto.setCustomerId(order.get().getCustomer().getCustomerId());

        logger.info("Getting FoodItem List from Order");
        List<OrderedFoodItems> orderedFoodItemsList = order.get().getOrderFoodItems();

        List<FoodItemOrderedResponseDto> foodItemOrderedResponseDtoList = new ArrayList<>();

        for (OrderedFoodItems orderedFoodItems : orderedFoodItemsList) {

            FoodItemOrderedResponseDto foodItemOrderedResponseDto = new FoodItemOrderedResponseDto();

            logger.info("Setting value of OrderedFoodItemsList in FoodItemOrderedResponseDto");
            foodItemOrderedResponseDto.setFoodItemName(orderedFoodItems.getFoodItem().getFoodItemName());
            foodItemOrderedResponseDto.setQuantity(orderedFoodItems.getQuantity());
            foodItemOrderedResponseDto.setCategoryName(orderedFoodItems.getCategory().getCategoryName());
            foodItemOrderedResponseDto.setPricePerQuantity(orderedFoodItems.getFoodItem().getPrice());

            logger.info("Adding foodItemOrderedResponseDto in foodItemOrderedResponseDtoList");
            foodItemOrderedResponseDtoList.add(foodItemOrderedResponseDto);
        }

        logger.info("Setting foodItemOrderedResponseDtoList in orderResponseDto ");
        orderResponseDto.setFoodItemsList(foodItemOrderedResponseDtoList);

        logger.info("Returning orderResponseDto");
        return orderResponseDto;
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        List<Order> orderList = orderRepo.findAll();
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for (Order order : orderList) {
            OrderResponseDto orderResponseDto = new OrderResponseDto();

            orderResponseDto.setOrderDate(order.getOrderDate());
            orderResponseDto.setTotal(order.getTotal());
            orderResponseDto.setCustomerId(order.getCustomer().getCustomerId());

            List<OrderedFoodItems> foodItemsList = order.getOrderFoodItems();

            List<FoodItemOrderedResponseDto> foodItemOrderedResponseDtoList = new ArrayList<>();

            for (OrderedFoodItems orderedFoodItems : foodItemsList) {

                FoodItemOrderedResponseDto foodItemOrderedResponseDto = new FoodItemOrderedResponseDto();
                foodItemOrderedResponseDto.setFoodItemName(orderedFoodItems.getFoodItem().getFoodItemName());
                foodItemOrderedResponseDto.setQuantity(orderedFoodItems.getQuantity());
                foodItemOrderedResponseDto.setPricePerQuantity(orderedFoodItems.getFoodItem().getPrice());
                foodItemOrderedResponseDto.setCategoryName(orderedFoodItems.getCategory().getCategoryName());
                foodItemOrderedResponseDtoList.add(foodItemOrderedResponseDto);

            }
            orderResponseDto.setFoodItemsList(foodItemOrderedResponseDtoList);
            orderResponseDtoList.add(orderResponseDto);

        }
        return orderResponseDtoList;
    }


    @Override
    public List<OrderByCustomerIdResponseDto> getOrderInfoByCustomerId(String customerId) {
        logger.info("Getting order list by customerId");
      List<Order> orderList=orderRepo.findByCustomer_CustomerId(customerId);

      if(orderList.isEmpty()){
          logger.info("Customer hasn't placed order yet");
          throw new ResourceNotFoundException("Customer "+customerId+" hasn't ordered anything till now");
      }

      logger.info("Creating ArrayList for response");
      List<OrderByCustomerIdResponseDto> orderByCustomerIdResponseDtoList=new ArrayList<>();
        for (Order order:orderList) {

            OrderByCustomerIdResponseDto orderByCustomerIdResponseDto=new OrderByCustomerIdResponseDto();

            logger.info("Setting value of order to orderByCustomerIdResponseDto");
            orderByCustomerIdResponseDto.setOrderDate(order.getOrderDate());
            orderByCustomerIdResponseDto.setTotal(order.getTotal());

            logger.info("Getting orderFoodItems from order");
            List<OrderedFoodItems> orderedFoodItemsList = order.getOrderFoodItems();

            List<FoodItemOrderedResponseDto> foodItemOrderedResponseDtoList=new ArrayList<>();

            for (OrderedFoodItems orderedFoodItems:orderedFoodItemsList) {

                logger.info("Setting value of order to foodItemOrderedResponseDto");
                FoodItemOrderedResponseDto foodItemOrderedResponseDto=new FoodItemOrderedResponseDto();

                foodItemOrderedResponseDto.setCategoryName(orderedFoodItems.getCategory().getCategoryName());
                foodItemOrderedResponseDto.setFoodItemName(orderedFoodItems.getFoodItem().getFoodItemName());
                foodItemOrderedResponseDto.setQuantity(orderedFoodItems.getQuantity());
                foodItemOrderedResponseDto.setPricePerQuantity(orderedFoodItems.getFoodItem().getPrice());

                foodItemOrderedResponseDtoList.add(foodItemOrderedResponseDto);
            }

            orderByCustomerIdResponseDto.setFoodItemsList(foodItemOrderedResponseDtoList);

            orderByCustomerIdResponseDtoList.add(orderByCustomerIdResponseDto);
        }

        logger.info("Returning orderByCustomerIdResponseDtoList");
        return orderByCustomerIdResponseDtoList;

    }

    public void placeNewOrder(OrderRequestDto orderRequestDto) {

        Optional<Customer> customer = customerRepo.findById(orderRequestDto.getCustomerId());

        if (!customer.isPresent()) {

            logger.error("Customer doesn't exists");
            throw new ResourceNotFoundException("Customer with customer Id " + orderRequestDto.getCustomerId() + " doesn't exists");
        }

        Order order = new Order();

        List<OrderedFoodItems> orderedFoodItems = new ArrayList<>();

        logger.info("Getting foodItem list from orderRequestDto");
        List<FoodItemOrderedRequestDto> foodItemOrderedRequestDtoList = orderRequestDto.getFoodItems();

        double totalPrice = 0.0;

        for (FoodItemOrderedRequestDto foodItemOrderedRequestDto : foodItemOrderedRequestDtoList) {

            Category category = categoryRepo.findByCategoryName(foodItemOrderedRequestDto.getCategoryName().toLowerCase());

            if (category == null) {
                logger.info("Category does not exists");
                throw new ResourceNotFoundException(foodItemOrderedRequestDto.getCategoryName() + " does not exists");
            }

            Long categoryId = category.getCategoryId();

            logger.info("Getting foodItem from food_item database table according to foodItem name");
            FoodItems foodItem = foodItemsRepo.findByFoodItemNameAndCategoryId(foodItemOrderedRequestDto.getFoodItemName(), categoryId);

            if (foodItem == null) {
                logger.error("FoodItem does not exists");
                throw new ResourceNotFoundException(foodItemOrderedRequestDto.getFoodItemName() + " does not exists in " + foodItemOrderedRequestDto.getCategoryName());
            }
            if (foodItem.getStock() <= foodItemOrderedRequestDto.getQuantity()) {
                logger.error("Out of stock");
                throw new InsufficientResourcesException("We are currently unavailable for " + foodItemOrderedRequestDto.getQuantity() + " of " + foodItemOrderedRequestDto.getFoodItemName() + " in " + foodItemOrderedRequestDto.getCategoryName());
            }

            OrderedFoodItems orderedFoodItem = new OrderedFoodItems();

            orderedFoodItem.setOrder(order);
            orderedFoodItem.setFoodItem(foodItem);
            orderedFoodItem.setQuantity(foodItemOrderedRequestDto.getQuantity());

            logger.info("Updating foodItems stock after deducting it from quantity ordered by customer");
            foodItem.setStock(foodItem.getStock() - foodItemOrderedRequestDto.getQuantity());

            if (foodItem.getStock() < 0) {
                logger.info("Setting status to out of stock if stock is 0");
                foodItem.setStatus(FoodItemsStatus.CURRENTLY_UNAVAILABLE);
            }

            orderedFoodItem.setCategory(category);

            logger.info("Adding orderedFoodItem in orderedFoodItems");
            orderedFoodItems.add(orderedFoodItem);

            logger.info("Calling totalPriceOfOrderList method to calculate total price of each foodItem according to quantity");
            double itemTotalPrice = totalPriceOfOrderList.calculateTotalPrice(foodItem, foodItemOrderedRequestDto.getQuantity());
            totalPrice += itemTotalPrice;

        }

        order.setOrderFoodItems(orderedFoodItems);
        order.setTotal(totalPrice);
        order.setCustomer(customer.get());


        logger.info("Saving order into database table");
        orderRepo.save(order);
    }

}

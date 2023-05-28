package com.example.canteenmgmtsys.foodItems.model;

import com.example.canteenmgmtsys.category.model.Category;
import com.example.canteenmgmtsys.foodItems.enums.FoodItemsStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodItemId;
    private String foodItemName;
    private double price;
    private int stock;
    @Enumerated(EnumType.STRING)
    private FoodItemsStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

//    @OneToMany(mappedBy = "foodItem", cascade = CascadeType.ALL)
//    private List<OrderedFoodItems> orderFoodItems;

}

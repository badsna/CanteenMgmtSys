package com.example.canteenmgmtsys.category.model;

import com.example.canteenmgmtsys.foodItems.model.FoodItems;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="category")
    private List<FoodItems> foodItems;

}

package com.example.canteenmgmtsys.category.dto;

import com.example.canteenmgmtsys.category.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter

public class CategoryResponseDto implements Serializable {
    private String categoryName;

    /*public CategoryResponseDto(String categoryName) {
        this.categoryName=categoryName;
    }

    public static CategoryResponseDto of(Category category){
        return new CategoryResponseDto(
                category.getCategoryName()
        );
    }*/
}

package com.example.canteenmgmtsys.category.service;

import com.example.canteenmgmtsys.category.dto.CategoryRequestDto;
import com.example.canteenmgmtsys.category.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    void addNewCategory(CategoryRequestDto categoryRequestDto);
    List<CategoryResponseDto> getAllCategory();
}

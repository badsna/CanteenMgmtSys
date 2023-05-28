package com.example.canteenmgmtsys.category.controller;

import com.example.canteenmgmtsys.category.dto.CategoryRequestDto;
import com.example.canteenmgmtsys.category.dto.CategoryResponseDto;
import com.example.canteenmgmtsys.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add_new_category")
    public String addNewCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        categoryService.addNewCategory(categoryRequestDto);
        return "New Category Saved Successfully";

    }

    @GetMapping("/get_all_category")
    public List<CategoryResponseDto> getAllCategory() {
        return categoryService.getAllCategory();
    }
}

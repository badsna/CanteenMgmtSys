package com.example.canteenmgmtsys.category.repo;

import com.example.canteenmgmtsys.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    Category findByCategoryName(String categoryName);
}

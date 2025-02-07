package com.hust.seller.repository;

import com.hust.seller.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAll();
   Category findByCategoryId(int categoryId);
   Category findByCategoryName(String categoryName);

}

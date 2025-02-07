package com.hust.seller.security;

import com.hust.seller.entity.Category;
import com.hust.seller.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CatergoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAll();
   Category findByCategoryId(int categoryId);
}

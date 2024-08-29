package com.example.IngreWiz.repository;

import com.example.IngreWiz.model.Category;
import com.example.IngreWiz.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // Custom query method to find recipes by category
    List<Recipe> findByCategory(Category category);

    // Custom query method to find recipes by name containing a keyword
    List<Recipe> findByNameContainingIgnoreCase(String keyword);
}

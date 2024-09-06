package com.example.IngreWiz.repository;

import com.example.IngreWiz.model.Category;
import com.example.IngreWiz.model.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*
Public Recipes: Original, unmodifiable by users, accessible to all.
Private Recipes: User-specific copies that can be modified without affecting the public version.
Cloning: Create a new copy when a user wants to modify a public recipe.
 */

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    // Custom query method to find recipes by category
    List<Recipe> findByCategory(Category category);

    // Custom query method to find recipes by name 
    // SELECT * FROM recipes WHERE LOWER(recipe_name) LIKE LOWER('%{recipeName}%')
    List<Recipe> findByRecipeNameContainingIgnoreCase(String recipeName);
}

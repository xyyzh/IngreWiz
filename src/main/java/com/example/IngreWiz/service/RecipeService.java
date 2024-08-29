package com.example.IngreWiz.service;

import com.example.IngreWiz.model.Category;
import com.example.IngreWiz.model.Recipe;
import com.example.IngreWiz.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return recipe.orElse(null);
    }

    // Save a new recipe or update an existing one
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    // Find recipes by category
    public List<Recipe> getRecipesByCategory(Category category) {
        return recipeRepository.findByCategory(category);
    }

    // Find recipes by name containing a keyword
    public List<Recipe> getRecipesByName(String keyword) {
        return recipeRepository.findByNameContainingIgnoreCase(keyword);
    }
}

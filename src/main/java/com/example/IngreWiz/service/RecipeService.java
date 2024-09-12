package com.example.IngreWiz.service;

import com.example.IngreWiz.model.Category;
import com.example.IngreWiz.model.Chef;
import com.example.IngreWiz.model.Recipe;
import com.example.IngreWiz.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes() {
        Iterable<Recipe> iterable = recipeRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                            .collect(Collectors.toList());
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public List<Recipe> getRecipesByChefId(Long chefId) {
        return recipeRepository.findAllRecipesByChefId(chefId);
    }

    public Recipe addRecipe(Recipe recipe, Long chefId) {
        return recipeRepository.addRecipe(recipe, chefId);
    }

    public Recipe updateRecipe(Recipe recipe) {
        return recipeRepository.updateRecipe(recipe);
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
        return recipeRepository.findByRecipeNameContainingIgnoreCase(keyword);
    }
}

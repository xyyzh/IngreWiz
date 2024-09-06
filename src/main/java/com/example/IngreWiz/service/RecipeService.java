package com.example.IngreWiz.service;

import com.example.IngreWiz.model.Category;
import com.example.IngreWiz.model.Chef;
import com.example.IngreWiz.model.Recipe;
import com.example.IngreWiz.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe clonePublicRecipeForUser(Long recipeId) {
        Recipe publicRecipe = recipeRepository.findById(recipeId)
                .filter(Recipe::isPublic)
                .orElseThrow(() -> new RuntimeException("Public recipe not found"));

        // Clone the recipe
        Recipe userRecipe = new Recipe();
        userRecipe.setPublic(false); // Mark as private
        userRecipe.setRecipeName((publicRecipe.getRecipeName()));
        userRecipe.setCategory(publicRecipe.getCategory());
        userRecipe.setDescription(publicRecipe.getDescription());
        userRecipe.setSteps(publicRecipe.getSteps());
        userRecipe.setPhotos(publicRecipe.getPhotos());
        userRecipe.setKeyIngredients(publicRecipe.getKeyIngredients());

        // Save the cloned recipe
        return recipeRepository.save(userRecipe);
    }

    public Recipe clonePublicRecipeForUser(Recipe publicRecipe) {
        // Clone the recipe
        Recipe userRecipe = new Recipe();
        userRecipe.setPublic(false); // Mark as private
        userRecipe.setRecipeName((publicRecipe.getRecipeName()));
        userRecipe.setCategory(publicRecipe.getCategory());
        userRecipe.setDescription(publicRecipe.getDescription());
        userRecipe.setSteps(publicRecipe.getSteps());
        userRecipe.setPhotos(publicRecipe.getPhotos());
        userRecipe.setKeyIngredients(publicRecipe.getKeyIngredients());

        // Save the cloned recipe
        return recipeRepository.save(userRecipe);
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    // Save a new recipe or update an existing one
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    // Once make public, users cannot modify it, unless make a private copy
    public void makeRecipePublic(Recipe recipe){
        recipe.setPublic(true);
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

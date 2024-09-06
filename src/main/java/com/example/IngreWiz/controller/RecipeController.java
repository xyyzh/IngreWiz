package com.example.IngreWiz.controller;

import com.example.IngreWiz.model.Recipe;
import com.example.IngreWiz.service.RecipeService;
import com.example.IngreWiz.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);


    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    // GET /api/recipes/{id} - Get recipe by ID
    @GetMapping("/{id}")
    public Optional<Recipe> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    // POST /api/recipes - Create a new recipe
    @PostMapping
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeService.saveRecipe(recipe);
    }

    // PUT /api/recipes/{id} - Update an existing recipe
    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        Optional<Recipe> optionalRecipe = recipeService.getRecipeById(id);
        if (optionalRecipe.isPresent()) {
            Recipe existingRecipe = optionalRecipe.get();
            existingRecipe.setRecipeName(recipe.getRecipeName());
            existingRecipe.setCategory(recipe.getCategory());
            existingRecipe.setServings(recipe.getServings());
            existingRecipe.setDescription(recipe.getDescription());
            existingRecipe.setKeyIngredients(recipe.getKeyIngredients());
            existingRecipe.setSteps(recipe.getSteps());
            existingRecipe.setPhotos(recipe.getPhotos());
            return recipeService.saveRecipe(existingRecipe);
        } else {
            logger.warn("No recipe is updated");
            return null;
        }
    }

    // DELETE /api/recipes/{id} - Delete a recipe by ID
    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }

    // GET /api/recipes/mock - Mock endpoint
    @GetMapping("/mock")
    public Recipe getMockRecipe() {
        Recipe mockRecipe = new Recipe();
        mockRecipe.setRecipeName("Mock Recipe");
        mockRecipe.setCategory(Category.JAPANESE);
        mockRecipe.setServings(4);
        mockRecipe.setDescription("This is a mock recipe for testing purposes.");
        mockRecipe.setKeyIngredients(List.of("Mock Ingredient 1", "Mock Ingredient 2"));
        mockRecipe.setSteps(List.of("Mock Step 1", "Mock Step 2"));
        mockRecipe.setPhotos(List.of("mockphoto1.jpg", "mockphoto2.jpg"));

        return mockRecipe;
    }
}

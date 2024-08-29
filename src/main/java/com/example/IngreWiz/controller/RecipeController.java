package com.example.IngreWiz.controller;

import com.example.IngreWiz.model.Recipe;
import com.example.IngreWiz.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public Recipe getRecipeById(@PathVariable Long id) {
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
        Recipe existingRecipe = recipeService.getRecipeById(id);
        if (existingRecipe != null) {
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
}

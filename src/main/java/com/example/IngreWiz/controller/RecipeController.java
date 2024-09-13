package com.example.IngreWiz.controller;

import com.example.IngreWiz.model.Recipe;
import com.example.IngreWiz.service.ChefService;
import com.example.IngreWiz.service.RecipeService;
import com.example.IngreWiz.model.Category;
import com.example.IngreWiz.model.Chef;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/chef/{chefId}/profile/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ChefService chefService;

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    // GET /chef/{chefId}/profile/recipes - Get all recipes by chef ID
    @GetMapping
    public String getAllRecipesByChefId(@PathVariable Long chefId, Model model) {
        logger.info("Fetching recipes for chef with ID: " + chefId);
        List<Recipe> recipes = recipeService.getRecipesByChefId(chefId);
        if (recipes.isEmpty()) {
            logger.warn("No recipes found for chef with ID: " + chefId);
        } else {
            logger.info("Found " + recipes.size() + " recipes for chef with ID: " + chefId);
        }
        model.addAttribute("recipes", recipes);
        model.addAttribute("chefId", chefId);
        model.addAttribute("recipe", new Recipe()); // Add this line to ensure the recipe object is available
        return "recipes";
    }

    // GET /chef/{chefId}/profile/recipes/{recipeId} - Get recipe by ID
    @GetMapping("/{recipeId}")
    public Optional<Recipe> getRecipeById(@PathVariable Long recipeId) {
        return recipeService.getRecipeById(recipeId);
    }

    // GET /chef/{chefId}/profile/recipes/view - View recipes by chef ID
    @GetMapping("/view")
    public String viewRecipes(@PathVariable Long chefId, Model model) {
        Optional<Chef> chef = chefService.getChefById(chefId);
        if (chef.isPresent()) {
            List<Recipe> recipes = recipeService.getRecipesByChefId(chefId);
            if (recipes.isEmpty()) {
                logger.warn("No recipes found for chef with ID: " + chefId);
            } else {
                logger.info("Found " + recipes.size() + " recipes for chef with ID: " + chefId);
            }
            model.addAttribute("chef", chef.get());
            model.addAttribute("chefId", chef.get().getId());
            model.addAttribute("recipes", recipes);
            model.addAttribute("recipe", new Recipe());
            return "recipes";
        } else {
            throw new IllegalArgumentException("Chef not found");
        }
    }

    // GET /chef/{chefId}/profile/recipes/add - Show add recipe form
    @GetMapping("/add")
    public String showAddRecipeForm(@PathVariable Long chefId, Model model) {
        Optional<Chef> chef = chefService.getChefById(chefId);
        if (chef.isPresent()) {
            model.addAttribute("chef", chef.get());
            model.addAttribute("recipe", new Recipe());
            return "addRecipe";
        } else {
        throw new IllegalArgumentException("Chef not found");
    }
}

    // POST /chef/{chefId}/profile/recipes/add - Add a new recipe
    @PostMapping("/add")
    public String addRecipe(@PathVariable Long chefId, @ModelAttribute Recipe recipe, Model model) {
        recipeService.addRecipe(recipe, chefId);
        model.addAttribute("chefId", chefId);
        model.addAttribute("recipe", recipe);
        return "addRecipeSuccess";
    }


    // GET /chef/{chefId}/profile/recipes/{recipeId}/update - Show update form for a recipe
    @GetMapping("/{recipeId}/update")
    public String showUpdateForm(@PathVariable Long recipeId, Model model) {
        Optional<Recipe> recipe = recipeService.getRecipeById(recipeId);
        if (recipe.isPresent()) {
            model.addAttribute("recipe", recipe.get());
            return "updateRecipe";
        } else {
            throw new IllegalArgumentException("Recipe not found");
        }
    }

    // PUT /chef/{chefId}/profile/recipes/{recipeId} - Update an existing recipe
    @PutMapping("/{recipeId}")
    public Recipe updateRecipe(@RequestBody Recipe recipe, @PathVariable Long recipeId) {
        Optional<Recipe> optionalRecipe = recipeService.getRecipeById(recipeId);
        if (optionalRecipe.isPresent()) {
            Recipe existingRecipe = optionalRecipe.get();
            existingRecipe.setRecipeName(recipe.getRecipeName());
            existingRecipe.setCategory(recipe.getCategory());
            existingRecipe.setServings(recipe.getServings());
            existingRecipe.setDescription(recipe.getDescription());
            existingRecipe.setKeyIngredients(recipe.getKeyIngredients());
            existingRecipe.setSteps(recipe.getSteps());
            existingRecipe.setPhotos(recipe.getPhotos());
            return recipeService.updateRecipe(existingRecipe);
        } else {
            logger.warn("No recipe is updated");
            return null;
        }
    }

    // DELETE /chef/{chefId}/profile/recipes/{recipeId} - Delete a recipe by ID
    @DeleteMapping("/{recipeId}")
    public void deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
    }

}

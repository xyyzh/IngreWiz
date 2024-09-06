package com.example.IngreWiz.service;

import com.example.IngreWiz.model.Chef;
import com.example.IngreWiz.model.Recipe;
import com.example.IngreWiz.model.Category;
import com.example.IngreWiz.repository.ChefRepository;
import com.example.IngreWiz.repository.RecipeRepository;
import com.example.IngreWiz.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChefService {

    @Autowired
    private final ChefRepository chefRepository;
    @Autowired
    private final RecipeService recipeService;

    @Autowired
    public ChefService(ChefRepository chefRepository, RecipeService recipeService) {
        this.chefRepository = chefRepository;
        this.recipeService = recipeService;
    }

    public Optional<Chef> getChefById(Long id) {
        return chefRepository.findById(id);
    }

    public Chef saveChef(Chef chef) {
        return chefRepository.save(chef);
    }

    public void addRecipeToChef(Long chefId, Long recipeId) {
        Optional<Chef> optionalChef = getChefById(chefId);
        Optional<Recipe> optionalRecipe = recipeService.getRecipeById(recipeId);

        if (optionalChef.isPresent() && optionalRecipe.isPresent()) {
            Chef chef = optionalChef.get();
            Recipe recipe = optionalRecipe.get();
            List<Recipe> savedRecipes = chef.getSavedRecipes();

            Recipe clonedPrivateRecipe = recipeService.clonePublicRecipeForUser(recipe);
            savedRecipes.add(clonedPrivateRecipe);
            chef.setSavedRecipes(savedRecipes);
            chefRepository.save(chef);


//            else if (!savedRecipes.contains(recipe)) {
//                savedRecipes.add(recipe);
//                chef.setSavedRecipes(savedRecipes);
//                chefRepository.save(chef);
//            }
//            else{
//                System.out.println("Recipe already saved!");
//            }

        } else {
            throw new RuntimeException("Chef or Recipe not found.");
        }
    }

    public void removeRecipeFromChef(Long chefId, Recipe recipe) {
        Optional<Chef> optionalChef = chefRepository.findById(chefId);

        if (optionalChef.isPresent()) {
            Chef chef = optionalChef.get();
            List<Recipe> savedRecipes = chef.getSavedRecipes();

            if (savedRecipes.contains(recipe)) {
                savedRecipes.remove(recipe);
                chef.setSavedRecipes(savedRecipes);
                chefRepository.save(chef);
            }
        } else {
            // Handle the case where the chef is not found
            throw new RuntimeException("Chef not found");
        }
    }

    public Chef createChef(String chefName, String email, Category preferredCuisineCategory) {
        Chef chef = new Chef(chefName, email, preferredCuisineCategory);
        return chefRepository.save(chef);
    }
}


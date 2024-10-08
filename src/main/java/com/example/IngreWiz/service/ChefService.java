package com.example.IngreWiz.service;

import com.example.IngreWiz.model.Chef;
import com.example.IngreWiz.model.Recipe;
import com.example.IngreWiz.model.Category;
import com.example.IngreWiz.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public Chef addChef(Chef chef) {
        return chefRepository.addChef(chef);
    }


    public void removeRecipeFromChef(Long chefId, Recipe recipe) {
        Optional<Chef> optionalChef = chefRepository.findById(chefId);

        if (optionalChef.isPresent()) {
            Chef chef = optionalChef.get();
            List<Recipe> savedRecipes = chef.getSavedRecipes();

            if (savedRecipes.contains(recipe)) {
                savedRecipes.remove(recipe);
                chef.setSavedRecipes(savedRecipes);
                chefRepository.addChef(chef);
            }
        } else {
            throw new RuntimeException("Chef not found");
        }
    }

    public Chef createChef(String chefName, String email, Category preferredCuisineCategory) {
        Chef chef = new Chef(chefName, email, preferredCuisineCategory);
        chefRepository.addChef(chef);
        return chef;
    }

    public Optional<Chef> getChefByName(String name) {
        return chefRepository.findByChefName(name);
    }

    //chefRepository.findAll() returns an Iterable<Chef>, not a List<Chef>. 
    //resolve this by converting the Iterable to a List.

    public List<Chef> getAllChefs() {
        Iterable<Chef> iterable = chefRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                            .collect(Collectors.toList());
    }

    public void updateChefProfile(Long id, String email, Category category, String phoneNumber, String bio) {
        Optional<Chef> optionalChef = getChefById(id);

        if (optionalChef.isPresent()) {
            Chef chef = optionalChef.get();
            chef.setEmail(email);
            chef.setPreferredCuisineCategory(category);
            chef.setPhoneNumber(phoneNumber);
            chef.setBio(bio);
            chefRepository.updateChef(chef);
        } else {
            throw new RuntimeException("Chef not found");
        }
    }
}


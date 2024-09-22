package com.example.IngreWiz.model;

//import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chef {

    private Long id;

    private String chefName;

    private Category preferredCuisineCategory;

    private List<Recipe> savedRecipes;

    private String email;
    private String phoneNumber;

    private String bio;

    public Chef(){}

    public Chef(String chefName, String email, Category preferredCuisineCategory){
        this.chefName = chefName;
        this.email = email;
        this.preferredCuisineCategory = preferredCuisineCategory;
        this.savedRecipes = new ArrayList<>();
    }

    public void addSavedRecipe(Recipe recipe) {
        if (!savedRecipes.contains(recipe)) {
            savedRecipes.add(recipe);
        }
    }

    public void removeSavedRecipe(Recipe recipe) {
        savedRecipes.remove(recipe);
    }
}

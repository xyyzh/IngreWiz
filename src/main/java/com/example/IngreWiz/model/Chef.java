package com.example.IngreWiz.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chefs")
public class Chef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chefName;

    private Category preferredCuisineCategory;

    @OneToMany(mappedBy = "chef", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recipe> savedRecipes;

    private String email;
    private String phoneNumber;

    private String profilePictureUrl;

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

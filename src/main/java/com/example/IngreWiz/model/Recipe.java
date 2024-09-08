package com.example.IngreWiz.model;

import java.util.List;
//import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recipe {

    private Long recipeId;

    private String recipeName;


    private Category category;

    private int servings;


    private String description;

    private List<String> keyIngredients;

    private List<String> steps;

    private List<String> photos;

    private Chef chef;

    public Recipe(){}

    public Recipe(String recipeName, Category category, int servings, String description, List<String> keyIngredients, List<String> steps, List<String> photos) {
        this.recipeName = recipeName;
        this.category = category;
        this.servings = servings;
        this.description = description;
        this.keyIngredients = keyIngredients;
        this.steps = steps;
        this.photos = photos;
    }
}

package com.example.IngreWiz.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    private boolean isPublic;

    private String recipeName;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int servings;

    @Column(length = 1000)
    private String description;

    @ElementCollection
    private List<String> keyIngredients;

    @ElementCollection
    private List<String> steps;

    @ElementCollection
    private List<String> photos;

    @ManyToOne
    @JoinColumn(name = "chef_id")
    private Chef chef;

    public Recipe(){}

    public Recipe(String recipeName, Category category, int servings, String description, List<String> keyIngredients, List<String> steps, List<String> photos) {
        this.isPublic = false;
        this.recipeName = recipeName;
        this.category = category;
        this.servings = servings;
        this.description = description;
        this.keyIngredients = keyIngredients;
        this.steps = steps;
        this.photos = photos;
    }

    /*
    public Recipe(boolean isPublic, String recipeName, Category category, int servings, String description, List<String> keyIngredients, List<String> steps, List<String> photos) {
        this.isPublic = isPublic;
        this.recipeName = recipeName;
        this.category = category;
        this.servings = servings;
        this.description = description;
        this.keyIngredients = keyIngredients;
        this.steps = steps;
        this.photos = photos;
    }

     */

}

package com.example.IngreWiz.model;

import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "recipes") // Add table name for JDBC
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    private String recipeName;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int servings;

    @Column(length = 1000)
    private String description;

    @ElementCollection
    @CollectionTable(name = "recipe_key_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient")
    private List<String> keyIngredients;

    @ElementCollection
    @CollectionTable(name = "recipe_steps", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "step")
    private List<String> steps;

    @ElementCollection
    @CollectionTable(name = "recipe_photos", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "photo")
    private List<String> photos;

    @ManyToOne
    @JoinColumn(name = "chef_id")
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

package com.example.IngreWiz.model;

import jakarta.persistence.*;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Chef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chefName;

    private Category preferredCuisineCategory;

    private List<Recipe> savedRecipes;

    public Chef(){}

    public Chef(String chefName, Category preferredCuisineCategory, List<Recipe> savedRecipes){
        this.chefName = chefName;
        this.preferredCuisineCategory = preferredCuisineCategory;
        this.savedRecipes = savedRecipes;
    }


}

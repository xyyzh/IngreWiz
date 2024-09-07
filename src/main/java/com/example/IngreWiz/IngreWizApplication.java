package com.example.IngreWiz;

import com.example.IngreWiz.model.Category;
import com.example.IngreWiz.model.Chef;
import com.example.IngreWiz.model.Recipe;
import com.example.IngreWiz.service.DatabaseService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class IngreWizApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngreWizApplication.class, args);
		DatabaseService.initializeDatabase(); 

		//testRecipes();
		//testChefs();
		//testSetters();

		//System.out.println("Tests completed. Exiting application.");
		//System.exit(0);
	}

	private static void testRecipes() {
		Recipe r1 = createSampleRecipe("Italian Pasta", Category.ITALIAN);
		Recipe r2 = createSampleRecipe("Sushi Roll", Category.JAPANESE);
		Recipe r3 = createSampleRecipe("Tacos", Category.MEXICAN);

		System.out.println("Recipes created:");
		System.out.println(r1.getRecipeName() + " - " + r1.getCategory());
		System.out.println(r2.getRecipeName() + " - " + r2.getCategory());
		System.out.println(r3.getRecipeName() + " - " + r3.getCategory());
	}

	private static void testChefs() {
		Chef c1 = createSampleChef("Mario", "mario@example.com", Category.ITALIAN);
		Chef c2 = createSampleChef("Hiroshi", "hiroshi@example.com", Category.JAPANESE);
		Chef c3 = createSampleChef("Maria", "maria@example.com", Category.MEXICAN);

		System.out.println("Chefs created:");
		System.out.println(c1.getChefName() + " - " + c1.getEmail() + " - " + c1.getPreferredCuisineCategory());
		System.out.println(c2.getChefName() + " - " + c2.getEmail() + " - " + c2.getPreferredCuisineCategory());
		System.out.println(c3.getChefName() + " - " + c3.getEmail() + " - " + c3.getPreferredCuisineCategory());
	}

	private static void testSetters() {
		System.out.println("\nTesting setters:");
		
			// Test Recipe setters
		Recipe recipe = createSampleRecipe("Test Recipe", Category.ITALIAN);
		recipe.setRecipeName("Updated Recipe");
		recipe.setCategory(Category.FRENCH);
		recipe.setServings(4);
		recipe.setDescription("Updated description");
		recipe.setKeyIngredients(Arrays.asList("new ingredient1", "new ingredient2"));
		recipe.setSteps(Arrays.asList("New Step 1", "New Step 2"));
		recipe.setPhotos(Arrays.asList("newPhotoURL1", "newPhotoURL2"));

		System.out.println("Updated Recipe: " + recipe.getRecipeName() + " - " + recipe.getCategory());
		
			// Test Chef setters
		Chef chef = createSampleChef("Test Chef", "testchef@example.com", Category.ITALIAN);
		chef.setChefName("Updated Chef");
		chef.setPreferredCuisineCategory(Category.FRENCH);
		chef.setSavedRecipes(Arrays.asList(recipe));

		System.out.println("Updated Chef: " + chef.getChefName() + " - " + chef.getPreferredCuisineCategory());
		System.out.println("Saved Recipes: " + chef.getSavedRecipes().size());
	}

	private static Recipe createSampleRecipe(String name, Category category) {
		List<String> ingredients = Arrays.asList("ingredient1", "ingredient2");
		List<String> steps = Arrays.asList("Step 1", "Step 2");
		List<String> photos = Collections.singletonList("photoURL");

		return new Recipe(name, category, 2, "Sample " + name, ingredients, steps, photos);
	}

	private static Chef createSampleChef(String name, String email, Category preferredCategory) {
		return new Chef(name, email, preferredCategory);
	}
}

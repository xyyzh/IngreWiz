package com.example.IngreWiz.Service;

import com.example.IngreWiz.model.Chef;
import com.example.IngreWiz.model.Recipe;
import com.example.IngreWiz.model.Category;
import com.example.IngreWiz.service.ChefService;
import com.example.IngreWiz.service.RecipeService;
import com.example.IngreWiz.repository.ChefRepository;
import com.example.IngreWiz.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
public class ServiceTest {

    @Autowired
    private ChefService chefService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void testAddChef() {
        Chef chef = new Chef();
        chef.setChefName("Gordon Ramsay");
        chef.setEmail("gordon@example.com");
        chef.setPreferredCuisineCategory(Category.ITALIAN);
        chef.setPhoneNumber("1234567890");
        

        Chef savedChef = chefService.addChef(chef);

        Optional<Chef> foundChef = chefRepository.findById(savedChef.getId());
        assertThat(foundChef).isPresent();
        assertThat(foundChef.get().getChefName()).isEqualTo("Gordon Ramsay");
    }

    @Test
    public void testAddRecipe() {
        Chef chef = new Chef();
        chef.setChefName("Gordon Ramsay");
        chef.setEmail("gordon@example.com");
        chef.setPreferredCuisineCategory(Category.ITALIAN);
        chef.setPhoneNumber("1234567890");

        Chef savedChef = chefService.addChef(chef);

        Recipe recipe = new Recipe();
        recipe.setRecipeName("Beef Wellington");
        recipe.setDescription("A classic British dish.");
        recipe.setKeyIngredients(List.of("Beef", "puff pastry", "mushrooms"));
        recipe.setSteps(List.of("1. Prepare the beef... 2. Wrap in pastry..."));
        recipe.setPhotos(List.of("photo1.jpg", "photo2.jpg"));
        recipe.setCategory(Category.ITALIAN);
        recipe.setServings(4);
        recipe.setChef(savedChef);

        Recipe savedRecipe = recipeService.addRecipe(recipe);

        Optional<Recipe> foundRecipe = recipeRepository.findById(savedRecipe.getRecipeId());
        assertThat(foundRecipe).isPresent();
        assertThat(foundRecipe.get().getRecipeName()).isEqualTo("Beef Wellington");
    }
}

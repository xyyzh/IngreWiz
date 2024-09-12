package com.example.IngreWiz.repository;

import com.example.IngreWiz.model.Category;
import com.example.IngreWiz.model.Recipe;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;


@Repository
public class RecipeRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecipeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Custom query method to find recipes by category
    public List<Recipe> findByCategory(Category category) {
        String sql = "SELECT * FROM recipe WHERE category = ?";
        return jdbcTemplate.query(sql, ps -> ps.setString(1, category.name()), new RecipeRowMapper());
    }

    // Custom query method to find recipes by name
    public List<Recipe> findByRecipeNameContainingIgnoreCase(String recipeName) {
        String sql = "SELECT * FROM recipe WHERE LOWER(recipe_name) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, ps -> ps.setString(1, "%" + recipeName + "%"), new RecipeRowMapper());
    }

    public Optional<Recipe> findById(Long id) {
        String sql = "SELECT * FROM recipe WHERE id = ?";
        List<Recipe> recipes = jdbcTemplate.query(sql, ps -> ps.setLong(1, id), new RecipeRowMapper());
        return recipes.isEmpty() ? Optional.empty() : Optional.of(recipes.get(0));
    }

    public List<Recipe> findAllRecipesByChefId(Long chefId) {
        String sql = "SELECT * FROM recipe WHERE chef_id = ?";
        return jdbcTemplate.query(sql, ps -> ps.setLong(1, chefId), new RecipeRowMapper());
    }

    public Recipe addRecipe(Recipe recipe, Long chefId) {
        String sql = "INSERT INTO recipe (name, description, category, servings, ingredients, steps, chef_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, ps -> {
            ps.setString(1, recipe.getRecipeName());
            ps.setString(2, recipe.getDescription());
            ps.setString(3, recipe.getCategory().name());
            ps.setInt(4, recipe.getServings());
            ps.setString(5, String.join(", ", recipe.getKeyIngredients()));
            ps.setString(6, String.join("\n", recipe.getSteps()));
            ps.setLong(7, chefId);
        });
        return recipe;
    }

    public Recipe updateRecipe(Recipe recipe) {
        String sql = "UPDATE recipe SET name = ?, description = ?, category = ?, servings = ?, ingredients = ?, steps = ? WHERE id = ?";
        jdbcTemplate.update(sql, ps -> {
            ps.setString(1, recipe.getRecipeName());
            ps.setString(2, recipe.getDescription());
            ps.setString(3, recipe.getCategory().name());
            ps.setInt(4, recipe.getServings());
            ps.setString(5, String.join(", ", recipe.getKeyIngredients()));
            ps.setString(6, String.join("\n", recipe.getSteps()));
            ps.setLong(7, recipe.getRecipeId());
        });
        return recipe;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM recipe WHERE id = ?";
        jdbcTemplate.update(sql, ps -> ps.setLong(1, id));
    }

    public Iterable<Recipe> findAll() {
        String sql = "SELECT * FROM recipe";
        return jdbcTemplate.query(sql, new RecipeRowMapper());
    }

    private static class RecipeRowMapper implements RowMapper<Recipe> {
        @Override
        public Recipe mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            Recipe recipe = new Recipe();
            recipe.setRecipeId(rs.getLong("id"));
            recipe.setRecipeName(rs.getString("name"));
            recipe.setCategory(Category.valueOf(rs.getString("category")));
            recipe.setServings(rs.getInt("servings"));
            recipe.setDescription(rs.getString("description"));
            recipe.setKeyIngredients(Arrays.asList(rs.getString("ingredients").split(",")));
            recipe.setSteps(Arrays.asList(rs.getString("steps").split("\n")));
            recipe.setChefId(rs.getLong("chef_id"));
            return recipe;
        }
    }
}

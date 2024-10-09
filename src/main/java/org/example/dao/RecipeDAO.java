package org.example.dao;


import org.example.entity.Recipe;

import java.util.List;

public interface RecipeDAO {

    Recipe save(Recipe recipe);

    Recipe findById(Long id);

    List<Recipe> findAll();

    List<Recipe> findByName(String name);

    List<Recipe> findByCategory(String category);

    List<Recipe> findPopularRecipes();

    List<Recipe> findRecipesOfTheDay();

    void delete(Recipe recipe);

    void update(Recipe recipe);

    List<Recipe> findRecipesByUserId(Long userId);

    List<Recipe> findSavedRecipesByUserId(Long userId);

    void saveRecipeToBook(Long userId, Long recipeId);

    void removeRecipeFromBook(Long userId, Long recipeId);

}


package org.example.dao;

import org.example.entity.Recipe;
import org.example.entity.User;
import org.example.util.ConnectionProvider;
import org.example.util.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao {
    private ConnectionProvider connectionProvider;

    public RecipeDao(){
        try {
            this.connectionProvider = ConnectionProvider.getInstance();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public Recipe saveRecipe(Recipe recipe, User user) {
        String sql = "INSERT INTO \"Recipe\" (name, description, category, \"preparationTime\", servings, ingredients, steps, user_id, \"createdAt\") VALUES (?, ?, ?, ?,?,?,?,?,?) RETURNING id";

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = today.format(formatter);

        try (Connection connection = connectionProvider.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, recipe.getName());
            preparedStatement.setString(2, recipe.getDescription());
            preparedStatement.setString(3, recipe.getCategory());
            preparedStatement.setInt(4, recipe.getPreparationTime());
            preparedStatement.setInt(5, recipe.getServings());
            preparedStatement.setString(6, recipe.getIngredients());
            preparedStatement.setString(7, recipe.getSteps());
//            preparedStatement.setBytes(8, recipe.getImage());
            preparedStatement.setLong(8, user.getId());
            preparedStatement.setString(9, formattedDate);


            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                recipe.setId(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DbException e) {
            throw new RuntimeException(e);
        }

        return recipe;
    }


    public List<Recipe> findCreatedRecipesByUserId(Long userId) {
        String sql = "SELECT * FROM \"Recipe\" WHERE user_id = ?";
        List<Recipe> recipes = new ArrayList<>();

        try (Connection connection = connectionProvider.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(resultSet.getLong("id"));
                recipe.setName(resultSet.getString("name"));
                recipe.setDescription(resultSet.getString("description"));
                recipe.setCategory(resultSet.getString("category"));
                recipe.setPreparationTime(resultSet.getInt("preparationTime"));
                recipe.setServings(resultSet.getInt("servings"));
                recipe.setIngredients(resultSet.getString("ingredients"));
                recipe.setSteps(resultSet.getString("steps"));
//                recipe.setCreatedAt(resultSet.getString("\"createdAt\""));

                // recipe.setImage(resultSet.getBytes("image"));


                recipes.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DbException e) {
            throw new RuntimeException(e);
        }

        return recipes;
    }

    public List<Recipe> findFavoriteRecipesByUserId(Long userId) {
        String sql = "SELECT * FROM \"UserFavoriteRecipes\" WHERE user_id = ?";
        List<Recipe> recipes = new ArrayList<>();

        try (Connection connection = connectionProvider.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                RecipeDao recipeDao = new RecipeDao();
                Recipe recipe = recipeDao.findById(resultSet.getLong("recipe_id"));


                recipes.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DbException e) {
            throw new RuntimeException(e);
        }

        return recipes;
    }

    public Recipe findById(Long id) {
        String sql = "SELECT * FROM \"Recipe\" WHERE id = ?";
        Recipe recipe = null;

        try (Connection connection = connectionProvider.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                recipe = new Recipe();
                recipe.setId(resultSet.getLong("id"));
                recipe.setName(resultSet.getString("name"));
                recipe.setDescription(resultSet.getString("description"));
                recipe.setCategory(resultSet.getString("category"));
                recipe.setPreparationTime(resultSet.getInt("preparationTime"));
                recipe.setServings(resultSet.getInt("servings"));
                recipe.setIngredients(resultSet.getString("ingredients"));
                recipe.setSteps(resultSet.getString("steps"));
//                recipe.setCreatedAt(resultSet.getString("\"createdAt\""));

                Long userId = resultSet.getLong("user_id");
                if (userId != null) {
                    User user = new User();
                    user.setId(userId);
                    recipe.setUser(user);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DbException e) {
            throw new RuntimeException(e);
        }

        return recipe;
    }

    public void deleteRecipe(Long recipeId) throws DbException {
        String sql = "DELETE FROM \"Recipe\" WHERE id = ?";

        try (Connection connection = connectionProvider.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, recipeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException("Ошибка при удалении рецепта", e);
        }
    }

    public void updateRecipe(Recipe recipe) throws DbException {
        String updateSql = "UPDATE \"Recipe\" SET name = ?, description = ?, category = ?, \"preparationTime\" = ?," +
                " servings = ?, ingredients = ?, steps = ? WHERE id = ?";

        try (Connection connection = connectionProvider.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(updateSql)) {

            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getDescription());
            statement.setString(3, recipe.getCategory());
            statement.setInt(4, recipe.getPreparationTime());
            statement.setInt(5, recipe.getServings());
            statement.setString(6, recipe.getIngredients());
            statement.setString(7, recipe.getSteps());
            statement.setLong(8, recipe.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException("Ошибка при обновлении рецепта", e);
        }
    }




//
//    Recipe findById(Long id);
//
//    List<Recipe> findAll();
//
//    List<Recipe> findByName(String name);
//
//    List<Recipe> findByCategory(String category);
//
//    List<Recipe> findPopularRecipes();
//
//    List<Recipe> findRecipesOfTheDay();
//
//    void delete(Recipe recipe);
//
//    void update(Recipe recipe);
//
//    List<Recipe> findRecipesByUserId(Long userId);
//
//    List<Recipe> findSavedRecipesByUserId(Long userId);
//
//    void saveRecipeToBook(Long userId, Long recipeId);
//
//    void removeRecipeFromBook(Long userId, Long recipeId);

}


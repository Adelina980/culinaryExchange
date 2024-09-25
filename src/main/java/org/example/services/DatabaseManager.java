package org.example.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:postgresql://localhost:8080/database";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "123";

    private Connection connection;

    public DatabaseManager() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Пользователи ---

    public void createUser(String username, String email, String password, String avatar, String bio,
                           String favoriteCuisines, String skillLevel, String achievements) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO users (username, email, password, avatar, bio, favorite_cuisines, skill_level, achievements) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password); // Используйте хэширование паролей!
            stmt.setString(4, avatar);
            stmt.setString(5, bio);
            stmt.setString(6, favoriteCuisines);
            stmt.setString(7, skillLevel);
            stmt.setString(8, achievements);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ... другие методы для работы с пользователями (получение, обновление, удаление) ...

    // --- Рецепты ---

    public void createRecipe(int userId, String title, String description, String ingredients, String instructions,
                             String image, String category, int preparationTime, int servings) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO recipes (user_id, title, description, ingredients, instructions, image, category, preparation_time, servings, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())")) {
            stmt.setInt(1, userId);
            stmt.setString(2, title);
            stmt.setString(3, description);
            stmt.setString(4, ingredients);
            stmt.setString(5, instructions);
            stmt.setString(6, image);
            stmt.setString(7, category);
            stmt.setInt(8, preparationTime);
            stmt.setInt(9, servings);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ... другие методы для работы с рецептами (получение, обновление, удаление, получение по id, получение по имени) ...

    // --- Комментарии ---

    public void createComment(int recipeId, int userId, String content) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO comments (recipe_id, user_id, content, created_at) VALUES (?, ?, ?, NOW())")) {
            stmt.setInt(1, recipeId);
            stmt.setInt(2, userId);
            stmt.setString(3, content);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ... другие методы для работы с комментариями (получение, обновление, удаление) ...

    // --- Рейтинги ---

    public void createRating(int recipeId, int userId, int rating) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO ratings (recipe_id, user_id, rating, created_at) VALUES (?, ?, ?, NOW())")) {
            stmt.setInt(1, recipeId);
            stmt.setInt(2, userId);
            stmt.setInt(3, rating);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ... другие методы для работы с рейтингами (получение, обновление, удаление) ...

    // --- Сохраненные рецепты ---

    public void saveRecipe(int userId, int recipeId) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO saved_recipes (user_id, recipe_id) VALUES (?, ?)")) {
            stmt.setInt(1, userId);
            stmt.setInt(2, recipeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ... другие методы для работы с сохраненными рецептами (получение, обновление, удаление) ...

    // --- Кулинарные челленджи ---

    public void createChallenge(String title, String description, Timestamp startDate, Timestamp endDate) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO challenges (title, description, start_date, end_date) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setTimestamp(3, startDate);
            stmt.setTimestamp(4, endDate);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ... другие методы для работы с челленджами (получение, обновление, удаление) ...

    // --- Участники челленджа ---

    public void joinChallenge(int userId, int challengeId, String image) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO challenge_participants (user_id, challenge_id, image) VALUES (?, ?, ?)")) {
            stmt.setInt(1, userId);
            stmt.setInt(2, challengeId);
            stmt.setString(3, image);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ... другие методы для работы с участниками челленджа (получение, обновление, удаление) ...

    // --- Кулинарные книги ---

    public void createCookbook(int userId, String name) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO cookbooks (user_id, name) VALUES (?, ?)")) {
            stmt.setInt(1, userId);
            stmt.setString(2, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ... другие методы для работы с кулинарными книгами (получение, обновление, удаление) ...

    // --- Рецепты в кулинарных книгах ---

    public void addRecipeToCookbook(int cookbookId, int recipeId) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO cookbook_recipes (cookbook_id, recipe_id) VALUES (?, ?)")) {
            stmt.setInt(1, cookbookId);
            stmt.setInt(2, recipeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ... другие методы для работы с рецептами в кулинарных книгах (получение, обновление, удаление) ...

    // --- Обратная связь ---

    public void createFeedback(int userId, String subject, String message, String image) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO feedback (user_id, subject, message, image) VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, userId);
            stmt.setString(2, subject);
            stmt.setString(3, message);
            stmt.setString(4, image);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ... другие методы для работы с обратной связью (получение, обновление, удаление) ...

    // --- Администраторы ---

    public void createAdmin(int userId) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO admins (user_id) VALUES (?)")) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ... другие методы для работы с администраторами (получение, обновление, удаление) ...

    public class Recipe {

        private int id;
        private int userId;
        private String title;
        private String description;
        private String ingredients;
        private String instructions;
        private String image;
        private String category;
        private int preparationTime;
        private int servings;
        private final Timestamp createdAt;


        public Recipe(int id, int userId, String title, String description,
                      String ingredients, String instructions, String image, String category, int preparationTime, int servings, Timestamp createdAt) {
            this.id = id;
            this.userId = userId;
            this.title = title;
            this.description = description;
            this.ingredients = ingredients;
            this.instructions = instructions;
            this.image = image;
            this.category = category;
            this.preparationTime = preparationTime;
            this.servings = servings;
            this.createdAt = createdAt;
        }

        // Геттеры для доступа к полям
        public int getId() {
            return id;
        }

        public int getUserId() {
            return userId;
        }

        public String getTitle() {
            return title;
        }

        // ... геттеры для других полей

        // Сеттеры для изменения значений полей
        public void setTitle(String title) {
            this.title = title;
        }

        // ... сеттеры для других полей

        // ... другие методы для работы с данными (например, расчет средней оценки)
    }


    // --- Вспомогательные методы ---

    public List<Recipe> getRecipesByUserId(int userId) {
        List<Recipe> recipes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM recipes WHERE user_id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                recipes.add(new Recipe(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("ingredients"),
                        rs.getString("instructions"),
                        rs.getString("image"),
                        rs.getString("category"),
                        rs.getInt("preparation_time"),
                        rs.getInt("servings"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    // --- Метод для закрытия соединения с базой данных ---

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


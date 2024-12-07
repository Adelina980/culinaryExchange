package org.example.util;

import org.example.dao.*;
import org.example.service.UserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.flywaydb.core.Flyway;
@WebListener
public class InitListener implements ServletContextListener {
    private ConnectionProvider connectionProvider;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Flyway flyway = Flyway.configure()//.baselineOnMigrate(true)
                    .dataSource("jdbc:postgresql://localhost:5432/HashPasswordProject", "postgres", "123").load();
            // Start the migration
            flyway.migrate();
            connectionProvider = ConnectionProvider.getInstance();
            sce.getServletContext().setAttribute("commentDao", new CommentDao(connectionProvider));
            sce.getServletContext().setAttribute("preferenceDao", new PreferenceDao(connectionProvider));
            sce.getServletContext().setAttribute("ratingDao", new RatingDao(connectionProvider));
            sce.getServletContext().setAttribute("recipeDao", new RecipeDao(connectionProvider));
            sce.getServletContext().setAttribute("userDao", new UserDao(connectionProvider));
            sce.getServletContext().setAttribute("userFavoriteRecipesDao", new UserFavoriteRecipesDao(connectionProvider));
            sce.getServletContext().setAttribute("userPreferenceDao", new UserPreferenceDao(connectionProvider));
            sce.getServletContext().setAttribute("userService", new UserService());

            Map<UUID, Long> userSessions = new HashMap<>();

            sce.getServletContext().setAttribute("USER_SESSIONS", userSessions);
        } catch (DbException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void contextDestroyed(ServletContextEvent sce) {
        // Закрываем подключение
        if(connectionProvider != null){
            connectionProvider.releaseConnection((Connection) connectionProvider);
            System.out.println("Соединение с БД закрыто.");
        } else{
            System.out.println("Соединение с БД не было установлено.");
        }
    }
}

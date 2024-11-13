package org.example.dao;

import org.example.entity.Preference;
import org.example.entity.User;
import org.example.entity.UserPreference;
import org.example.util.ConnectionProvider;
import org.example.util.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPreferenceDao {
    ConnectionProvider connectionProvider;

    public UserPreferenceDao() {
        try {
            this.connectionProvider = ConnectionProvider.getInstance();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public UserPreference getUserPreference(Long user_id) {
        String sql = "SELECT * FROM \"UserPreference\" WHERE user_id = ?";
        UserPreference userPreference = null;

        try (Connection connection = connectionProvider.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userPreference = new UserPreference();
                UserDao userDao = new UserDao();
                User user = userDao.findById(user_id);
                PreferenceDao preferenceDao = new PreferenceDao();
                Preference preference = preferenceDao.findById(resultSet.getLong("preference_id"));

                userPreference.setId(resultSet.getLong("id"));
                userPreference.setUser(user);
                userPreference.setPreference(preference);



            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DbException e) {
            throw new RuntimeException(e);
        }

        return userPreference;
    }

    public UserPreference updateUserPreference(UserPreference updatedUserPreference) {
        try (Connection connection = connectionProvider.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE \"UserPreference\" SET user_id = ?, preference_id = ? WHERE id = ?")) {
//            preparedStatement.setBlob(1, updatedUser.getAvatar());
            preparedStatement.setLong(1, updatedUserPreference.getUser().getId());
            preparedStatement.setLong(2, updatedUserPreference.getPreference().getId());
            preparedStatement.setLong(3, updatedUserPreference.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return updatedUserPreference;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DbException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

package org.example.dao;


import org.example.entity.*;
import org.example.util.ConnectionProvider;
import org.example.util.DbException;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserDao {
    private ConnectionProvider connectionProvider;

//    public UserDao(ConnectionProvider connectionProvider){
//        this.connectionProvider = connectionProvider;
//    }

    public UserDao(ConnectionProvider connectionProvider) {
        try {
            this.connectionProvider = connectionProvider.getInstance();
        } catch (DbException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User save(User user) {
        String sql = "INSERT INTO \"User\" (username, email, password, \"createdAt\") VALUES (?, ?, ?, ?) RETURNING id";
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = today.format(formatter);

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, formattedDate);


            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    public void addPreferenceToUser(User user, String preferenceName) throws DbException {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO \"UserPreference\" (user_id, preference_id) VALUES (?, ?) RETURNING id")) {
//            Long userId = getUserIdByName(userName);
            Preference preference = getPreferenceByName(preferenceName);

            Long userId = user.getId();
            Long preferenceId = preference.getId();
            statement.setLong(1, userId);
            statement.setLong(2, preferenceId);
            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                Long generatedId = resultSet.getLong("id");
//                UserPreference userPreference = new UserPreference();
//                userPreference.setUser(user);
//                userPreference.setPreference(preference);
//                userPreference.setId(generatedId);
////
//            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }



    private Preference getPreferenceByName(String preferenceName) throws DbException {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT \"id\" FROM \"Preference\" WHERE \"preferenceName\" = ?")) {
            statement.setString(1, preferenceName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Preference preference = new Preference();
                Long id = resultSet.getLong("id");
                preference.setId(id);
                preference.setPreferenceName(preferenceName);

                return preference;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e.getMessage());
        }
        return null;
    }

//    public User getUserByConfirmationToken(String confirmationToken) throws DbException {
//        try (Connection connection = connectionProvider.getInstance().getConnection();
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"User\" WHERE \"confirmationToken\" = ?")) {
//            statement.setString(1, confirmationToken);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                User user = new User();
//                user.setId(resultSet.getLong("id"));
//                user.setUsername(resultSet.getString("username"));
//                user.setEmail(resultSet.getString("email"));
//                user.setPassword(resultSet.getString("password"));
//                user.setConfirmationToken(resultSet.getString("confirmationToken"));
//
//                return user;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DbException(e.getMessage());
//        }
//        return null;
//    }


    public boolean isUsernameExists(String username) throws DbException {
        String sql = "SELECT COUNT(*) FROM \"User\" WHERE username = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return false;
    }

    public boolean isEmailExists(String email) throws DbException {
        String sql = "SELECT COUNT(*) FROM \"User\" WHERE email = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return false;
    }

    public User findByEmail(String email) throws DbException {
        User user = null;
        String sql = "SELECT * FROM \"User\" WHERE email = ?";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            try(ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
//                    user.setCreatedAt(resultSet.getString("createdAt"));

                }
            }
        } catch (SQLException e) {
            throw new DbException("Ошибка при выполнении запроса findByEmail", e);
        }
        return user;
    }

    public User findById(Long id) {
        String sql = "SELECT * FROM \"User\" WHERE id = ?";
        User user = null;

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setAvatar(resultSet.getBlob("avatar"));
                user.setCreatedAt(resultSet.getString("createdAt"));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User updateUser(User updatedUser) {
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE \"User\" SET username = ?, email = ? WHERE id = ?")) {
//            preparedStatement.setBlob(1, updatedUser.getAvatar());
            preparedStatement.setString(1, updatedUser.getUsername());
            preparedStatement.setString(2, updatedUser.getEmail());
            preparedStatement.setLong(3, updatedUser.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return updatedUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




//    public void updateUser(User user) throws DbException {
//
//        try (Connection connection = connectionProvider.getInstance().getConnection()) {
//            String sql = "UPDATE \"User\" SET \"confirmationToken\" = ? WHERE \"email\" = ?";
//            PreparedStatement statement = connection.prepareStatement(sql);
//
//            statement.setString(1, user.getConfirmationToken());
//            statement.setString(2, user.getEmail());
//
//            statement.executeUpdate();
//
//            statement.close();
//        } catch (SQLException e) {
//            throw new DbException("Ошибка обновления пользователя", e);
//        }
//    }


//    User findById(Long id);
//
//    User findByEmail(String email);
//
//    User findByUsername(String username);
//
//    void delete(User user);
//
//    void update(User user);
//
//    List<User> findAll();

}


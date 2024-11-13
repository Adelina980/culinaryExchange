package org.example.dao;


import org.example.entity.Preference;
import org.example.entity.User;
import org.example.util.ConnectionProvider;
import org.example.util.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PreferenceDao {
    ConnectionProvider connectionProvider;

    public PreferenceDao() {
        try {
            this.connectionProvider = ConnectionProvider.getInstance();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public List<String> getPreferences() {
        List<String> preferences = new ArrayList<>();

        try (Connection connection = ConnectionProvider.getInstance().getConnection()) {
            String query = "SELECT \"preferenceName\" FROM \"Preference\"";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                preferences.add(resultSet.getString("preferenceName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return preferences;
    }

    public Preference findById(Long id) {
        String sql = "SELECT * FROM \"Preference\" WHERE id = ?";
        Preference preference = null;

        try (Connection connection = connectionProvider.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                preference = new Preference();
                preference.setId(resultSet.getLong("id"));
                preference.setPreferenceName(resultSet.getString("preferenceName"));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DbException e) {
            throw new RuntimeException(e);
        }

        return preference;
    }

    public Preference findByPreferenceName(String preferenceName) {
        String sql = "SELECT * FROM \"Preference\" WHERE preferenceName = ?";
        Preference preference = null;

        try (Connection connection = connectionProvider.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, preferenceName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                preference = new Preference();
                preference.setId(resultSet.getLong("id"));
                preference.setPreferenceName(resultSet.getString("preferenceName"));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DbException e) {
            throw new RuntimeException(e);
        }

        return preference;
    }

}


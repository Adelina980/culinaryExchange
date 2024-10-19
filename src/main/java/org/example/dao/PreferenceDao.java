package org.example.dao;


import org.example.util.ConnectionProvider;
import org.example.util.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PreferenceDao {

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

}


package com.example.jdbcmysqlfull;

import javafx.collections.ObservableList;

import java.sql.*;

public class Database {
    String url = "jdbc:mysql://localhost:3307/mydb";
    String user = "root";
    String password = "thegoatlevi123";

    public Connection getConnection() throws SQLException {
        String dbUrl = url;
        String username = user;
        String pass = password;
        return DriverManager.getConnection(dbUrl, username, pass);
    }
    public void createTable(String tableName, String[] cols, ObservableList<String> dataTypes) {
        try (Connection connection = getConnection()) {
            String useDb = "USE mydb";
            try (PreparedStatement usePrepStatement = connection.prepareStatement(useDb)) {
                usePrepStatement.execute();
            }
            StringBuilder createQuery = new StringBuilder("CREATE TABLE " + tableName + " (");

            for (int i = 0; i < cols.length; i++) {
                createQuery.append(cols[i] + " ");
                switch (dataTypes.get(i)) {
                    case "INT":
                    case "DATE":
                        createQuery.append(dataTypes.get(i));
                        break;
                    case "VARCHAR":
                        createQuery.append(dataTypes.get(i) + "(50)");
                        break;
                    case "DECIMAL":
                        createQuery.append(dataTypes.get(i) + "(10, 10)");
                    default:
                        System.out.println("Invalid data type");
                        return;
                }
                if (i < cols.length - 1) {
                    createQuery.append(", ");
                }
            }
            createQuery.append(");");
            System.out.println("Generated Query: " + createQuery.toString());
            try (PreparedStatement preparedStatement = connection.prepareStatement(createQuery.toString())) {
                preparedStatement.executeUpdate();
                System.out.println("Table created successfully");
            } catch (SQLException e) {
                System.err.println("Error executing the query: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

}

package com.example.jdbcmysqlfull;

import java.sql.*;

public class Database {
    String url = "jdbc:mysql://localhost:3306/test";
    String user = "root";
    String password = "";
    public Connection getConnection() throws SQLException {
        String dbUrl = url;
        String username = user;
        String pass = password;
        return DriverManager.getConnection(dbUrl, username, pass);
    }
    public void createTable(String tableName) throws SQLException {
        Connection connection = getConnection();
        String createQuery = "CREATE TABLE " + tableName + "()";
    }
}

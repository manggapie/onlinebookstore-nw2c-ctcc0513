
package com.jasmineenriquez.casestudy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;
    private static final String DB_URL = "jdbc:mysql://localhost:8111/onlinebookstore";
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASS = "";
    
    public static Connection getConnection() {
        connection = null;
        try {
            Class.forName(DATABASE_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to the database!");
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        
        return null;
    }
    
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database!");
                connection = null; // Set connection to null after closing
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Checks if the database is connected
    public static boolean isDatabaseConnected() {
        try {
            Connection connection = getConnection();
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

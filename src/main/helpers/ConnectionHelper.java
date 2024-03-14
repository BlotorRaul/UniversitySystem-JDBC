package main.helpers;

import java.sql.*;

public class ConnectionHelper {

    private static final String url = "jdbc:mysql://localhost:3306/proiectfinal";
    private static final String user = "root";
    private static final String password = "parolaTa";

    private Connection connection;


    public Connection getConnection() throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return this.connection.prepareStatement(query);
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }

}

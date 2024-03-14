package main.forms;

import main.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ParasesteStudCurs extends  JDialog{
    private User user;
    final String DB_URL = "jdbc:mysql://localhost:3306/proiectfinal";
    final String DB_USERNAME = "root";
    final String DB_PASSWORD = "parolaTa";
    private JPanel JPanelParasesteStudCurs;

    public ParasesteStudCurs(User user)throws SQLException {
        this.user = user;
        setTitle("Profesor");
        setContentPane(JPanelParasesteStudCurs);
        setMinimumSize(new Dimension(500,500));
        setSize(1200,700);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

        }catch (SQLException e) {
        e.printStackTrace();
    }
    }
}

package main.forms;

import main.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class VizualizareCatalog extends JDialog{
    private User user;
    final String DB_URL = "jdbc:mysql://localhost:3306/proiectfinal";
    final String DB_USERNAME = "root";
    final String DB_PASSWORD = "parolaTa";
    private JPanel JPanelVizualizareCatalog;

    public VizualizareCatalog(User user) throws SQLException {
        this.user = user;
        setTitle("Profesor");
        setContentPane(JPanelVizualizareCatalog);
        setMinimumSize(new Dimension(500,500));
        setSize(1200,700);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

    }
}

package main.forms;

import main.User;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Activitati extends JDialog {
    private JTextArea textArea1;
    private JPanel JPanelActivitati;
    final String DB_URL = "jdbc:mysql://localhost:3306/proiectfinal";
    final String DB_USERNAME = "root";
    final String DB_PASSWORD = "parolaTa";
    private User user;

    public Activitati(User user) throws SQLException {
        this.user = user;
        setTitle("Profesor");
        setContentPane(JPanelActivitati);
        setMinimumSize(new Dimension(500,500));
        setSize(1200,700);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            // Creează un obiect Statement pentru a executa interogarea
            Statement statement = connection.createStatement();

            // Execută interogarea
            ResultSet resultSet = statement.executeQuery("SELECT * FROM fiecareSaptamana");

            // Iterează prin rezultate și afișează-le
            while (resultSet.next()) {
                String zi = resultSet.getString("ziua");
                java.sql.Time oraInceput = resultSet.getTime("oraInceput");
                java.sql.Time oraSfarsit = resultSet.getTime("oraSfarsit");
                String numeMaterie = resultSet.getString("numeMaterie");
                String numeActivitate = resultSet.getString("numeActivitate");

                textArea1.append("Ziua: " + zi + ", Ora început: " + oraInceput + ", Ora sfârșit: "
                        + oraSfarsit + ", Materie: " + numeMaterie + ", Activitate: " + numeActivitate + "\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}

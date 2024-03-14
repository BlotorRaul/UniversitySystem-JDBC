package main.forms;

import main.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InscriereStudMaterie extends JDialog {
    private User user;
    final String DB_URL = "jdbc:mysql://localhost:3306/proiectfinal";
    final String DB_USERNAME = "root";
    final String DB_PASSWORD = "parolaTa";
    private JPanel JPanelInscriereStudMaterie;
    private JComboBox cbListaMateriiBun;
    private JComboBox cbProfesoriDisponibili;
    private JTextField tfAnInvatamant;
    private JButton btnInscriereStud;
    private JButton btnPopulareTabelStud;
    private JPanel JPanelContinuare;
    private JLabel labelMaterie;
    private JLabel labelProfDIsponibili;
    private JTextField tfOreSustinute;
    private JPanel JPanelPrimaParte;
    private JTextArea textArea1;
    private Integer idMaterie = -1;
    private Integer idUser = -1;
    private Integer idStudent = -1;

    public InscriereStudMaterie(User user) throws SQLException{
        this.user = user;
        setTitle("Profesor");
        setContentPane(JPanelInscriereStudMaterie);
        setMinimumSize(new Dimension(500,500));
        setSize(1200,700);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        loadDataIntoComboBox();


        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            //********obtin idUser din user.getUsername
            CallableStatement callableStatement1 = connection.prepareCall("{call getidbyuser(?)}");
            callableStatement1.setString(1,user.getUsername());
            ResultSet resultSet1 = callableStatement1.executeQuery();
            resultSet1.next();

            idUser = resultSet1.getInt("idUser");
            System.out.println(idUser);


        }catch (SQLException e) {
        e.printStackTrace();
        }
        System.out.println(idStudent);
        //FAC PARTEA DE INVIZIBIL A COMPONENTELOR
        JPanelContinuare.setVisible(false);
        btnPopulareTabelStud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //vizibil restu continutului
                JPanelContinuare.setVisible(true);
                String query = "INSERT INTO student (idUser, oreSustinute, anInvatamant) VALUES (?, ?, ?)";
                String query2 = "SELECT idStudent FROM student WHERE idUser = ?";
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                    //******** fac inserare in tabelul student ptr idUser
                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setInt(1, idUser);
                    preparedStatement.setInt(2, Integer.parseInt(tfOreSustinute.getText()));
                    preparedStatement.setInt(3, Integer.parseInt(tfAnInvatamant.getText()));

                    // Executarea interogării
                    preparedStatement.executeUpdate();

                    System.out.println("Inserare reușită!");
                    //idStudent
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                    preparedStatement2.setInt(1, idUser);
                    ResultSet resultSet = preparedStatement2.executeQuery();
                    resultSet.next();
                    idStudent = resultSet.getInt("idStudent");
                    System.out.println(idStudent);

                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
                JPanelPrimaParte.setVisible(false);
            }
        });
        btnInscriereStud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idMaterie = cbListaMateriiBun.getSelectedIndex() + 1;
                System.out.println(idMaterie);
                System.out.println(idStudent);

            }
        });
    //primul combobox face sa se trigger al doilea combobox
        cbListaMateriiBun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                    // Apelarea procedurii stocate
                    String callProcedure = "{CALL SelectDistinctUsernamesForMaterie(?)}";
                    try (CallableStatement callableStatement = connection.prepareCall(callProcedure)) {
                        // Setarea valorii pentru parametrul procedurii
                        callableStatement.setString(1, "POO");

                        // Executarea procedurii
                        callableStatement.execute();

                        // Obținerea rezultatelor, dacă este necesar
                        ResultSet resultSet = callableStatement.getResultSet();
                        while (resultSet.next()) {
                            String username = resultSet.getString("username");
                            cbProfesoriDisponibili.addItem(username);
                            //textArea1.append(username);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Eroare la apelare procedură: " + ex.getMessage());
                }
                cbProfesoriDisponibili.removeAll();
            }
        });
    }

    private void loadDataIntoComboBox() {


        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT numeMaterie FROM materie";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String materie = resultSet.getString("numeMaterie");
                    cbListaMateriiBun.addItem(materie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package main.forms;

import main.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AsignareActivitati extends JDialog {
    private User user;
    private JPanel JPanelAsignareActivitati;
    private JComboBox cbActivitate;
    private JTextField tfPondereNota;
    private JTextField tfNrParticipanti;
    private JButton btnAdauga;
    private JButton button2;
    private JTextArea textArea1;
    private JComboBox cbCursuri;
    final String DB_URL = "jdbc:mysql://localhost:3306/proiectfinal";
    final String DB_USERNAME = "root";
    final String DB_PASSWORD = "parolaTa";
    private String optiuneCurs;
    private String optiuneActivitate;
    private Integer idProfesor = -1;
    private Integer idUser = -1;
    private Integer idMaterie = -1;
    private Integer idActivitate = -1;

    public AsignareActivitati(User user)throws SQLException {
        this.user =user;
        setTitle("Profesor");
        setContentPane(JPanelAsignareActivitati);
        setMinimumSize(new Dimension(500,500));
        setSize(1200,700);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);



        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            CallableStatement callableStatement1 = connection.prepareCall("{call getidbyuser(?)}");
            callableStatement1.setString(1, user.getUsername());
            ResultSet resultSet1 = callableStatement1.executeQuery();
            resultSet1.next();
            idUser = resultSet1.getInt("idUser");

            String query = "SELECT idProfesor FROM profesor WHERE idUser = ?";
            //OBTIN IdProfesor din IdUser
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, resultSet1.getInt("idUser"));
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            idProfesor = resultSet.getInt("idProfesor")+1;
                //************* umplu combobox(cbCursuri) cu materii
            CallableStatement callableStatement2 = connection.prepareCall("call SelectMateriiByProfesor(?)");
            callableStatement2.setInt(1, idProfesor);
            ResultSet resultSet2 = callableStatement2.executeQuery();
            cbCursuri.removeAllItems();
            while (resultSet2.next()) {
                String numeMaterie = resultSet2.getString("numeMaterie");
                cbCursuri.addItem(numeMaterie);
            }
            //************* umplu combobox(cbActivitate) cu activitati
            String query2 = "SELECT numeActivitate from activitate";
            PreparedStatement pstmt2 = connection.prepareStatement(query2);
            ResultSet resultSet3 = pstmt2.executeQuery();

            while (resultSet3.next()) {
                String numeActivitate = resultSet3.getString("numeActivitate");
                cbActivitate.addItem(numeActivitate);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }


        btnAdauga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optiuneCurs =(String)cbCursuri.getSelectedItem();
                optiuneActivitate= (String) cbActivitate.getSelectedItem();
                //System.out.println(optiuneCurs);
                System.out.println(optiuneActivitate);
                System.out.println(idProfesor);
                System.out.println(idUser);
                String query = "SELECT tipActivitate FROM activitate WHERE numeActivitate = ?";
                String insertQuery = "INSERT INTO activitati_materie (tipActivitate, idMaterie, idProfesor, nrParticipantiMax, pondereNota) VALUES (?, ?, ?, ?, ?)";
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                    String callProcedure = "{call GetIdMaterieByNume(?)}";

                    CallableStatement callableStatement = connection.prepareCall(callProcedure);
                        callableStatement.setString(1, optiuneCurs);


                    ResultSet resultSet3 = callableStatement.executeQuery();
                    resultSet3.next();
                    idMaterie = resultSet3.getInt("idMaterie");
                    System.out.println("Id-ul materiei este: " + idMaterie);

                    PreparedStatement pstmt = connection.prepareStatement(query);
                    pstmt.setString(1, optiuneActivitate);
                    ResultSet resultSet = pstmt.executeQuery();
                    resultSet.next();
                    idActivitate = resultSet.getInt("tipActivitate");


                    System.out.println("Activitatea este " + idActivitate);

                    PreparedStatement pstmt2 = connection.prepareStatement(insertQuery);
                    pstmt2.setInt(1, idActivitate);
                    pstmt2.setInt(2, idMaterie);
                    pstmt2.setInt(3, idProfesor);
                    pstmt2.setInt(4, Integer.parseInt(tfNrParticipanti.getText() ));
                    pstmt2.setInt(5, Integer.parseInt(tfPondereNota.getText() ));
                    int rowsAffected = pstmt2.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Inserare reușită!");
                    } else {
                        System.out.println("Inserare eșuată!");
                    }
                    //textArea scris
                    String textCurent = textArea1.getText();
                    String newText = textCurent + "\nProfesorul " + user.getUsername() +
                            "\nla materia " +(String)cbCursuri.getSelectedItem()+  " cu activitatea " +(String) cbActivitate.getSelectedItem()+
                            "\ndoreste o pondere de " + tfPondereNota.getText()+
                            "\nsi se pot inscrie "+ tfNrParticipanti.getText() + " studenti.\n";
                    textArea1.setText(newText);
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }



            }
        });
    }
}

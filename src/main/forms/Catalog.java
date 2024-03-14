package main.forms;

import main.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Catalog extends JDialog{
    private User user;
    private JComboBox cbMaterie;
    private JTextField tfNumeStudent;
    private JTextField tfNota;
    private JButton btnSelectNota;
    private JTextArea textArea1;
    private JPanel JPanelCatalog;
    private JComboBox cbActivitate;
    final String DB_URL = "jdbc:mysql://localhost:3306/proiectfinal";
    final String DB_USERNAME = "root";
    final String DB_PASSWORD = "parolaTa";
    private Integer idProfesor = -1;
    private Integer idUser = -1;
    private Integer idUserStudent = -1;
    private Integer idStudent = -1;
    private Integer idMaterie = -1;
    private String numeActivitateSelectata = "";
    private String materieSelectata;
    private Integer idActivitate = -1;
    private Integer idActivitateMaterieTabel = -1;

    public Catalog(User user) throws SQLException {
        this.user = user;
        this.user =user;
        setTitle("Profesor");
        setContentPane(JPanelCatalog);
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
            cbMaterie.removeAllItems();
            while (resultSet2.next()) {
                String numeMaterie = resultSet2.getString("numeMaterie");
                cbMaterie.addItem(numeMaterie);
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
        btnSelectNota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                materieSelectata = (String) cbMaterie.getSelectedItem();
                numeActivitateSelectata = (String) cbActivitate.getSelectedItem();
                idActivitate =cbActivitate.getSelectedIndex() + 1; //incepe de la 0 dar mie imi trebuie de la 1
                System.out.println(idActivitate);
                System.out.println(materieSelectata);
                System.out.println(numeActivitateSelectata);
                System.out.println(tfNota.getText());//trebuie convertit din string in float
                String storedProcedureCall = "{CALL getStudentIdByUserId(?)}";
                String callProcedure = "{CALL GetIdMaterieByNume(?)}";
                String storedProcedureCall1 = "{CALL GetIdActivitateMaterie(?, ?, ?)}";
                String insertQuery = "INSERT INTO note (idActivitateMaterie, idStudent, nota, numeActivitate) VALUES (?, ?, ?, ?)";
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                    //************* din usernameStudent ii iau idUser
                CallableStatement callableStatement3 = connection.prepareCall("{call getidbyuser(?)}");
                callableStatement3.setString(1, tfNumeStudent.getText());
                ResultSet resultSet4 = callableStatement3.executeQuery();
                resultSet4.next();
                idUserStudent = resultSet4.getInt("idUser");
                System.out.println(idUserStudent);

                //************* din idUser al studentului obtin idStudent (andreineagu are idUser = 3 dar idStudent = 2)
                CallableStatement callableStatement = connection.prepareCall(storedProcedureCall);
                callableStatement.setInt(1, idUserStudent);
                ResultSet resultSet = callableStatement.executeQuery();
                resultSet.next();
                System.out.println(resultSet.getInt("idStudent"));
                    System.out.println(numeActivitateSelectata);
                //************* din materia selectata obtin idMaterie
                CallableStatement callableStatement4 = connection.prepareCall(callProcedure);
                callableStatement4.setString(1, materieSelectata);
                    ResultSet resultSet2 = callableStatement4.executeQuery();
                    resultSet2.next();
                idMaterie = resultSet2.getInt("idMaterie");
                    System.out.println("Id-ul materiei selectate este: " + idMaterie);

                //************* din idMaterie, idProfesor, tipActivitate(idActivitate) obtin idActivitateMaterie
                    System.out.println("--------------");
                    System.out.println(idMaterie);
                    System.out.println(idProfesor);
                    System.out.println(idActivitate);
                    CallableStatement callableStatement5 = connection.prepareCall(storedProcedureCall1);
                    callableStatement5.setInt(1,idMaterie);
                    callableStatement5.setInt(2,idProfesor);
                    callableStatement5.setInt(3,idActivitate);
                    ResultSet resultSet1 = callableStatement5.executeQuery();
                    resultSet1.next();
                    idActivitateMaterieTabel = resultSet1.getInt("idActivitateMaterie");
                    System.out.println(idActivitateMaterieTabel);

                //*************inserez in tabel note cele 4 campuri
                    PreparedStatement pstmt = connection.prepareStatement(insertQuery);
                    pstmt.setInt(1, idActivitateMaterieTabel); // înlocuiește cu valoarea specifică pentru idActivitateMaterie
                    pstmt.setInt(2, idStudent); // înlocuiește cu valoarea specifică pentru idStudent
                    pstmt.setFloat(3, Float.parseFloat(tfNota.getText())); // înlocuiește cu valoarea specifică pentru nota
                    pstmt.setString(4, numeActivitateSelectata);
                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Inserare reușită!");

                        // Adăugare text frumos în textArea1
                        String textCurent = textArea1.getText();
                        String newText = textCurent + "\nNota pentru activitatea " + numeActivitateSelectata +
                                "\na fost adăugată cu succes pentru studentul  " + tfNumeStudent.getText()+
                                ".\nNota acordată: " + Float.parseFloat(tfNota.getText()) + "\n";
                        textArea1.setText(newText);
                    } else {
                        System.out.println("Inserare eșuată!");
                    }

                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}

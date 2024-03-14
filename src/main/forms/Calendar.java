package main.forms;

import main.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Calendar extends JDialog{
    private User user;
    private JTextField tfMaterie;
    private JTextField tfActivitate;
    private JTextField tfZiua;
    private JTextField tfOraInceput;
    private JTextField tfOraTerminare;
    private JButton btnSalveaza;
    private JPanel JPanelCalendar;
    private JComboBox cbCursuri;
    private JComboBox cbActivitate;
    private JComboBox cbZileSaptamanii;
    final String DB_URL = "jdbc:mysql://localhost:3306/proiectfinal";
    final String DB_USERNAME = "root";
    final String DB_PASSWORD = "parolaTa";
    private Integer idProfesor = -1;
    private Integer idUser = -1;
    private String curs;
    private String activitate;
    private String ziDinSapt;

    public Calendar(User user) throws SQLException {
        this.user = user;
        setTitle("Profesor");
        setContentPane(JPanelCalendar);
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

            String[] zileSaptamanii = {"Luni", "Marti", "Miercuri", "Joi", "Vineri"};
            for(String zi: zileSaptamanii){
                cbZileSaptamanii.addItem(zi);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        btnSalveaza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curs = (String)cbCursuri.getSelectedItem();
                activitate= (String)cbActivitate.getSelectedItem();
                ziDinSapt= (String)cbZileSaptamanii.getSelectedItem();
                System.out.println(curs);
                System.out.println(activitate);
                System.out.println(ziDinSapt);

                String callProcedure = "{call InserareFiecareSaptamana(?, ?, ?, ?, ?)}";
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                    CallableStatement callableStatement = connection.prepareCall(callProcedure);
                    callableStatement.setString(1, ziDinSapt);
                    callableStatement.setTime(2, Time.valueOf(tfOraInceput.getText()));
                    callableStatement.setTime(3, Time.valueOf(tfOraTerminare.getText()));
                    callableStatement.setString(4, curs);
                    callableStatement.setString(5, activitate);
                    callableStatement.executeUpdate();

                    System.out.println("Inserare reușită!");
                }catch (SQLException x) {
                    x.printStackTrace();
                }
            }
        });
    }
}

package main.forms;

import main.User;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class datePersonaleProf extends  JDialog {
    private User user;
    private JTextField numePrenume;
    private JTextField cnp;
    private JTextField adresa;
    private JTextField numarTelefon;
    private JTextField Email;
    private JTextField contIban;
    private JTextField numarContract;
    private JPanel JPaneldateProf;

    public datePersonaleProf(User user) throws SQLException {
        this.user =user;
        setTitle("Profesor");
        setContentPane(JPaneldateProf);
        setMinimumSize(new Dimension(500,500));
        setSize(1200,700);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

       final String DB_URL = "jdbc:mysql://localhost:3306/proiectfinal";
        final String DB_USERNAME = "root";
        final String DB_PASSWORD = "parolaTa";

        Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        CallableStatement callableStatement1 = con.prepareCall("{call getidbyuser(?)}");
        callableStatement1.setString(1,user.getUsername());
        ResultSet resultSet1 = callableStatement1.executeQuery();
        resultSet1.next();

        user.setId(resultSet1.getString("idUser"));
        System.out.println(user.getId());
        CallableStatement callableStatement2 = con.prepareCall("{call getuserbyid(?)}");
        //ptr parametrii
        callableStatement2.setInt(1,resultSet1.getInt("idUser"));
        ResultSet resultSet =callableStatement2.executeQuery();
        resultSet.next();

            numePrenume.setText(resultSet.getString("nume")+" " + resultSet.getString("prenume"));
            cnp.setText(resultSet.getString("CNP"));
            adresa.setText(resultSet.getString("adresa"));
            numarTelefon.setText(resultSet.getString("numarTelefon"));
            Email.setText(resultSet.getString("email"));
            contIban.setText(resultSet.getString("IBAN"));
            numarContract.setText(resultSet.getString("numarContract"));





        callableStatement1.close();
        callableStatement2.close();
        con.close();
    }


}

package main.forms;

import main.User;
import main.helpers.ConnectionHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdministratorForm extends JFrame{
    private JPanel AdministratorPanel;
    private JLabel lbAdmin;
    private JButton btnCancel;
    public AdministratorForm(JFrame paren){
        setTitle("Administrator");
        setContentPane(AdministratorPanel);
        setMinimumSize(new Dimension(500,500));
        setSize(1200,700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        boolean hasRegistredUsers = connectToDatabase();
        if(hasRegistredUsers){
            //show login form
            LoginForm loginForm = new LoginForm(this);
            User user = loginForm.user;

            if(user != null)
            {
                lbAdmin.setText("main.User: " + user.getNume());
                setLocationRelativeTo(null);
//                setVisible(true);
            }
            else {
                dispose();
            }
        }
        else {
            //show Registration form
            RegistrationForm registrationForm = new RegistrationForm(this);
            User user = registrationForm.user;

            if(user != null){
                lbAdmin.setText("main.User: " + user.getNume());
                setLocationRelativeTo(null);
//                setVisible(true);
            }
            else{
                dispose();
            }
        }
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationForm registrationForm = new RegistrationForm(AdministratorForm.this);
                User user = registrationForm.user;


                if(user != null)
                {
                    JOptionPane.showMessageDialog(AdministratorForm.this,
                            "New user: "+ user.getNume(),
                            "Successsful Registration",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                dispose();
                WelcomeForm welcomeForm = new WelcomeForm(null);
                welcomeForm.setVisible(true);
            }
        });
    }

    private boolean connectToDatabase() {
        boolean hasRegistredUsers = false;

        ConnectionHelper connectionHelper = new ConnectionHelper();

        try {
            Connection connection = connectionHelper.getConnection();
            //create a query
            String q = "select count(*) from users";
            PreparedStatement preparedStatement = connectionHelper.prepareStatement(q);


            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                int numUsers = resultSet.getInt(1);
                if(numUsers > 0){
                    hasRegistredUsers = true;
                }
            }

            preparedStatement.close();
            connectionHelper.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hasRegistredUsers;
    }

    public static void main(String[] args) {
//        AdministratorForm administrator = new AdministratorForm(null);
    }
}

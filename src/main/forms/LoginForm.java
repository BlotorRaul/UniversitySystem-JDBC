package main.forms;

import main.User;
import main.helpers.ConnectionHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginForm extends JDialog {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnEnter;
    private JButton btnCancel;
    private JPanel loginPanel;

    public LoginForm(JFrame parent)
    {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450,500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfUsername.getText();
                String password = String.valueOf(pfPassword.getPassword());

                user = getAuthentificatedUser(username, password);
                System.out.println(user.getNume());
                if(user != null){
                    if (user.getNivelAccesibilitate().equals("Admin")) {
                        AdministratorForm administratorForm = new AdministratorForm(null);
                        dispose();
                        administratorForm.setVisible(true);

                    } else if (user.getNivelAccesibilitate().equals("Profesor")) {
                        ProfesorForm profesorForm = new ProfesorForm(null,user);
                        dispose();
                        profesorForm.setVisible(true);
                    } else if (user.getNivelAccesibilitate().equals("Student")) {
                        StudentForm studentForm = new StudentForm(null,user);
                        dispose();
                        studentForm.setVisible(true);
                    } else {
                        try {
                            throw new Exception(new Exception());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
//                    LoginForm loginForm = new LoginForm(null);
//                    loginForm.setVisible(true);

                }
                else{
                    JOptionPane.showMessageDialog(LoginForm.this,"Eroare de logare a user","Mai incearca",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                WelcomeForm welcomeForm =  new WelcomeForm(null);
                welcomeForm.setVisible(true);
            }
        });

//        setVisible(true);
    }

    public User user;
    private User getAuthentificatedUser(String username, String password) {
        User user = null;

        Connection connection;
        ConnectionHelper connectionHelper = new ConnectionHelper();
        try {
            connection = connectionHelper.getConnection();
            //create a query
            String q = "Select * from users where username =? and parola=?;";

            PreparedStatement pstmt = connectionHelper.prepareStatement(q);


            //get the preparedStatement object

            //PreparedStatement pstmt = connection.prepareStatement(q);

            //set the values to query
            pstmt.setString(1,username);
            pstmt.setString(2,password);

            ResultSet resultSet = pstmt.executeQuery();

            //System.out.println(pstmt);
            //System.out.println(resultSet);
            if(resultSet.next())
            {
                user = new User();
                user.setNivelAccesibilitate(resultSet.getString("tipUser"));
                user.setCnp(resultSet.getString("CNP"));
                user.setNume(resultSet.getString("nume"));
                user.setPrenume(resultSet.getString("prenume"));
                user.setAdresa(resultSet.getString("adresa"));
                user.setNrTelefon(resultSet.getString("numarTelefon"));
                user.setEmail(resultSet.getString("email"));
                user.setIban(resultSet.getString("IBAN"));
                user.setNrContract(resultSet.getString("numarContract"));
                user.setUsername(resultSet.getString("username"));
                user.setParola(resultSet.getString("parola"));
            }
            connectionHelper.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return user;
    }

    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm(null);
        User user = loginForm.user;

        if(user != null)
        {
            System.out.println("Te-ai autentificat cu succes!");
            System.out.println("Nickname este: " + user.getUsername());
            System.out.println("Parola este: " + user.getParola());
            System.out.println("Nivelul de autentificare este: " + user.getNivelAccesibilitate());
        }
        else
            System.out.println("Autentificarea a fost anulata!");
    }
}

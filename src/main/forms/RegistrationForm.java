package main.forms;

import main.User;
import main.helpers.ConnectionHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationForm extends JDialog {
    private JTextField tfNume;
    private JTextField tfNickname;
    private JTextField tfPrenume;
    private JTextField tfEmail;
    private JTextField tfCNP;
    private JTextField tfIBAN;
    private JTextField tfNumarTelefon;
    private JTextField tfNumarContract;
    private JComboBox cbNivelAccesibilitate;
    private JTextField tfAdresa;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPasswordField pfParola;
    private JPanel registerPanel;
    private String cbNivelAccesibilitateCopie;

    public RegistrationForm(JFrame parent) {
        super(parent);
        setTitle("Creeaza un nou cont");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(600, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //COMBO BOX
        String[] accesibilitate = {"Admin", "Profesor", "Student"};
        setComboBoxModel(accesibilitate);
        cbNivelAccesibilitate.addActionListener(this::actionPerformed);
        //BUTOANE
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = registerUser();
                if (user != null) {
                    if (user.getNivelAccesibilitate().equals("Admin")) {
                        AdministratorForm administratorForm = new AdministratorForm(null);
                        dispose();
                        administratorForm.setVisible(true);

                    } else if (user.getNivelAccesibilitate().equals("Profesor")) {
                        ProfesorForm profesorForm = new ProfesorForm(null);
                        dispose();
                        profesorForm.setVisible(true);
                    } else if (user.getNivelAccesibilitate().equals("Student")) {
                        StudentForm studentForm = new StudentForm(null);
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
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                WelcomeForm welcomeForm = new WelcomeForm(null);
                welcomeForm.setVisible(true);
            }
        });

//        setVisible(true);
    }

    private void setComboBoxModel(String[] values) {
        // Crearea unui model pentru ComboBox folosind array-ul
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(values);
        // Setarea noului model pentru JComboBox
        cbNivelAccesibilitate.setModel(comboBoxModel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cbNivelAccesibilitate) {

            cbNivelAccesibilitateCopie = (String) cbNivelAccesibilitate.getSelectedItem();
            System.out.println("Valoarea selectată: " + cbNivelAccesibilitateCopie);
        }
    }

    // Metoda pentru a obține valoarea selectată
    public String getNivelAccesibilitate() {
        return cbNivelAccesibilitateCopie;
    }

    private User registerUser() {
        String nume = tfNume.getText();
        String prenume = tfPrenume.getText();
        String username = tfNickname.getText();//Nickname
        String email = tfEmail.getText();
        String parola = String.valueOf(pfParola.getPassword());
        String cnp = tfCNP.getText();
        String iban = tfIBAN.getText();
        String nrContract = tfNumarContract.getText();
        String nrTelefon = tfNumarTelefon.getText();
        String nivelAccesibilitate = getNivelAccesibilitate();
        String adresa = tfAdresa.getText();

        if (nume.isEmpty() || prenume.isEmpty() || username.isEmpty() || email.isEmpty() || parola.isEmpty() || cnp.isEmpty() ||
                iban.isEmpty() || nrContract.isEmpty() || nrTelefon.isEmpty() || adresa.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Au ramas campuri goale!",
                    "Incearca din nou!",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        user = addUserToDatabase(nume, prenume, username, email, parola, cnp, iban, nrContract, nrTelefon, nivelAccesibilitate, adresa);
        if (user != null) {
            dispose();
            return user;
        } else {
            JOptionPane.showMessageDialog(this, "Eroare de inregistrare a user", "Mai incearca", JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }

    public User user;

    private User addUserToDatabase(String nume, String prenume, String username, String email, String parola, String cnp, String iban, String nrContract, String nrTelefon, String nivelAccesibilitate, String adresa) {
        User user = null;

        ConnectionHelper connectionHelper = new ConnectionHelper();
        Connection connection;
        try {
            connection = connectionHelper.getConnection();
            //create a query
            String q = "INSERT INTO users (tipUser, CNP, nume, prenume, adresa, numarTelefon, email, IBAN, numarContract, username, parola)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);\n";
            //get the preparedStatement object

            PreparedStatement pstmt = connectionHelper.prepareStatement(q);

            //set the values to query
            pstmt.setString(1, nivelAccesibilitate);
            pstmt.setString(2, cnp);
            pstmt.setString(3, nume);
            pstmt.setString(4, prenume);
            pstmt.setString(5, adresa);
            pstmt.setString(6, nrTelefon);
            pstmt.setString(7, email);
            pstmt.setString(8, iban);
            pstmt.setString(9, nrContract);
            pstmt.setString(10, username);
            pstmt.setString(11, parola);

            int addedRows = pstmt.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.setNivelAccesibilitate(nivelAccesibilitate);
                user.setCnp(cnp);
                user.setNume(nume);
                user.setPrenume(prenume);
                user.setAdresa(adresa);
                user.setNrTelefon(nrTelefon);
                user.setEmail(email);
                user.setIban(iban);
                user.setNrContract(nrContract);
                user.setUsername(username);
                user.setParola(parola);
            }
            System.out.println("inserted...");
            connectionHelper.closeConnection();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public static void main(String[] args) {
        RegistrationForm myform = new RegistrationForm(null);
        User user = myform.user;
        if (user != null)
            System.out.println(user.getNume() + " s-a inregistrat cu succes!");
        else
            System.out.println("Inregistrarea a fost anulata!");


    }

}

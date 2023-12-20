package main.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeForm extends JDialog{
    private JButton btnRegistration;
    private JButton btnLogin;
    private JPanel panelWelcome;

    public WelcomeForm(JFrame parent) {

        super(parent);
        setTitle("WELCOME");
        setContentPane(panelWelcome);
        setMinimumSize(new Dimension(600, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnRegistration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationForm registrationForm = new RegistrationForm(null);
                dispose();
                registrationForm.setVisible(true);
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginForm loginForm = new LoginForm(null);
                dispose();
                loginForm.setVisible(true);
            }
        });

    }
}

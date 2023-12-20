package main.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentForm extends JDialog{
    private JButton btnCancel;
    private JPanel StudentPanel;
    private JList list1;

    public StudentForm(JFrame parent) {
        setTitle("Student");
        setContentPane(StudentPanel);
        setMinimumSize(new Dimension(500,500));
        setSize(1200,700);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                WelcomeForm welcomeForm = new WelcomeForm(null);
                welcomeForm.setVisible(true);
            }
        });
    }
}

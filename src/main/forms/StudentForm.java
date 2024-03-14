package main.forms;

import main.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class StudentForm extends JDialog{
    private User user;
    private JButton btnCancel;
    private JPanel StudentPanel;
    private JList list1;
    private JButton btndatePersonale;
    private JButton btnInscriere;
    private JButton btnStergeCurs;
    private JButton btnNote;

    public StudentForm(JFrame parent,User user) {
        this.user = user;
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
        btndatePersonale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datePersonaleProf dateProf = null;
                try {
                    dateProf = new datePersonaleProf(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dateProf.setVisible(true);
            }
        });
        btnInscriere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InscriereStudMaterie inscriereStudMaterie = null;
                try {
                    inscriereStudMaterie = new InscriereStudMaterie(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                inscriereStudMaterie.setVisible(true);
            }
        });
        btnStergeCurs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ParasesteStudCurs parasesteStudCurs = null;
                try {
                    parasesteStudCurs = new ParasesteStudCurs(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                parasesteStudCurs.setVisible(true);
            }
        });
        btnNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VizualizareCatalog vizualizareCatalog = null;
                try {
                    vizualizareCatalog = new VizualizareCatalog(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                vizualizareCatalog.setVisible(true);
            }
        });
    }
}

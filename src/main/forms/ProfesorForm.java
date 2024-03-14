package main.forms;

import main.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ProfesorForm extends JDialog{
    private User user;
    private JButton btnCancel;
    private JPanel ProfesorPanel;
    private JButton btnDatePersonale;
    private JButton btnCatalog;
    private JButton btnAsignareActivitati;
    private JButton btnActivitati;
    private JButton btnAsignareMaterie;
    private JButton btnCalendar;

    public ProfesorForm(JFrame parent,User user) {
        this.user = user;
        setTitle("Profesor");
        setContentPane(ProfesorPanel);
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

        btnDatePersonale.addActionListener(new ActionListener() {
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
        btnAsignareMaterie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InscriereProfMaterie inscriereProfMaterie = null;
                try {
                  inscriereProfMaterie = new InscriereProfMaterie(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                inscriereProfMaterie.setVisible(true);
            }
        });
        btnAsignareActivitati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AsignareActivitati asignareActivitati = null;
                try {
                   asignareActivitati = new AsignareActivitati(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                asignareActivitati.setVisible(true);
            }
        });
        btnCatalog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Catalog catalog = null;
                try{
                    catalog = new Catalog(user);
                }catch (SQLException ex)
                {
                    throw new RuntimeException(ex);
                }
                catalog.setVisible(true);
            }
        });
        btnCalendar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar calendar = null;
                try{
                    calendar = new Calendar(user);
                }catch (SQLException exx)
                {
                    throw new RuntimeException(exx);
                }
                calendar.setVisible(true);
            }
        });
        btnActivitati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Activitati activitati = null;
                try{
                    activitati = new Activitati(user);
                }catch (SQLException ex)
                {
                    throw new RuntimeException(ex);
                }
                activitati.setVisible(true);
            }
        });
    }
}

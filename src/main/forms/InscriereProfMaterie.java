package main.forms;

import main.ProfessorData;
import main.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InscriereProfMaterie extends JDialog {
    private User user;
    //private JComboBox cbListaMateriiBun;
    private JComboBox<String> cbListaMateriiBun;
    private JPanel JPanelInscrierelaMat;
    private JButton btnSalveaza;
    private JTextArea textArea1;
    private JTextField tfMinimOre;
    private JTextField tfMaximOre;
    final String DB_URL = "jdbc:mysql://localhost:3306/proiectfinal";
    final String DB_USERNAME = "root";
    final String DB_PASSWORD = "parolaTa";
    private ProfessorData profesorData;
    public InscriereProfMaterie(User user)throws SQLException {
        this.user =user;
        setTitle("Profesor");
        setContentPane(JPanelInscrierelaMat);
        setMinimumSize(new Dimension(500,500));
        setSize(1200,700);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        loadDataIntoComboBox();

        btnSalveaza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(user.getUsername());
                afiseazaOptiuneaSelectata();
            }
        });

    }
    private void loadDataIntoComboBox() {


        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT numeMaterie FROM materie";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String materie = resultSet.getString("numeMaterie");
                    cbListaMateriiBun.addItem(materie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void afiseazaOptiuneaSelectata() {
        String optiuneSelectata = (String) cbListaMateriiBun.getSelectedItem();
        Integer selectedItem = cbListaMateriiBun.getSelectedIndex();
        if (optiuneSelectata != null) {
            String textCurent = textArea1.getText();
            String newText = textCurent + "\nOptiunea selectată: " + optiuneSelectata +
                    "\nNumăr minim de ore: " + tfMinimOre.getText() +
                    "\nNumăr maxim de ore: " + tfMaximOre.getText()+ "\n";
            textArea1.setText(newText);

            System.out.println(selectedItem+1);
            updateDatabase(selectedItem+1);
        } else {
            textArea1.setText("Nicio optiune selectată");
        }
    }

    private void updateDatabase(Integer selectedItem) {
        try {
            // Conectează-te la baza de date
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);


            // Construiește și execută comanda SQL de actualizare
            String updateQuery = "INSERT INTO profesor (idUser, minimOre, maximOre, departament) VALUES(?,?,?,?)";
            String query = "SELECT idProfesor FROM profesor WHERE idUser = ?";
            String query2 = "INSERT INTO profesor_materie (idProfesor, idMaterie) VALUES (?, ?)";
            int idProfesor = -1;
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                //OBTIN ID PROFESORULUI CU CARE MA LOGHEZ
                CallableStatement callableStatement1 = connection.prepareCall("{call getidbyuser(?)}");
                callableStatement1.setString(1,user.getUsername());
                ResultSet resultSet1 = callableStatement1.executeQuery();
                resultSet1.next();

                System.out.println(resultSet1.getInt("idUser"));
                //INSEREZ DATELE IN TABELUL PROFESOR
                preparedStatement.setInt(1, resultSet1.getInt("idUser"));
                preparedStatement.setInt(2, Integer.parseInt(tfMinimOre.getText()));
                preparedStatement.setInt(3, Integer.parseInt(tfMaximOre.getText()));
                preparedStatement.setString(4, "Calculatoare");

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Datele au fost actualizate în baza de date.");
                } else {
                    System.out.println("Nu s-au găsit înregistrări pentru actualizare.");
                }
            //OBTIN IdProfesor din IdUser
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,resultSet1.getInt("idUser" ));
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();

//            int idProfesor = resultSet.getInt("idProfesor");
//            System.out.println();
//            System.out.println(idProfesor);
            if (resultSet.next()) {
                idProfesor = resultSet.getInt("idProfesor");
                System.out.println("Id-ul Profesorului pentru idUser " + resultSet1.getInt("idUser" ) + " este " + idProfesor);
                profesorData.setIdProfesor(idProfesor);
                profesorData.setIdUser(resultSet1.getInt("idUser" ));

            } else {
                System.out.println("Nu a fost găsit niciun Profesor pentru idUser " + resultSet1.getInt("idUser" ));
            }
            System.out.println();
            System.out.println(idProfesor);

            //DORESC SA ASIGNEZ MATERIE LA PROFESOR(ma folosesc de tabela profesor_materie)
            PreparedStatement pstmt2 = connection.prepareStatement(query2);
            pstmt2.setInt(1, idProfesor);
            pstmt2.setInt(2, selectedItem);
            int rowsAffected2 = pstmt2.executeUpdate();
            if (rowsAffected2 > 0) {
                System.out.println("Inserare reușită!");
            } else {
                System.out.println("Inserare eșuată!");
            }


            // Închide conexiunea
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

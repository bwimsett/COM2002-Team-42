package ui;

import main.DentalPractice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PatientTable extends JDialog {
    String[] columnNames = new String[]{"Patient ID","First Name","Surname","Healthcare Plan"};
    String[][] data;

    JScrollPane scrollPane;

    public JTable table;

    public PatientTable(){
        initialise();
    }

    private void initialise(){
        this.setSize(300,400);
        this.setVisible(true);
        this.setTitle("Patients");

        scrollPane = new JScrollPane();

        populateTable();

        //scrollPane.add(table);

        add(new JScrollPane(table));
    }

    public void populateTable(){
        ArrayList<String> entries = new ArrayList();
        Connection con = DentalPractice.getCon();
        try {
            String query = "SELECT * From team042.Patient;";
            Statement statement = con.createStatement();

            ResultSet result = statement.executeQuery(query);

            while(result.next()){
                String entry = "";
                entry += result.getString("PatientID");
                entry += ",";
                entry += result.getString("Forename");
                entry += ",";
                entry += result.getString("Surname");
                entry += ",";
                entry += result.getString("Plan");

                entries.add(entry);
            }

            String[] entriesArray = new String[entries.size()];

            data = new String[entriesArray.length][4];

            for(int i = 0; i < entriesArray.length; i++){
                entriesArray[i] = entries.get(i);
                String[] splitEntry = entriesArray[i].split(",");

                for(int x = 0; x < 4; x++){
                    data[i][x] = splitEntry[x];
                }
            }


            table = new JTable(data,columnNames);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

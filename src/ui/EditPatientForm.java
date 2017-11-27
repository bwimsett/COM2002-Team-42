package ui;

import main.DentalPractice;
import ui.calendar.Calendar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditPatientForm extends JDialog {

    JTextField patientIDField;
    JPanel patientIDPanel;

    JButton findPatientButton;
    JPanel findPatientPanel;
    String currentHealthcarePlan;

    JLabel currentHealthcarePlanLabel;
    JComboBox<String> healthcarePlanOptions = new JComboBox();

    public EditPatientForm(){
        initialise();
    }

    public void initialise(){
        this.setSize(200,200);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        patientIDPanel = new JPanel();
        patientIDPanel.add(new JLabel("Patient ID"));
        patientIDField = new JTextField();
        patientIDField.setColumns(10);
        patientIDPanel.add(patientIDField);

        findPatientButton = new JButton("Find Patient");
        findPatientPanel = new JPanel();
        findPatientPanel.add(findPatientButton);

        add(patientIDPanel);
        add(findPatientPanel);
    }

    public String getCurrentHealthcarePlan() {
        return currentHealthcarePlan;
    }

    public void setCurrentHealthcarePlan(String currentHealthcarePlan) {
        this.currentHealthcarePlan = currentHealthcarePlan;
    }


}

class FindPatientButtonListener implements ActionListener {

    int patientID;
    EditPatientForm editPatientForm;

    public FindPatientButtonListener(int patientID, EditPatientForm editPatientForm){
        this.patientID = patientID;
        this.editPatientForm = editPatientForm;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Connection con = DentalPractice.getCon();

        String query = "SELECT * FROM Team042.Patient WHERE team042.Patient.PatientID = "+patientID+";";

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);

            if(result.next()){
                String currentHealthCarePlan = result.getString("HealthcarePlan");
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}

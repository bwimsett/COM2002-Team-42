package ui;

import main.DentalPractice;

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

    JPanel changeHealthcarePlanPanel;
    JComboBox<String> healthcarePlanOptions = new JComboBox();
    JButton changeHealthcarePlanButton;

    public EditPatientForm(){
        initialise();
    }

    public void initialise(){
        this.setSize(400,200);
        this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        patientIDPanel = new JPanel();
        patientIDPanel.add(new JLabel("Patient ID"));
        patientIDField = new JTextField();
        patientIDField.setColumns(10);
        patientIDPanel.add(patientIDField);

        findPatientButton = new JButton("Find Patient");
        findPatientButton.addActionListener(new FindPatientButtonListener(patientIDField,this));
        findPatientPanel = new JPanel();
        findPatientPanel.add(findPatientButton);

        currentHealthcarePlanLabel = new JLabel();

        changeHealthcarePlanPanel = new JPanel();
        changeHealthcarePlanButton = new JButton("Change plan");
        changeHealthcarePlanButton.addActionListener(new ChangeHealthcarePlanButtonListener(this,patientIDField));
        changeHealthcarePlanPanel.add(healthcarePlanOptions);
        changeHealthcarePlanPanel.add(changeHealthcarePlanButton);

        add(patientIDPanel);
        add(findPatientPanel);
        add(currentHealthcarePlanLabel);
        add(changeHealthcarePlanPanel);
    }

    public String getCurrentHealthcarePlan() {
        return currentHealthcarePlan;
    }

    public void setCurrentHealthcarePlan(String currentHealthcarePlan) {
        this.currentHealthcarePlan = currentHealthcarePlan;
        currentHealthcarePlanLabel.setText("Healthcare plan: "+currentHealthcarePlan);

        revalidate();
        repaint();
    }

    public void updateCurrentHealthcarePlan(){
        Connection con = DentalPractice.getCon();

        int patientID = Integer.parseInt(patientIDField.getText());

        String query = "SELECT * FROM team042.Patient WHERE team042.Patient.PatientID = "+patientID+";";

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);

            if(result.next()){
                String currentHealthCarePlan = result.getString("Plan");
                setCurrentHealthcarePlan(currentHealthCarePlan);
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public JComboBox<String> getHealthcarePlanOptions() {
        return healthcarePlanOptions;
    }

    public void updateHealthcareplanOptions(){
        String query = "SELECT * FROM team042.HealthcarePlan;";
        Connection con = DentalPractice.getCon();

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);

            healthcarePlanOptions.removeAllItems();

            while(result.next()){
                healthcarePlanOptions.addItem(result.getString("Plan"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

class FindPatientButtonListener implements ActionListener {

    JTextField patientIDField;
    EditPatientForm editPatientForm;

    public FindPatientButtonListener(JTextField patientIDField, EditPatientForm editPatientForm){
        this.patientIDField = patientIDField;
        this.editPatientForm = editPatientForm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        editPatientForm.updateHealthcareplanOptions();
        editPatientForm.updateCurrentHealthcarePlan();
    }
}

class ChangeHealthcarePlanButtonListener implements ActionListener{

    JComboBox healthcarePlanOptions;
    JTextField patientIDField;
    EditPatientForm editPatientForm;


    public ChangeHealthcarePlanButtonListener(EditPatientForm editPatientForm, JTextField patientIDField){
        this.editPatientForm = editPatientForm;
        this.patientIDField = patientIDField;
        healthcarePlanOptions = editPatientForm.getHealthcarePlanOptions();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int patientID = Integer.parseInt(patientIDField.getText());
        Connection con = DentalPractice.getCon();
        try {
            Statement statement = con.createStatement();
            String selectedPlan = (String)healthcarePlanOptions.getSelectedItem();

            String query = "UPDATE team042.Patient SET team042.Patient.Plan = '"+selectedPlan+"' WHERE team042.Patient.PatientID = "+patientID+";";

            statement.execute(query);

            editPatientForm.updateCurrentHealthcarePlan();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }
}
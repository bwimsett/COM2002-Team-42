package ui;

import main.DentalPractice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class RegisterPatientForm extends JDialog {

    private JButton registerPatientButton;

    JPanel namePanel = new JPanel();
    JComboBox<String> titlePicker = new JComboBox(new String[]{"Mr","Mrs"});
    JTextField forename;
    JTextField surname;
    DatePicker dateOfBirth;

    JPanel phoneNumberPanel = new JPanel();
    JTextField phoneNumber;

    JPanel addressPanel = new JPanel();
    JTextField postcode;
    JTextField houseNumber;

    JPanel healthcarePlanPanel = new JPanel();
    JComboBox<String>  healthcarePlan = new JComboBox(new String[]{"Full","Minimum","None"});

    JPanel finalButtonsPanel = new JPanel();
    JButton registerButton;
    JButton cancelButton;

    public RegisterPatientForm(JButton registerPatientButton){
        this.registerPatientButton = registerPatientButton;
        initialise();
    }

    private void initialise(){
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        setSize(700,250);
        setTitle("Register Patient");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);

        forename = new JTextField(15);
        surname = new JTextField(15);
        namePanel.add(new JLabel("Title"));
        namePanel.add(titlePicker);
        namePanel.add(new JLabel("Forename"));
        namePanel.add(forename);
        namePanel.add(new JLabel("Surname"));
        namePanel.add(surname);
        add(namePanel);

        dateOfBirth = new DatePicker(1930,Calendar.getInstance().get(Calendar.YEAR),"DOB");
        add(dateOfBirth);


        phoneNumber = new JTextField(15);
        phoneNumberPanel.add(new JLabel("Phone Number"));
        phoneNumberPanel.add(phoneNumber);
        add(phoneNumberPanel);

        postcode = new JTextField(10);
        houseNumber = new JTextField(5);
        addressPanel.add(new JLabel("Postcode"));
        addressPanel.add(postcode);
        addressPanel.add(new JLabel("House number"));
        addressPanel.add(houseNumber);
        add(addressPanel);

        healthcarePlanPanel.add(new JLabel("Healthcare Plan"));
        healthcarePlanPanel.add(healthcarePlan);
        add(healthcarePlanPanel);

        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new registerPatientCancelButtonListener(registerPatientButton,this));
        registerButton.addActionListener(new RegisterButtonListener(this));
        finalButtonsPanel.add(registerButton);
        finalButtonsPanel.add(cancelButton);
        add(finalButtonsPanel);

        setVisible(true);
    }

    public DatePicker getDateOfBirth() {
        return dateOfBirth;
    }

}

class RegisterButtonListener implements ActionListener{
    Connection con = DentalPractice.getCon();
    String query;
    RegisterPatientForm registerPatientForm;

    public RegisterButtonListener(RegisterPatientForm registerPatientForm){
        this.registerPatientForm = registerPatientForm;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        registerPatient();
    }

    public void registerPatient() {

        String title = (String)registerPatientForm.titlePicker.getSelectedItem();

        String forename = registerPatientForm.forename.getText();

        String surname = registerPatientForm.surname.getText();

        DatePicker datePicker = registerPatientForm.getDateOfBirth();
        int chosenYear = (int)datePicker.getYearComboBox().getSelectedItem()-1900;
        int chosenMonth = datePicker.monthToInt((String)datePicker.getMonthComboBox().getSelectedItem())-1;
        int chosenDay = (int)datePicker.getDayComboBox().getSelectedItem();
        Date dateOfBirth = new Date(chosenYear,chosenMonth,chosenDay);

        String phoneNo = registerPatientForm.phoneNumber.getText();

        String plan = (String)registerPatientForm.healthcarePlan.getSelectedItem();

        String postCode = registerPatientForm.postcode.getText();

        int houseNo = Integer.parseInt(registerPatientForm.houseNumber.getText());

        int credit = 0;

        /*NOT CURRENTLY WORKING AS THE ADDRESS DATABASE NEEDS UPDATING IF THE ADDRESS GIVEN IS NOT ALREADY REGISTERED
        * - Needs inputs for Street, District and City
        * - If the current address is not already in the address database, update that first and then patient database
        * - Otherwise update patient database.
        */
        query = "INSERT INTO Patient (Title, Forename, Surname, DOB, PhoneNumber, Credit, Plan, Postcode, HouseNumber) " +
                "VALUES ('"+title+"', '"+forename+"', '"+surname+"', '"+dateOfBirth+"', '"+phoneNo+"', "+credit+", '"+plan+"', '"+postCode+"', "+houseNo+");";

        try {
            Statement statement = con.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

class registerPatientCancelButtonListener implements ActionListener{

    private JButton registerPatientButton;
    private RegisterPatientForm registerPatientForm;

    public registerPatientCancelButtonListener(JButton registerPatientButton, RegisterPatientForm form){
        this.registerPatientButton = registerPatientButton;
        this.registerPatientForm = form;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        registerPatientButton.setEnabled(true);
        registerPatientForm.dispose();
    }
}

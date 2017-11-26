package ui;

import main.DentalPractice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
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
    JTextField street;
    JTextField district;
    JTextField city;
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
        setSize(750,450);
        setTitle("Register Patient");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);

        updateHealthcareplanTypes();

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
        street = new JTextField(15);
        district = new JTextField(15);
        city = new JTextField(15);
        houseNumber = new JTextField(5);
        addressPanel.add(new JLabel("House number"));
        addressPanel.add(houseNumber);
        addressPanel.add(new JLabel("Street"));
        addressPanel.add(street);
        addressPanel.add(new JLabel("City"));
        addressPanel.add(city);
        addressPanel.add(new JLabel("District"));
        addressPanel.add(district);
        addressPanel.add(new JLabel("Postcode"));
        addressPanel.add(postcode);
        addressPanel.setLayout(new BoxLayout(addressPanel,BoxLayout.Y_AXIS));

        add(addressPanel);

        healthcarePlanPanel.add(new JLabel("Healthcare Plan"));
        healthcarePlanPanel.add(healthcarePlan);
        add(healthcarePlanPanel);

        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new registerPatientCancelButtonListener(registerPatientButton,this));
        registerButton.addActionListener(new RegisterButtonListener(this,registerPatientButton));
        finalButtonsPanel.add(registerButton);
        finalButtonsPanel.add(cancelButton);
        add(finalButtonsPanel);

        setVisible(true);
    }

    private void updateHealthcareplanTypes(){
        String query = "SELECT * FROM team042.HealthcarePlan;";
        Connection con = DentalPractice.getCon();

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);

            healthcarePlan.removeAllItems();

            while(result.next()){
                healthcarePlan.addItem(result.getString("Plan"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DatePicker getDateOfBirth() {
        return dateOfBirth;
    }

}

class RegisterButtonListener implements ActionListener{
    Connection con = DentalPractice.getCon();
    String query;
    RegisterPatientForm registerPatientForm;
    JButton registerButton;

    public RegisterButtonListener(RegisterPatientForm registerPatientForm, JButton registerButton){
        this.registerPatientForm = registerPatientForm;
        this.registerButton = registerButton;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        registerPatient();
        registerButton.setEnabled(true);
        registerPatientForm.dispose();
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
        String street = registerPatientForm.street.getText();
        String district = registerPatientForm.district.getText();
        String city = registerPatientForm.city.getText();
        int houseNo = Integer.parseInt(registerPatientForm.houseNumber.getText());

        int credit = 0;

        /*NOT CURRENTLY WORKING AS THE BIT THAT MAKES THE ADDRESSES DOESN'T YET WORK
        not sure why this is :/
        "Cannot add or update a child row: a foreign key constraint fails"
        this implies that the address creation hasn't worked properly
        */

        //query = "SELECT * FROM team042.Address WHERE team042.Address.Postcode = '"+postCode+"' AND HouseNumber = "+houseNo+";";


        query = "INSERT INTO team042.Address (Postcode, HouseNumber, Street, District, City) " +
                "VALUES ('"+postCode+"', "+houseNo+", '"+street+"', '"+district+"', '"+city+"');";

        try {
            Statement statement = con.createStatement();
            statement.execute(query);
            statement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        query = "INSERT INTO team042.Patient (" +
                "team042.Patient.Title, " +
                "team042.Patient.Forename, " +
                "team042.Patient.Surname, " +
                "team042.Patient.DOB, " +
                "team042.Patient.PhoneNumber, " +
                "team042.Patient.Credit, " +
                "team042.Patient.Plan, " +
                "team042.Patient.Postcode, " +
                "team042.Patient.HouseNumber) " +
                "VALUES ('"+title+"', '"+forename+"', '"+surname+"', '"+dateOfBirth+"', '"+phoneNo+"', "+credit+", '"+plan+"', '"+postCode+"', "+houseNo+");";

        try {
            Statement statement = con.createStatement();
            statement.execute(query);
            statement.close();
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

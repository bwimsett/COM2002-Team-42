package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    JComboBox<Boolean>  hasHealthcarePlan = new JComboBox(new Boolean[]{true,false});

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
        healthcarePlanPanel.add(hasHealthcarePlan);
        add(healthcarePlanPanel);

        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new registerPatientCancelButtonListener(registerPatientButton,this));
        finalButtonsPanel.add(registerButton);
        finalButtonsPanel.add(cancelButton);
        add(finalButtonsPanel);

        setVisible(true);
    }
}

class registerButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {

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

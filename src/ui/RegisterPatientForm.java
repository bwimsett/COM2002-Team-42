package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPatientForm extends JDialog {

    private JButton registerPatientButton;

    JPanel finalButtonsPanel = new JPanel();
    JButton registerButton;
    JButton cancelButton;

    public RegisterPatientForm(JButton registerPatientButton){
        this.registerPatientButton = registerPatientButton;
        initialise();
    }

    private void initialise(){
        setSize(200,200);
        setTitle("Register Patient");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);

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

package ui;

import main.DentalPractice;
import ui.calendar.Calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ben on 21/11/2017.
 */
public class WelcomeScreen extends JDialog {

    Color backgroundColor = new Color(64,64,64);
    private JComboBox<String> comboBox;
    private String[] comboBoxItems = new String[]{"Secretary","Dental Professional"};
    private JButton continueButton = new JButton("Log In");

    public WelcomeScreen(String title){
        this.setTitle(title);
        this.setSize(new Dimension(300,150));
        initialise();
        display();
    }

    private void initialise(){
        comboBox = new JComboBox(comboBoxItems);
        add(new JLabel("Select your role"));
        add(comboBox,BorderLayout.CENTER);
        continueButton.addActionListener(new LoginButtonListener(comboBox, this));
        add(continueButton,BorderLayout.SOUTH);

        //CENTER ON SCREEN
        setLocationRelativeTo(null);
        //PREVENT RESIZING
        setResizable(false);
    }

    public void display(){
        setVisible(true);
    }
}

class LoginButtonListener implements ActionListener {

    private JComboBox<String> comboBox;
    private JDialog container;

    public LoginButtonListener(JComboBox<String> comboBox, JDialog container){
        this.comboBox = comboBox;
        this.container = container;
    }

    public void actionPerformed(ActionEvent e){
        String selectedItem = (String)(comboBox.getSelectedItem());

        if(DentalPractice.getCalendar() != null){
            DentalPractice.getCalendar().dispose();
        }

        if(selectedItem.equals("Secretary")) {
            DentalPractice.setCalendar(new Calendar(DentalPractice.getCon(),"Secretary Calendar",EmployeeRole.SECRETARY,9));
        } else if (selectedItem.equals("Dental Professional")){
            DentalPractice.setCalendar(new Calendar(DentalPractice.getCon(),"Dentist Calendar",EmployeeRole.DENTAL_PROFESSIONAL,9));
        }

        //Hide container when button clicked
        container.setVisible(false);
    }
}

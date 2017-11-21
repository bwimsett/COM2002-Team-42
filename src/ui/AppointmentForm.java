package ui;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ben on 21/11/2017.
 */
public class AppointmentForm extends JDialog{

    JButton calendarBookButton;

    JPanel staffMemberComboPanel = new JPanel();
    JComboBox<String> staffMemberCombo = new JComboBox(new String[]{"Dentist","Hygienist"});

    JPanel patientIDPanel = new JPanel();
    JTextField patientIDField = new JTextField(10);

    JFXPanel datePickerPanel = new JFXPanel();
    DatePicker datePicker = new DatePicker();

    JPanel finalButtonsPanel = new JPanel();
    JButton cancelButton = new JButton("Cancel");
    JButton bookButton = new JButton("Book");

     public AppointmentForm(JButton calendarBookButton){
         this.calendarBookButton = calendarBookButton;

         initialise();

     }

     private void initialise(){
         setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
         setSize(300,150);
         setTitle("Book Appointment");
         setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);


        staffMemberComboPanel.add(new JLabel("Staff member:"));
        staffMemberComboPanel.add(staffMemberCombo);
        add(staffMemberComboPanel);

        patientIDPanel.add(new JLabel("Patient ID:"));
        patientIDPanel.add(patientIDField);
        add(patientIDPanel);

        cancelButton.addActionListener(new cancelButtonListener(this,calendarBookButton));
        finalButtonsPanel.add(bookButton);
        finalButtonsPanel.add(cancelButton);
        add(finalButtonsPanel);


         setResizable(false);
         setVisible(true);
     }
}

class cancelButtonListener implements ActionListener{

    JDialog buttonContainer;
    JButton calendarBookButton;

    public cancelButtonListener(JDialog buttonContainer, JButton calendarBookButton){
        this.buttonContainer = buttonContainer;
        this.calendarBookButton = calendarBookButton;
    }

    public void actionPerformed(ActionEvent e) {
        calendarBookButton.setEnabled(true);
        buttonContainer.dispose();
    }
}
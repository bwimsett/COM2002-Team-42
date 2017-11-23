package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.sql.*;
import java.util.Properties;

/**
 * Created by Ben on 21/11/2017.
 */
public class AppointmentForm extends JDialog{

    JButton calendarBookButton;

    JPanel staffMemberComboPanel = new JPanel();
    JComboBox<String> staffMemberCombo = new JComboBox(new String[]{"Dentist","Hygienist"});

    JPanel patientIDPanel = new JPanel();
    JTextField patientIDField = new JTextField(10);

    DatePicker datePicker;

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
        
        datePicker = new DatePicker(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR),4);
        add(datePicker);

        patientIDPanel.add(new JLabel("Patient ID:"));
        patientIDPanel.add(patientIDField);
        add(patientIDPanel);

        cancelButton.addActionListener(new cancelButtonListener(this,calendarBookButton));
        bookButton.addActionListener(new bookButtonListener());
        finalButtonsPanel.add(bookButton);
        finalButtonsPanel.add(cancelButton);
        add(finalButtonsPanel);


         setResizable(false);
         setVisible(true);
     }


}

class bookButtonListener implements ActionListener{
    Connection con = Calendar.getCon();
    String query;
	
	public void actionPerformed(ActionEvent e) {
        query = "SELECT Forename, Surname FROM Patient";
        bookAppointment();
	}
	
    public void bookAppointment() {
		try {
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(query);
			result.next();
			System.out.println(result.getString("Forename") +" "+result.getString("Surname"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
package ui;

import main.DentalPractice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.sql.*;

/**
 * Created by Ben on 21/11/2017.
 */
public class AppointmentForm extends JDialog{

    JButton calendarBookButton;

    JPanel staffMemberComboPanel = new JPanel();
    JComboBox<String> staffMemberPicker = new JComboBox(new String[]{"Dentist","Hygienist"});

    JPanel appointmentTypePanel = new JPanel();
    JComboBox<String> appointmentTypePicker = new JComboBox(new String[]{"Check Up","Descaling"});

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
        setSize(300,200);
        setTitle("Book Appointment");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        staffMemberComboPanel.add(new JLabel("Staff member"));
        staffMemberComboPanel.add(staffMemberPicker);
        add(staffMemberComboPanel);

        appointmentTypePanel.add(new JLabel("Appointment Type"));
        appointmentTypePanel.add(appointmentTypePicker);
        add(appointmentTypePanel);
        
        datePicker = new DatePicker(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR),4);
        add(datePicker);

        patientIDPanel.add(new JLabel("Patient ID"));
        patientIDPanel.add(patientIDField);
        add(patientIDPanel);

        cancelButton.addActionListener(new cancelButtonListener(this,calendarBookButton));
        bookButton.addActionListener(new bookButtonListener(this,calendarBookButton));
        finalButtonsPanel.add(bookButton);
        finalButtonsPanel.add(cancelButton);
        add(finalButtonsPanel);


         setResizable(false);
         setVisible(true);
     }

     //ACCESSOR / MUTATOR
    public JComboBox<String> getStaffMemberPicker() {
        return staffMemberPicker;
    }

    public JComboBox<String> getAppointmentTypePicker() {
        return appointmentTypePicker;
    }

    public JTextField getPatientIDField() {
        return patientIDField;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }
}

class bookButtonListener implements ActionListener{
    Connection con = DentalPractice.getCon();
    String query;
    AppointmentForm appointmentForm;
    JButton calendarBookButton;

    public bookButtonListener(AppointmentForm appointmentForm, JButton calendarBookButton){
        this.appointmentForm = appointmentForm;
        this.calendarBookButton = calendarBookButton;
    }

	public void actionPerformed(ActionEvent e) {
        bookAppointment();
	}
	
    public void bookAppointment() {
        int professionalID = DentalPractice.getStaffManager().getProfessionalID((String)appointmentForm.getStaffMemberPicker().getSelectedItem());
        String appointmentType = (String)appointmentForm.getAppointmentTypePicker().getSelectedItem();

        DatePicker datePicker = appointmentForm.getDatePicker();
        Date chosenDate = new Date( (int)datePicker.getYearComboBox().getSelectedItem(),
                                    datePicker.monthToInt((String)datePicker.getMonthComboBox().getSelectedItem()),
                                    (int)datePicker.getDayComboBox().getSelectedItem());
        Time startTime = new Time(12,0,0);
        Time endTime = new Time(13,0,0);
        int patientID = Integer.parseInt(appointmentForm.getPatientIDField().getText());

        query = "INSERT INTO Appointment VALUES ("+professionalID+", '"+chosenDate+"', '"+startTime+"', '"+endTime+"', '"+appointmentType+"', "+patientID+");";

		try {
			Statement statement = con.createStatement();
			statement.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		calendarBookButton.setEnabled(true);
        appointmentForm.dispose();

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
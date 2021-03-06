package ui;

import main.DentalPractice;
import ui.calendar.Calendar;

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
import java.util.ArrayList;

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

    TimePicker timePicker;

    JPanel finalButtonsPanel = new JPanel();
    JButton cancelButton = new JButton("Cancel");
    JButton bookButton = new JButton("Book");

    public AppointmentForm(JButton calendarBookButton){
         this.calendarBookButton = calendarBookButton;
         initialise();
     }

    private void initialise(){
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        setSize(400,300);
        setTitle("Book Appointment");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        staffMemberPicker.addActionListener(new StaffMemberPickerActionListener(this));
        staffMemberComboPanel.add(new JLabel("Staff member"));
        staffMemberComboPanel.add(staffMemberPicker);
        add(staffMemberComboPanel);

        appointmentTypePanel.add(new JLabel("Appointment Type"));
        appointmentTypePanel.add(appointmentTypePicker);
        add(appointmentTypePanel);
        
        datePicker = new DatePicker(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR),4,"Date");
        add(datePicker);

        timePicker = new TimePicker();
        add(timePicker);

        patientIDPanel.add(new JLabel("Patient ID"));
        patientIDPanel.add(patientIDField);
        add(patientIDPanel);

        cancelButton.addActionListener(new BookAppointmentCancelButtonListener(this,calendarBookButton));
        bookButton.addActionListener(new BookButtonListener(this,calendarBookButton));
        finalButtonsPanel.add(bookButton);
        finalButtonsPanel.add(cancelButton);
        add(finalButtonsPanel);

        updateAppointmentTypes();

         setResizable(false);
         setVisible(true);
     }

    public void updateAppointmentTypes(){
        String selectedStaff = (String)staffMemberPicker.getSelectedItem();

        Connection con = DentalPractice.getCon();
        try {
            String query = "SELECT * FROM team042.Treatment WHERE team042.Treatment.JobTitle = '"+selectedStaff+"';";
            Statement statement = con.createStatement();

            ResultSet result = statement.executeQuery(query);

            appointmentTypePicker.removeAllItems();

            while(result.next()){
                String currentTreatmentType = result.getString("AppointmentType");
                appointmentTypePicker.addItem(currentTreatmentType);
            }

            revalidate();
            repaint();

        } catch (SQLException e) {
            e.printStackTrace();
        }


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

    public TimePicker getTimePicker() {
        return timePicker;
    }
}

class BookButtonListener implements ActionListener{
    Connection con = DentalPractice.getCon();
    String query;
    AppointmentForm appointmentForm;
    JButton calendarBookButton;

    public BookButtonListener(AppointmentForm appointmentForm, JButton calendarBookButton){
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

        int chosenYear = (int)datePicker.getYearComboBox().getSelectedItem()-1900;
        int chosenMonth = (int)datePicker.monthToInt((String)datePicker.getMonthComboBox().getSelectedItem())-1;
        int chosenDay = (int)datePicker.getDayComboBox().getSelectedItem();

        Date chosenDate = new Date(chosenYear,chosenMonth,chosenDay);

        TimePicker timePicker = appointmentForm.getTimePicker();
        int endTimeInSeconds = (((int)timePicker.startHour.getSelectedItem())*60*60)+
                                ((int)timePicker.getStartMinutes().getSelectedItem())*60+
                                ((int)timePicker.getDuration().getSelectedItem())*60;

        Time startTime = new Time((int)timePicker.startHour.getSelectedItem(),(int)timePicker.startMinutes.getSelectedItem(),0);
        Time endTime = new Time((int)timePicker.startHour.getSelectedItem(),((int)timePicker.startMinutes.getSelectedItem())+(int)timePicker.duration.getSelectedItem(),0);

        //this is frankly ugly but I'm not sure it needs/should be better
        //basically it lets you book appointments with no patient for a break for the staff but the console gets angry when you draw the calendar
        //this doesn't affect functionality *at all* (afaik)
        String patientIDString = appointmentForm.getPatientIDField().getText();
        if (!patientIDString.equals("")) {
            int patientID = Integer.parseInt(patientIDString);
            query = "INSERT INTO Appointment VALUES ("+professionalID+", '"+chosenDate+"', '"+startTime+"', '"+endTime+"',"+0+", '"+appointmentType+"', "+0+","+patientID+");";
        }
        else {
            query = "INSERT INTO team042.Appointment (ProfessionalID, AppointmentDate, AppointmentStartTime, AppointmentEndTime, AppointmentType, Completed) " +
                    "VALUES ("+professionalID+", '"+chosenDate+"', '"+startTime+"', '"+endTime+"', '"+appointmentType+"', "+0+");";
        }
        //int patientID = Integer.parseInt(patientIDString);



		try {
			Statement statement = con.createStatement();
			statement.execute(query);
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        DentalPractice.getCalendar().refreshCalendar();
		calendarBookButton.setEnabled(true);
        appointmentForm.dispose();

    }
}

class BookAppointmentCancelButtonListener implements ActionListener{
    JDialog buttonContainer;
    JButton calendarBookButton;


    public BookAppointmentCancelButtonListener(JDialog buttonContainer, JButton calendarBookButton){
        this.buttonContainer = buttonContainer;
        this.calendarBookButton = calendarBookButton;
    }

    public void actionPerformed(ActionEvent e) {
        calendarBookButton.setEnabled(true);
        buttonContainer.dispose();
    }
}

class StaffMemberPickerActionListener implements ActionListener{

    AppointmentForm appointmentForm;

    public StaffMemberPickerActionListener(AppointmentForm appointmentForm){
        this.appointmentForm = appointmentForm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appointmentForm.updateAppointmentTypes();
    }
}
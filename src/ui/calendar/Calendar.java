package ui.calendar;

import main.DentalPractice;
import ui.AppointmentForm;
import ui.EmployeeRole;
import ui.RegisterPatientForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Created by Ben on 20/11/2017.
 */
public class Calendar extends JFrame {

    private Connection con;

	EmployeeRole employeeRole;

    private SecretaryToolbar secretaryToolbar;
    private CalendarDisplay calendarDisplay;

    static int startHour = 9; // The hour that work starts each day

    public Calendar(Connection con, String title, EmployeeRole employeeRole, int startHour) {
        this.con = con;
        this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        this.setTitle(title);
        this.employeeRole = employeeRole;
        this.startHour = startHour;
        this.initialise();
    }

    void initialise(){
        setSize(1300,900);
        calendarDisplay = new CalendarDisplay(this);
        if(employeeRole == EmployeeRole.SECRETARY){
            secretaryToolbar = new SecretaryToolbar(this);
            add(secretaryToolbar);
        }
        add(calendarDisplay);
        setVisible(true);
    }

    public static int getStartHour() {
        return startHour;
    }

    public CalendarDisplay getCalendarDisplay() {
        return calendarDisplay;
    }

    public void refreshCalendar(){
        getCalendarDisplay().refreshCalendarPanel();
    }

    public SecretaryToolbar getSecretaryToolbar() {
        return secretaryToolbar;
    }
}

class SecretaryToolbar extends JPanel{

    private JButton bookAppointment_btn;
    private JButton registerPatient_btn;
    private JButton checkoutPatient_btn;
    private JButton cancelAppointment_btn;

    Calendar calendar;

    public SecretaryToolbar(Calendar calendar){
        this.calendar = calendar;
        initialise();
    }

    public void initialise(){
        bookAppointment_btn = new JButton("Book Appointment");
        bookAppointment_btn.addActionListener(new BookAppointmentButtonListener(bookAppointment_btn));
        registerPatient_btn = new JButton("Register Patient");
        registerPatient_btn.addActionListener(new RegisterPatientButtonListener(registerPatient_btn));
        checkoutPatient_btn = new JButton("Checkout Patient");
        cancelAppointment_btn = new JButton("Cancel Appointment");
        cancelAppointment_btn.setEnabled(false);
        cancelAppointment_btn.addActionListener(new CancelAppointmentButtonListener(calendar.getCalendarDisplay().getCalendarPanel()));
        this.add(bookAppointment_btn);
        this.add(registerPatient_btn);
        this.add(checkoutPatient_btn);
        this.add(cancelAppointment_btn);
    }

    public JButton getCancelAppointment_btn() {
        return cancelAppointment_btn;
    }
}

class BookAppointmentButtonListener implements ActionListener {

    private JButton appointmentButton;
    private AppointmentForm appointmentForm;


    public BookAppointmentButtonListener(JButton appointmentButton){
        this.appointmentButton = appointmentButton;
    }

    public void actionPerformed(ActionEvent e) {
        appointmentForm = new AppointmentForm(appointmentButton);
        appointmentButton.setEnabled(false);
    }
}

class RegisterPatientButtonListener implements ActionListener{

    private JButton registerPatientButton;
    private RegisterPatientForm registerPatientForm;

    public RegisterPatientButtonListener(JButton registerPatientButton){
        this.registerPatientButton = registerPatientButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        registerPatientForm = new RegisterPatientForm(registerPatientButton);
        registerPatientButton.setEnabled(false);
    }
}

class CancelAppointmentButtonListener implements ActionListener{

    CalendarPanel calendarPanel;

    public CancelAppointmentButtonListener(CalendarPanel calendarPanel){
        this.calendarPanel = calendarPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CalendarAppointment selectedAppointment = calendarPanel.getSelectedAppointment();

        Date date = selectedAppointment.getDay();
        Time startTime = selectedAppointment.getStartTime();
        Time endTime = selectedAppointment.getEndTime();
        int professionalID = selectedAppointment.getProfessionalId();

        Connection con = DentalPractice.getCon();

        try {
            Statement statement = con.createStatement();

            String query = "DELETE FROM team042.Appointment " +
                    "WHERE team042.Appointment.AppointmentDate = '"+date+"' AND "+
                    "team042.Appointment.AppointmentStartTime = '"+startTime+"' AND "+
                    "team042.Appointment.AppointmentEndTime = '"+endTime+"' AND "+
                    "team042.Appointment.ProfessionalID = "+professionalID+";";

            statement.execute(query);
            DentalPractice.getCalendar().getCalendarDisplay().refreshCalendarPanel();


        } catch (SQLException e1) {
            e1.printStackTrace();
        }


    }
}
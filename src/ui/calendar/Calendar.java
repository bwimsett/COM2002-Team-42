package ui.calendar;

import main.DentalPractice;
import ui.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;

/**
 * Created by Ben on 20/11/2017.
 */
public class Calendar extends JFrame {

    private Connection con;

	EmployeeRole employeeRole;

    private SecretaryToolbar secretaryToolbar;
    private DentalProfessionalToolbar dentalToolbar;
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
        addWindowListener(new CalendarWindowListener(DentalPractice.getCon()));

        setSize(1300,900);
        calendarDisplay = new CalendarDisplay(this);
        secretaryToolbar = new SecretaryToolbar(this);
        dentalToolbar = new DentalProfessionalToolbar(this);

        if(employeeRole == EmployeeRole.SECRETARY){
            add(secretaryToolbar);
        } else {
            add(dentalToolbar);
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

    public DentalProfessionalToolbar getDentalToolbar() {
        return dentalToolbar;
    }
}

class SecretaryToolbar extends JPanel{

    private JButton bookAppointment_btn;
    private JButton registerPatient_btn;
    private JButton viewPatients_btn;
    private JButton editPatient_btn;
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
        viewPatients_btn = new JButton("View Patients");
        viewPatients_btn.addActionListener(new ViewPatientsButtonListener());
        checkoutPatient_btn = new JButton("Checkout Patient");
        editPatient_btn = new JButton("Edit Patient");
        editPatient_btn.addActionListener(new EditPatientButtonListener());
        cancelAppointment_btn = new JButton("Cancel Appointment");
        cancelAppointment_btn.setEnabled(false);
        cancelAppointment_btn.addActionListener(new CancelAppointmentButtonListener(calendar.getCalendarDisplay().getCalendarPanel()));
        this.add(bookAppointment_btn);
        this.add(registerPatient_btn);
        this.add(viewPatients_btn);
        this.add(editPatient_btn);
        this.add(checkoutPatient_btn);
        this.add(cancelAppointment_btn);
    }

    public JButton getCancelAppointment_btn() {
        return cancelAppointment_btn;
    }
}

class DentalProfessionalToolbar extends JPanel{
    private JButton completeAppointment;

    public DentalProfessionalToolbar(Calendar calendar){
        completeAppointment = new JButton("Mark as complete");
        completeAppointment.addActionListener(new CompleteAppointmentButtonListener(calendar.getCalendarDisplay().getCalendarPanel()));

        add(completeAppointment);
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

class ViewPatientsButtonListener implements ActionListener{

    PatientTable patientTable;

    @Override
    public void actionPerformed(ActionEvent e) {
        patientTable = new PatientTable();
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

            statement.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }


    }
}

class CompleteAppointmentButtonListener implements ActionListener{

    CalendarPanel calendarPanel;

    public CompleteAppointmentButtonListener(CalendarPanel calendarPanel){
        this.calendarPanel = calendarPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CalendarAppointment calendarAppointment = calendarPanel.getSelectedAppointment();
        calendarAppointment.complete();

        Connection con = DentalPractice.getCon();

        try {
            Statement statement = con.createStatement();

            Date appointmentDate = calendarAppointment.getDay();
            Time appointmentStartTime = calendarAppointment.getStartTime();
            Time appointmentEndTime = calendarAppointment.getEndTime();
            int appointmentProfessionalID = calendarAppointment.getProfessionalId();

            String query = "UPDATE team042.Appointment " +
                            "SET team042.Appointment.Completed = 1 " +
                            "WHERE team042.Appointment.AppointmentDate = '"+appointmentDate+"' AND " +
                            "team042.Appointment.AppointmentStartTime = '"+appointmentStartTime+"' AND "+
                            "team042.Appointment.AppointmentEndTime = '"+appointmentEndTime+"' AND " +
                            "team042.Appointment.ProfessionalID = '"+appointmentProfessionalID+"';";

            statement.execute(query);

            DentalPractice.getCalendar().getCalendarDisplay().refreshCalendarPanel();

            statement.close();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}

class EditPatientButtonListener implements ActionListener{

    EditPatientForm editPatientForm;

    public EditPatientButtonListener(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        editPatientForm = new EditPatientForm();
    }
}

class CalendarWindowListener implements WindowListener {

    Connection con;

    public CalendarWindowListener(Connection con){
        this.con = con;

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            con.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
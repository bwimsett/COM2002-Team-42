package ui.calendar;

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

    private SecretaryToolbar secretaryButtons;
    private CalendarDisplay calendarDisplay;

    public Calendar(Connection con, String title, EmployeeRole employeeRole){
        this.con = con;
        this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        this.setTitle(title);
        this.employeeRole = employeeRole;
        this.initialise();
    }

    void initialise(){
        setSize(1300,900);
        if(employeeRole == EmployeeRole.SECRETARY){
            secretaryButtons = new SecretaryToolbar();
            add(secretaryButtons);
        }

        calendarDisplay = new CalendarDisplay(this);
        add(calendarDisplay);
        setVisible(true);
    }
}

class SecretaryToolbar extends JPanel{

    private JButton bookAppointment_btn;
    private JButton registerPatient_btn;
    private JButton checkoutPatient_btn;

    public SecretaryToolbar(){
        initialise();
    }

    public void initialise(){
        bookAppointment_btn = new JButton("Book Appointment");
        bookAppointment_btn.addActionListener(new BookAppointmentButtonListener(bookAppointment_btn));
        registerPatient_btn = new JButton("Register Patient");
        registerPatient_btn.addActionListener(new RegisterPatientButtonListener(registerPatient_btn));
        checkoutPatient_btn = new JButton("Checkout Patient");
        this.add(bookAppointment_btn);
        this.add(registerPatient_btn);
        this.add(checkoutPatient_btn);
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
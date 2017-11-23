package ui;

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

    public Calendar(Connection con, String title, EmployeeRole employeeRole){
        this.con = con;
        this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        this.setTitle(title);
        this.employeeRole = employeeRole;
        this.initialise();
    }

    void initialise(){
        setSize(500,500);
        if(employeeRole == EmployeeRole.SECRETARY){
            secretaryButtons = new SecretaryToolbar();
            this.add(secretaryButtons);
        }
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

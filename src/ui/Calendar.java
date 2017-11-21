package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ben on 20/11/2017.
 */
public class Calendar extends JFrame {

    EmployeeRole employeeRole;

    private SecretaryButtons secretaryButtons;

    public Calendar(String title, EmployeeRole employeeRole){
        this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        this.setTitle(title);
        this.employeeRole = employeeRole;
        this.initialise();
    }

    void initialise(){
        setSize(500,500);
        if(employeeRole == EmployeeRole.SECRETARY){
            secretaryButtons = new SecretaryButtons();
            this.add(secretaryButtons);
            this.add(new JSeparator());
        }
        display();
    }

    void display(){
        setVisible(true);
    }
}

class SecretaryButtons extends JPanel{

    private JButton bookAppointment_btn;


    public SecretaryButtons(){
        initialise();
    }

    public void initialise(){
        bookAppointment_btn = new JButton("Book Appointment");
        bookAppointment_btn.addActionListener(new BookAppointmentButtonListener(bookAppointment_btn));
        this.add(bookAppointment_btn);
    }
}

class BookAppointmentButtonListener implements ActionListener {

    private JButton appointmentButton;
    private AppointmentForm appointmentForm;


    public BookAppointmentButtonListener(JButton appointmentButton){
        this.appointmentButton = appointmentButton;
    }

    public void actionPerformed(ActionEvent e) {
        appointmentForm = new AppointmentForm();
        appointmentButton.setEnabled(false);
    }
}

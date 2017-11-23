package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;



import java.util.Properties;

/**
 * Created by Ben on 20/11/2017.
 */
public class Calendar extends JFrame {

    private String databaseUsername =  "team042";
    private String databasePassword = "449c90e7";
    private static Connection con;

    EmployeeRole employeeRole;

    private SecretaryButtons secretaryButtons;

    public Calendar(String title, EmployeeRole employeeRole){
        this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        this.setTitle(title);
        this.employeeRole = employeeRole;
        this.initialise();
    }

    void initialise(){

        try {
            con = establishConnection();
			System.out.println(con.getMetaData());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setSize(500,500);
        if(employeeRole == EmployeeRole.SECRETARY){
            secretaryButtons = new SecretaryButtons();
            this.add(secretaryButtons);
        }
        setVisible(true);
    }

    Connection establishConnection() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connection established");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        String databaseAddress = "jdbc:mysql://stusql.dcs.shef.ac.uk/"+databaseUsername+"?user="+databaseUsername+"&password="+databasePassword;
        conn = DriverManager.getConnection(databaseAddress);

        return conn;
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
        appointmentForm = new AppointmentForm(appointmentButton);
        appointmentButton.setEnabled(false);
    }
}

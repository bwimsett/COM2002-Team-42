package ui;

import main.DentalPractice;
import ui.calendar.CalendarAppointment;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CompleteAppointmentForm extends JDialog {

    CalendarAppointment appointment;

    JPanel costPanel;
    JTextField costField;

    JPanel lastRow;
    JButton cancelButton;
    JButton completeButton;

    public CompleteAppointmentForm(/*CalendarAppointment appointment*/){
        this.appointment = appointment;

        initialise();
    }

    public void initialise(){
        setVisible(true);
        setSize(300,200);

        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        costPanel = new JPanel();
        costField = new JTextField(10);

        costPanel.add(new JLabel("Cost"));
        costPanel.add(costField);

        lastRow = new JPanel();

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CompleteAppointmentCancelButtonListener(this));
        completeButton = new JButton("Complete");
        completeButton.addActionListener(new CompleteAppointmentCompleteButtonListener(this));

        lastRow.add(cancelButton);
        lastRow.add(completeButton);

        add(costPanel);
        add(lastRow);
    }

    public CalendarAppointment getAppointment() {
        return appointment;
    }

    public JTextField getCostField() {
        return costField;
    }
}

class CompleteAppointmentCancelButtonListener implements ActionListener {

    CompleteAppointmentForm completeAppointmentForm;

    public CompleteAppointmentCancelButtonListener(CompleteAppointmentForm completeAppointmentForm){
        this.completeAppointmentForm = completeAppointmentForm;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        completeAppointmentForm.dispose();
    }
}

class CompleteAppointmentCompleteButtonListener implements ActionListener{


    CompleteAppointmentForm completeAppointmentForm;


    public CompleteAppointmentCompleteButtonListener(CompleteAppointmentForm completeAppointmentForm){
        this.completeAppointmentForm = completeAppointmentForm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int cost = Integer.parseInt(completeAppointmentForm.getCostField().getText());

        CalendarAppointment calendarAppointment = DentalPractice.getCalendar().getCalendarDisplay().getCalendarPanel().getSelectedAppointment();
        calendarAppointment.complete();

        Connection con = DentalPractice.getCon();

        try {
            Statement statement = con.createStatement();

            Date appointmentDate = calendarAppointment.getDay();
            Time appointmentStartTime = calendarAppointment.getStartTime();
            Time appointmentEndTime = calendarAppointment.getEndTime();
            int appointmentProfessionalID = calendarAppointment.getProfessionalId();

            String query = "UPDATE team042.Appointment " +
                    "SET team042.Appointment.Completed = 1, team042.Appointment.AppointmentCost = "+cost+" "+
                    "WHERE team042.Appointment.AppointmentDate = '"+appointmentDate+"' AND " +
                    "team042.Appointment.AppointmentStartTime = '"+appointmentStartTime+"' AND "+
                    "team042.Appointment.AppointmentEndTime = '"+appointmentEndTime+"' AND " +
                    "team042.Appointment.ProfessionalID = '"+appointmentProfessionalID+"';";

            statement.execute(query);

            DentalPractice.getCalendar().getCalendarDisplay().refreshCalendarPanel();

            statement.close();

            completeAppointmentForm.dispose();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
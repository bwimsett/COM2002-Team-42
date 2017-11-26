package ui.calendar;
import main.DentalPractice;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CalendarAppointment extends JPanel {

    JLabel appointmentTimeLabel;
    JLabel appointmentTypeLabel;
    Border border = BorderFactory.createLineBorder(Color.black);
    CalendarPanel calendarPanel;
    Color backgroundColor = Color.white;
    Color selectionColor = new Color(66,134,244);

    public CalendarAppointment(String startTime, String endTime, String appointmentType, CalendarPanel calendarPanel){
        this.calendarPanel = calendarPanel;

        addMouseListener(new AppointmentMouseListener(this));

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBorder(border);

        startTime = removeMilliseconds(startTime);
        endTime = removeMilliseconds(endTime);
        appointmentTimeLabel = new JLabel(""+startTime+" - "+endTime);
        appointmentTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(appointmentTimeLabel);

        appointmentTypeLabel = new JLabel(appointmentType);
        appointmentTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(appointmentTypeLabel);

        resetSelection();

        //setBackground(backgroundColor);
    }

    String removeMilliseconds(String inputString){
        String[] splitString = inputString.split(":");
        String outputString = splitString[0]+":"+splitString[1];

        return outputString;
    }

    public void resetSelection(){
        setBackground(backgroundColor);
        setBorder(border);
    }

    public void selectAppointment(){
        setBackground(selectionColor);
        calendarPanel.selectAppointment(this);
    }

}

class AppointmentMouseListener implements MouseListener {

    CalendarAppointment appointment;


    public AppointmentMouseListener(CalendarAppointment appointment){
        this.appointment = appointment;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        appointment.selectAppointment();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
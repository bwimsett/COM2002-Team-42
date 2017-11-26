package ui.calendar;
import main.DentalPractice;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.Time;

public class CalendarAppointment extends JPanel {

    JLabel appointmentTimeLabel;
    JLabel patientNameLabel;
    JLabel appointmentTypeLabel;
    JLabel completedLabel;
    Border border = BorderFactory.createLineBorder(Color.black);
    CalendarPanel calendarPanel;
    Color backgroundColor = Color.white;

    Color selectionColor = new Color(66,134,244);
    Color completionColor = new Color(244, 75, 66);

    Time startTime;
    Time endTime;
    String patientName;
    Date day;
    int professionalID;
    boolean selected;
    boolean completed;
    boolean checkedOut;

    public CalendarAppointment(String startTime, String endTime, String patientName, String appointmentType, Date day, int professionalID, boolean appointmentCompleted, CalendarPanel calendarPanel){
        this.day = day;
        this.startTime = Time.valueOf(startTime);
        this.endTime = Time.valueOf(endTime);
        this.professionalID = professionalID;
        this.patientName = patientName;

        completed = appointmentCompleted;
        checkedOut = false;

        selected = false;
        this.calendarPanel = calendarPanel;

        addMouseListener(new AppointmentMouseListener(this));

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBorder(border);

        startTime = removeMilliseconds(startTime);
        endTime = removeMilliseconds(endTime);
        appointmentTimeLabel = new JLabel(""+startTime+" - "+endTime);
        appointmentTimeLabel.setHorizontalTextPosition(JLabel.CENTER);
        add(appointmentTimeLabel);

        patientNameLabel = new JLabel(patientName);
        patientNameLabel.setHorizontalTextPosition(JLabel.CENTER);
        add(patientNameLabel);

        appointmentTypeLabel = new JLabel(appointmentType);
        appointmentTypeLabel.setHorizontalTextPosition(JLabel.CENTER);
        add(appointmentTypeLabel);

        if(completed) {
            completedLabel = new JLabel("Completed");
        } else {
            completedLabel = new JLabel("");
        }

        add(completedLabel);

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
        if(!selected) {
            setBackground(selectionColor);
            calendarPanel.selectAppointment(this);
            selected = true;
        } else {
            calendarPanel.deselectAppointment();
            selected = false;
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Date getDay() {
        return day;
    }

    public int getProfessionalId() {
        return professionalID;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void complete(){
        completed = true;
        completedLabel.setText("Completed");
        calendarPanel.deselectAppointment();
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;

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
package ui.calendar;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.*;

public class CalendarAppointment extends JPanel {

    JLabel appointmentTimeLabel;

    public CalendarAppointment(Color backgroundColor, int startHour, int startMinutes, int duration){
        appointmentTimeLabel = new JLabel(""+startHour+":"+startMinutes);
        add(appointmentTimeLabel);


        setBackground(backgroundColor);
    }

}

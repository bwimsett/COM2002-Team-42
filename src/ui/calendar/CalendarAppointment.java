package ui.calendar;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.*;
import java.text.SimpleDateFormat;

public class CalendarAppointment extends JPanel {

    JLabel appointmentTimeLabel;
    JLabel appointmentTypeLabel;
    Border border = BorderFactory.createLineBorder(Color.black);

    public CalendarAppointment(String startTime, String endTime, String appointmentType){
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

        //setBackground(backgroundColor);
    }

    String removeMilliseconds(String inputString){
        String[] splitString = inputString.split(":");
        String outputString = splitString[0]+":"+splitString[1];

        return outputString;
    }

}

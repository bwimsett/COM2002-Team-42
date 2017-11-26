package ui.calendar;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.*;
import java.text.SimpleDateFormat;

public class CalendarAppointment extends JPanel {

    JLabel appointmentTimeLabel;
    Border border = BorderFactory.createLineBorder(Color.black);

    public CalendarAppointment(Color backgroundColor, String startTime, String endTime){

        startTime = removeMilliseconds(startTime);
        endTime = removeMilliseconds(endTime);


        setBorder(border);
        appointmentTimeLabel = new JLabel(""+startTime+" - "+endTime);
        add(appointmentTimeLabel);

        //setBackground(backgroundColor);
    }

    String removeMilliseconds(String inputString){
        String[] splitString = inputString.split(":");
        String outputString = splitString[0]+":"+splitString[1];

        return outputString;
    }

}

package ui.calendar;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.*;

public class CalendarAppointment extends JPanel {

    Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);

    public CalendarAppointment(){
        setBorder(blackBorder);
    }

}
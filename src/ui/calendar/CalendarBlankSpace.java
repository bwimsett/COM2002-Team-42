package ui.calendar;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.*;

public class CalendarBlankSpace extends JPanel {

    Border greyBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

    public CalendarBlankSpace(){
        setBorder(greyBorder);
        setBackground(Color.white);
    }

}

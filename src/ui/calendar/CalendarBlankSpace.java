package ui.calendar;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.*;

public class CalendarBlankSpace extends JPanel {

    Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);

    public CalendarBlankSpace(){
        setBorder(blackBorder);
    }

}

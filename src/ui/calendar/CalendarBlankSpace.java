package ui.calendar;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.*;

public class CalendarBlankSpace extends JPanel {

    Border greyBorder = BorderFactory.createMatteBorder(0,1,0,1,Color.LIGHT_GRAY);

    public CalendarBlankSpace(){
        setBorder(greyBorder);
        setBackground(Color.white);
        setOpaque(false);
    }

}

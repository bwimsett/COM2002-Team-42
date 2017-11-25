package ui.calendar;

import javax.swing.*;
import java.awt.*;

public class CalendarPanel extends JPanel {

    GridBagConstraints constraints;

    public CalendarPanel(){
        initialise();
    }

    private void initialise(){
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.gridheight = 8;
        constraints.gridwidth = 7;

    }
}



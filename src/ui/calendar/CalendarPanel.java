package ui.calendar;

import javax.swing.*;
import java.awt.*;

public class CalendarPanel extends JPanel {

    GridBagConstraints constraints;
    GridBagLayout layout;

    CalendarBlankSpace[][] calendarBlankSpaces;


    public CalendarPanel(int width,int height){
        initialise(width,height);
    }

    private void initialise(int width, int height){
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();

        calendarBlankSpaces = new CalendarBlankSpace[width][height];


        constraints.weightx = 1;
        constraints.weighty = 1;
        //populate blank grid
        for(int x  = 0; x < width; x++){
            for(int y = 0; y < height; y++) {
                calendarBlankSpaces[x][y] = new CalendarBlankSpace();
                constraints.gridx = x;
                constraints.gridy = y;

                calendarBlankSpaces[x][y].setMinimumSize(new Dimension(this.getWidth()/width,this.getHeight()/height));

                add(calendarBlankSpaces[x][y],constraints);
            }
        }


        //add(new CalendarAppointment(),constraints);
    }
}



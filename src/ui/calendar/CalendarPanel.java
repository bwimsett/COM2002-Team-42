package ui.calendar;

import main.DentalPractice;

import javax.swing.*;
import java.awt.*;

public class CalendarPanel extends JPanel {

    GridBagConstraints constraints;
    GridBagLayout layout;

    CalendarBlankSpace[][] calendarBlankSpaces;

    int periodsPerHour;

    public CalendarPanel(int width,int height,int periodsPerHour){
        layout = new GridBagLayout();
        this.periodsPerHour = periodsPerHour;
        initialise(width,height);
    }

    private void initialise(int width, int height){
        setLayout(layout);
        constraints = new GridBagConstraints();

        calendarBlankSpaces = new CalendarBlankSpace[width][height*periodsPerHour];

        constraints.weightx = 1;
        constraints.weighty = 1;
        //populate blank grid
        for(int x  = 0; x < width; x++){
            for(int y = 0; y < height*periodsPerHour; y++) {
                calendarBlankSpaces[x][y] = new CalendarBlankSpace();
                constraints.gridx = x;
                constraints.gridy = y*periodsPerHour;
                constraints.gridheight = 1;
                constraints.fill = GridBagConstraints.BOTH;

                //calendarBlankSpaces[x][y].setMinimumSize(new Dimension(this.getWidth()/width,this.getHeight()/height));

                add(calendarBlankSpaces[x][y],constraints);
            }
        }

       /* constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 7;
        constraints.fill = GridBagConstraints.BOTH;
        add(new CalendarAppointment(Color.pink),constraints);*/


        addAppointment(9,31,60,0);
        addAppointment(10,10,60,1);
        addAppointment(11,10,60,2);
        addAppointment(12,10,60,3);
        addAppointment(13,10,60,4);
        addAppointment(14,10,60,5);
        addAppointment(15,10,60,6);
    }

    public void addAppointment(int startHour, int startMinutes, int duration,int day){
        int correctedStartHour = startHour-Calendar.getStartHour();

        int normalisedStartHour = (correctedStartHour*periodsPerHour)*periodsPerHour;
        int normalisedStartMinutes = Math.round(((float)startMinutes)/10)*periodsPerHour;
        int normalisedDuration = (duration/(60/periodsPerHour))*periodsPerHour;

        int normalisedStartTime = normalisedStartHour+normalisedStartMinutes;

        constraints.gridx = day;
        constraints.gridy = normalisedStartTime;
        constraints.gridheight = normalisedDuration;
        constraints.fill = GridBagConstraints.BOTH;

        CalendarAppointment appt = new CalendarAppointment(Color.pink,startHour,startMinutes,duration);

        add(appt,constraints);
    }
}



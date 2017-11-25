package ui.calendar;

import main.DentalPractice;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CalendarPanel extends JPanel {

    GridBagConstraints constraints;
    GridBagLayout layout;

    int width;
    int height;

    CalendarBlankSpace[][] calendarBlankSpaces;

    int periodsPerHour;

    Color backgroundColor = new Color(40,40,40);

    public CalendarPanel(int width,int height,int periodsPerHour){
        this.width = width;
        this.height = height;

        setBackground(backgroundColor);
        layout = new GridBagLayout();
        this.periodsPerHour = periodsPerHour;
        initialise();
    }

    private void initialise(){
        setLayout(layout);
        constraints = new GridBagConstraints();

        /*addAppointment(9,31,60,0);
        addAppointment(10,10,60,1);
        addAppointment(11,10,60,2);
        addAppointment(12,10,60,3);
        addAppointment(13,10,60,4);
        addAppointment(14,10,60,5);
        addAppointment(15,10,60,6);*/

        updateCalendarPanel("Dentist");
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

    //Create empty spaces for appointments to fill
    private void prepareSpaces(){
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
    }

    public void updateCalendarPanel(String staffMember){
        Connection con = DentalPractice.getCon();
        String request = "SELECT * FROM team042.StaffMember WHERE team042.StaffMember.JobTitle = '"+staffMember+"';";
        ResultSet selectedStaff;

        try {
            Statement statement = con.createStatement();
            selectedStaff = statement.executeQuery(request);

            String query = "SELECT * FROM team042.Appointment WHERE ProfessionalID = 0 ";

            while(selectedStaff.next()) {
                query += " OR  "+selectedStaff.getInt("ProfessionalID")+"" ;
            }

            query += ";";

            ResultSet selectedAppointments = statement.executeQuery(query);

            prepareSpaces();

            while(selectedAppointments.next()){
                String[] timeStrings = (selectedAppointments.getString("AppointmentStartTime")).split(":");

                int startHour = Integer.parseInt(timeStrings[0]);
                int startMinutes = Integer.parseInt(timeStrings[1]);

                addAppointment(startHour,startMinutes,60,0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



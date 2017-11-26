package ui.calendar;

import main.DentalPractice;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CalendarPanel extends JPanel {

    Date startingDate;

    GridBagConstraints constraints;
    GridBagLayout layout;

    int width;
    int height;
    int periodsPerHour;

    CalendarBlankSpace[][] calendarBlankSpaces;

    Color backgroundColor = new Color(40,40,40);

    public CalendarPanel(int width,int height,int periodsPerHour){
        this.width = width;
        this.height = height;

        //setBackground(backgroundColor);
        layout = new GridBagLayout();
        this.periodsPerHour = periodsPerHour;
        initialise();
    }

    private void initialise(){
        setLayout(layout);
        constraints = new GridBagConstraints();

        Long currentDate_java = java.util.Calendar.getInstance().getTime().getTime();
        Date currentDate = new Date(currentDate_java);

        startingDate = truncateDate(currentDate);

        System.out.println(startingDate);

        updateCalendarPanel("Dentist");
    }

    public void addAppointment(String startTime, String endTime, int day){
        int duration = getTimeDifference(startTime,endTime);

        String[] startTimeStrings = startTime.split(":");
        int startHour = Integer.parseInt(startTimeStrings[0]);
        int startMinutes = Integer.parseInt(startTimeStrings[1]);

        int correctedStartHour = startHour-Calendar.getStartHour();

        int normalisedStartHour = (correctedStartHour*periodsPerHour)*periodsPerHour;
        int normalisedStartMinutes = Math.round(((float)startMinutes)/10)*periodsPerHour;
        int normalisedDuration = (duration/(60/periodsPerHour))*periodsPerHour;

        int normalisedStartTime = normalisedStartHour+normalisedStartMinutes;

        constraints.gridx = day;
        constraints.gridy = normalisedStartTime;
        constraints.gridheight = normalisedDuration;
        constraints.fill = GridBagConstraints.BOTH;

        CalendarAppointment appt = new CalendarAppointment(new Color(100,100,100),startTime,endTime);

        add(appt,constraints);
    }

    public Date addToDate(int numberDays, Date startingDate){

        java.util.Date startingDate_java = new java.util.Date(startingDate.getTime());

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(startingDate_java);
        calendar.add(java.util.Calendar.DATE, numberDays);

        java.util.Date updatedDate_java = calendar.getTime();
        Date updatedDate = new Date(updatedDate_java.getTime());

        return updatedDate;

    }

    //Reset the starting date to the current day
    private Date truncateDate(Date date){
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY,0);
        cal.set(java.util.Calendar.MINUTE,0);
        cal.set(java.util.Calendar.SECOND,0);
        cal.set(java.util.Calendar.MILLISECOND,0);

        Long date_java = cal.getTime().getTime();

        Date truncatedDate = new Date(date_java);

        return truncatedDate;
    }

    public int getDifferenceBetweenDates(Date date1, Date date2) throws date2TooEarlyException{
        date1 = truncateDate(date1);
        date2 = truncateDate(date2);

        java.util.Date date1_java = new java.util.Date(date1.getTime());
        java.util.Date date2_java = new java.util.Date(date2.getTime());

        if(date2_java.before(date1_java)){
            throw new date2TooEarlyException();
        }

        Boolean matching;

        if(date1_java.getTime() == date2_java.getTime()){
            matching = true;
        } else {
            matching = false;
        }

        int counter = 0;

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date1_java);

        while(!matching){
            counter++;
            cal.add(java.util.Calendar.DATE,1);
            if(cal.getTime().getTime() == date2_java.getTime()){
                matching = true;
            }
        }

        return counter;
    }

    public int getTimeDifference(String time1, String time2){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        java.util.Date date1 = new Date(0);
        java.util.Date date2 = new Date(0);
        try {
            date1 = format.parse(time1);
            date2 = format.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = date2.getTime()-date1.getTime();

        int differenceInMinutes = (int)difference/1000/60;

        return differenceInMinutes;

    }

    public void goForwardDays(int days, String staffMember){
        Date newDate = addToDate(days, startingDate);
        startingDate = newDate;
        updateCalendarPanel(staffMember);
    }

    public void goBackwardDays(int days, String staffMember){
        Date newDate = addToDate(-days, startingDate);
        startingDate = newDate;
        updateCalendarPanel(staffMember);
    }

    //Create empty spaces for appointments to fill
    private void prepareSpaces(){
        removeAll();

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

        revalidate();
        repaint();
    }

    public void updateCalendarPanel(String staffMember){
        System.out.println("Updating calendar panel");
        Connection con = DentalPractice.getCon();
        String request = "SELECT * FROM team042.StaffMember WHERE team042.StaffMember.JobTitle = '"+staffMember+"';";
        ResultSet selectedStaff;

        try {
            Statement statement = con.createStatement();
            selectedStaff = statement.executeQuery(request);

            //SELECT BASED ON STAFF
            String query = "SELECT * FROM team042.Appointment WHERE (ProfessionalID = 0 ";

            while(selectedStaff.next()) {
                query += " OR  ProfessionalID = "+selectedStaff.getInt("ProfessionalID")+"" ;
            }


            //SELECT BASED ON DATE
            query += ") AND (AppointmentDate = '"+startingDate+"' ";
            Date currentDate = startingDate;

            for(int i = 0; i < 6; i++){
                currentDate = addToDate(1,currentDate);
                query += "OR AppointmentDate = '"+currentDate+"'";
            }

            query += ");"; //Close query

            ResultSet selectedAppointments = statement.executeQuery(query);

            prepareSpaces();

            while(selectedAppointments.next()){
                String startTime = selectedAppointments.getString("AppointmentStartTime");
                String endTime = selectedAppointments.getString("AppointmentEndTime");
                Date date = selectedAppointments.getDate("AppointmentDate");

                int day = 0;

                try {
                    day = getDifferenceBetweenDates(startingDate,date);
                } catch (date2TooEarlyException e) {
                    e.printStackTrace();
                }

                addAppointment(startTime,endTime,day);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Date getStartingDate() {
        return startingDate;
    }
}



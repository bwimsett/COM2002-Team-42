package main;
/**
 * Created by Ben on 09/11/2017.
 */

import staff.StaffManager;
import ui.calendar.Calendar;
import ui.WelcomeScreen;
import java.sql.*;

public class DentalPractice {
    private static WelcomeScreen welcomeScreen;
    private static Calendar calendar;
    private static StaffManager staffManager;

    private static String databaseUsername =  "team042";
    private static String databasePassword = "449c90e7";

    private static Connection con;

    public static void main (String[] args){
        initialise();
    }

    public static void initialise(){

        try {
            con = establishConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        staffManager = new StaffManager(con);
        welcomeScreen = new WelcomeScreen("Dental Practice");

    }


    private static Connection establishConnection() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connection established");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        String databaseAddress = "jdbc:mysql://stusql.dcs.shef.ac.uk/"+databaseUsername+"?user="+databaseUsername+"&password="+databasePassword;
        conn = DriverManager.getConnection(databaseAddress);

        return conn;
    }

    //GETTER SETTER
    public static Calendar getCalendar() {
        return calendar;
    }

    public static void setCalendar(Calendar calendar) {
        DentalPractice.calendar = calendar;
    }

    public static StaffManager getStaffManager() {
        return staffManager;
    }

    public static Connection getCon() {
        return con;
    }
}

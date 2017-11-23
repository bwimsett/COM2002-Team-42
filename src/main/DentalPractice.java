package main;
/**
 * Created by Ben on 09/11/2017.
 */

import ui.Calendar;
import ui.WelcomeScreen;
import java.sql.*;

public class DentalPractice {
    private static WelcomeScreen welcomeScreen;
    private static Calendar calendar;

    public static void main (String[] args){
        initialise();
    }

    public static void initialise(){


        welcomeScreen = new WelcomeScreen("Dental Practice");

    }

    //GETTER SETTER
    public static Calendar getCalendar() {
        return calendar;
    }

    public static void setCalendar(Calendar calendar) {
        DentalPractice.calendar = calendar;
    }
}

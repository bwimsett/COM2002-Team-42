package ui;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeSpinner extends JSpinner {
    private SpinnerDateModel spinnerDateModel;
    Date startTime;

    /*public TimeSpinner(Comparable<SimpleDateFormat> startTime, Comparable<SimpleDateFormat> endTime){
        spinnerDateModel = new SpinnerDateModel(startTime,startTime,endTime, Calendar.HOUR);
        DateEditor de = new JSpinner.DateEditor(this,"hh:mm a");
        startTime = new Date();
    }*/

}

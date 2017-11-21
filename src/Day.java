import ui.CalendarAppointment;

import java.util.ArrayList;

/**
 * Created by Ben on 20/11/2017.
 */
public class Day {
    private ArrayList<CalendarAppointment> appointments;

    public void addAppointment(CalendarAppointment appointment){
        appointments.add(appointment);
    }

    public void cancelAppointment(CalendarAppointment appointment) {
        appointments.remove(appointment);
    }
}

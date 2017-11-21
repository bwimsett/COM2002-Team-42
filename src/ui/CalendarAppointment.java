package ui;

/**
 * Created by Ben on 20/11/2017.
 */
public class CalendarAppointment {
    private int patientID;
    private int professionalID;

    public CalendarAppointment(int patientID, int professionalID){
        this.patientID = patientID;
        this.professionalID = professionalID;
    }

    //ACCESSOR/MUTATOR
    public int getPatientID() {
        return patientID;
    }

    public int getProfessionalID() {
        return professionalID;
    }

    public void setProfessionalID(int professionalID) {
        this.professionalID = professionalID;
    }
}

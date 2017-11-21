package staff;

/**
 * Created by Ben on 09/11/2017.
 */
public class DentalProfessional extends StaffMember {

    private String jobTitle;
    private int professionalID;

    //ACCESSOR/MUTATOR
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getProfessionalID() {
        return professionalID;
    }
}

package staff;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StaffManager {

    Connection con;

    public StaffManager(Connection con){
        this.con = con;
    }

    public int getProfessionalID(String jobTitle){
        String query = "SELECT ProfessionalID FROM StaffMember WHERE JobTitle = '"+jobTitle+"'";
        int professionalID;

        try {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);
            result.next();
            professionalID = result.getInt("ProfessionalID");
            return professionalID;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}

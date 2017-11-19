/**
 * Created by Ben on 09/11/2017.
 */
import UI.MainContainer;

public class DentalPractice {
    private static MainContainer mainContainer;

    public static void main (String[] args){
        initialise();
    }

    public static void initialise(){
        mainContainer = new MainContainer("Dental Practice");
        mainContainer.setVisible(true);
    }
}

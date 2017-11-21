/**
 * Created by Ben on 09/11/2017.
 */

import ui.WelcomeScreen;

public class DentalPractice {
    private static WelcomeScreen welcomeScreen;

    public static void main (String[] args){
        initialise();
    }

    public static void initialise(){
        welcomeScreen = new WelcomeScreen("Dental Practice");
    }
}

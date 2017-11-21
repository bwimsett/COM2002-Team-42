package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ben on 21/11/2017.
 */
public class AppointmentForm extends JDialog{

     public AppointmentForm(){
         initialise();

     }

     private void initialise(){
         setSize(200,200);
         setTitle("Book Appointment");
         setResizable(false);
         setVisible(true);
     }
}

package ui;

import javax.swing.*;

/**
 * Created by Ben on 20/11/2017.
 */
public class Calendar extends JFrame {

    public Calendar(String title){
        this.setTitle(title);
        this.initialise();
    }

    void initialise(){
        display();
    }

    void display(){
        setVisible(true);
    }

}

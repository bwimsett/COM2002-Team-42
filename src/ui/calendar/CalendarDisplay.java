package ui.calendar;

import javax.swing.*;
import java.awt.*;

public class CalendarDisplay extends JPanel {

    private JPanel topRow;
    private JComboBox<String> staffType = new JComboBox(new String[]{"Dentist","Hygienist"});

    private CalendarPanel calendarPanel;

    private JPanel weekButtonsPanel;
    private JButton prevWeek;
    private JButton nextWeek;

    JFrame container;

    public CalendarDisplay(JFrame container){
        this.container = container;
        initialise();
    }

    private void initialise(){

            this.setLayout(new BorderLayout());
            //this.setSize(700,850);

            topRow = new JPanel();
            calendarPanel = new CalendarPanel(7,8,6);
            weekButtonsPanel = new JPanel();

            topRow.add(staffType);

            //calendarPanel.setSize(500,500);

            prevWeek = new JButton("Prev Week");
            nextWeek = new JButton("Next Week");
            weekButtonsPanel.add(prevWeek);
            weekButtonsPanel.add(nextWeek);

            add(topRow,BorderLayout.NORTH);
            add(calendarPanel,BorderLayout.CENTER);
            add(weekButtonsPanel,BorderLayout.SOUTH);
    }

}

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
            this.setSize(500,550);

            topRow = new JPanel();
            calendarPanel = new CalendarPanel();
            weekButtonsPanel = new JPanel();

            topRow.add(staffType);

            calendarPanel.setSize(500,500);
            calendarPanel.setBackground(Color.BLACK);

            prevWeek = new JButton("Prev Week");
            nextWeek = new JButton("Next Week");
            weekButtonsPanel.add(prevWeek);
            weekButtonsPanel.add(nextWeek);

            add(topRow,BorderLayout.NORTH);
            add(calendarPanel,BorderLayout.CENTER);
            add(weekButtonsPanel,BorderLayout.SOUTH);
    }

}
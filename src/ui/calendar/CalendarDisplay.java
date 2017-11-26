package ui.calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

            staffType.addActionListener(new StaffTypeActionListener(calendarPanel,staffType));
            topRow.add(staffType);

            //calendarPanel.setSize(500,500);

            prevWeek = new JButton("Prev Week");
            prevWeek.addActionListener(new PrevWeekActionListener(calendarPanel,this));
            nextWeek = new JButton("Next Week");
            nextWeek.addActionListener(new NextWeekActionListener(calendarPanel,this));
            weekButtonsPanel.add(prevWeek);
            weekButtonsPanel.add(nextWeek);

            add(topRow,BorderLayout.NORTH);
            add(calendarPanel,BorderLayout.CENTER);
            add(weekButtonsPanel,BorderLayout.SOUTH);
    }

    public void refreshCalendarPanel(){
        String selectedStaff = (String)staffType.getSelectedItem();

        calendarPanel.updateCalendarPanel(selectedStaff);
    }

    public CalendarPanel getCalendarPanel() {
        return calendarPanel;
    }

    public JComboBox<String> getStaffType() {
        return staffType;
    }
}

class PrevWeekActionListener implements ActionListener {

    CalendarPanel calendarPanel;
    CalendarDisplay calendarDisplay;

    public PrevWeekActionListener(CalendarPanel calendarPanel, CalendarDisplay calendarDisplay){
        this.calendarPanel = calendarPanel;
        this.calendarDisplay = calendarDisplay;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        calendarPanel.goBackwardDays(7,(String)calendarDisplay.getStaffType().getSelectedItem());
    }
}

class NextWeekActionListener implements ActionListener {

    CalendarPanel calendarPanel;
    CalendarDisplay calendarDisplay;

    public NextWeekActionListener(CalendarPanel calendarPanel, CalendarDisplay calendarDisplay){
        this.calendarPanel = calendarPanel;
        this.calendarDisplay = calendarDisplay;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        calendarPanel.goForwardDays(7,(String)calendarDisplay.getStaffType().getSelectedItem());
    }
}


class StaffTypeActionListener implements ActionListener {

    CalendarPanel calendarPanel;
    JComboBox<String> staffType;

    public StaffTypeActionListener(CalendarPanel calendarPanel, JComboBox<String> staffType){
        this.calendarPanel = calendarPanel;
        this.staffType = staffType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String chosenStaff = (String)staffType.getSelectedItem();
        calendarPanel.updateCalendarPanel(chosenStaff);
    }


}
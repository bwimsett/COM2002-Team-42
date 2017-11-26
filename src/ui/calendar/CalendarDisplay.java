package ui.calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class CalendarDisplay extends JPanel {

    private JPanel topRow;
    private JPanel bottomRow;
    private JComboBox<String> staffType = new JComboBox(new String[]{"Dentist","Hygienist"});

    private CalendarPanel calendarPanel;

    private JPanel currentDatesPanel;
    private JLabel[] currentDatesLabels;


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
            bottomRow = new JPanel();
            bottomRow.setLayout(new BorderLayout());

            currentDatesPanel = new JPanel();
            calendarPanel = new CalendarPanel(7,8,6, this);
            weekButtonsPanel = new JPanel();

            staffType.addActionListener(new StaffTypeActionListener(calendarPanel,staffType));
            topRow.add(staffType);
            //topRow.add(topRowTop);

            currentDatesPanel.setLayout(new GridLayout());
            //currentDatesPanel.add(new JButton("test"));

            prevWeek = new JButton("Prev Week");
            prevWeek.addActionListener(new PrevWeekActionListener(calendarPanel,this));
            nextWeek = new JButton("Next Week");
            nextWeek.addActionListener(new NextWeekActionListener(calendarPanel,this));
            weekButtonsPanel.add(prevWeek);
            weekButtonsPanel.add(nextWeek);

            bottomRow.add(new JSeparator(SwingConstants.HORIZONTAL),BorderLayout.NORTH);
            bottomRow.add(currentDatesPanel,BorderLayout.CENTER);
            bottomRow.add(weekButtonsPanel,BorderLayout.SOUTH);


            add(topRow,BorderLayout.NORTH);
            add(calendarPanel,BorderLayout.CENTER);
            add(bottomRow,BorderLayout.SOUTH);
            //add(currentDatesPanel,BorderLayout.SOUTH);
            //add(weekButtonsPanel,BorderLayout.PAGE_END);
    }

    public void refreshCalendarPanel(){
        String selectedStaff = (String)staffType.getSelectedItem();

        calendarPanel.updateCalendarPanel(selectedStaff);
    }

    public void updateDates(Date[] currentDates){
        currentDatesLabels = new JLabel[currentDates.length];

        currentDatesPanel.removeAll();

        for(int i = 0; i < currentDates.length; i++){
            currentDatesLabels[i] = new JLabel(currentDates[i].toString());
            currentDatesLabels[i].setHorizontalAlignment(JLabel.CENTER);
            currentDatesPanel.add(currentDatesLabels[i]);
        }

        revalidate();
        repaint();
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
        calendarPanel.deselectAppointment();
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
        calendarPanel.deselectAppointment();
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
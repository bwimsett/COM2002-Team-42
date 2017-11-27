package ui;

import ui.calendar.CalendarAppointment;

import javax.swing.*;

public class CompleteAppointmentForm extends JDialog {

    CalendarAppointment appointment;

    JPanel costPanel;
    JTextField costField;

    JPanel lastRow;
    JButton cancelButton;
    JButton completeButton;

    public CompleteAppointmentForm(/*CalendarAppointment appointment*/){
        this.appointment = appointment;

        initialise();
    }

    public void initialise(){
        setVisible(true);
        setSize(300,200);

        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        costPanel = new JPanel();
        costField = new JTextField(10);

        costPanel.add(new JLabel("Cost"));
        costPanel.add(costField);

        lastRow = new JPanel();

        cancelButton = new JButton("Cancel");
        completeButton = new JButton("Complete");
        lastRow.add(cancelButton);
        lastRow.add(completeButton);

        add(costPanel);
        add(lastRow);
    }


}

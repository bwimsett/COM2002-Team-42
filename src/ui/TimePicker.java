package ui;

import javax.swing.*;

public class TimePicker extends JPanel {

    JComboBox<Integer> startHour = new JComboBox(new Integer[]{9,10,11,12,13,14,15,16});
    JComboBox<Integer> startMinutes = new JComboBox(new Integer[]{0,15,30,45});
    JComboBox<Integer> duration = new JComboBox(new Integer[]{20,30,45,60,120,180,480});

    public TimePicker(){
        this.add(new JLabel("Time"));
        this.add(startHour);
        this.add(startMinutes);
        this.add(new JLabel("Duration"));
        this.add(duration);
    }

    public JComboBox<Integer> getStartHour() {
        return startHour;
    }

    public JComboBox<Integer> getStartMinutes() {
        return startMinutes;
    }

    public JComboBox<Integer> getDuration() {
        return duration;
    }

}

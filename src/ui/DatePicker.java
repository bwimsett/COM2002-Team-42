package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatePicker extends JPanel{
	private JPanel datePickerPanel = new JPanel();
	private JComboBox<Integer> dayComboBox = new JComboBox(new Integer[]{0});
	private JComboBox<Integer> yearComboBox = new JComboBox(new Integer[]{0});
    private JComboBox<String> monthComboBox= new JComboBox(new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"});
	private JLabel label;

    public DatePicker(int startingYear, int yearRange, String labelText) {
		updateYearPicker(startingYear,yearRange);
		updateDayPicker((String)monthComboBox.getSelectedItem(),(int)yearComboBox.getSelectedItem());

    	monthComboBox.addActionListener(new monthChangeListener(monthComboBox,this));
    	yearComboBox.addActionListener(new monthChangeListener(monthComboBox,this));

    	label = new JLabel(labelText);

		add(label);
		add(dayComboBox);
		add(monthComboBox);
		add(yearComboBox);
    }
    
    public void updateDayPicker(String month, int year) {
	 	int pickerLength = 0;
	 
	 	switch(month) {
	 	case "January":
	 	case "March":
	 	case "May":
	 	case "July":
	 	case "August":
	 	case "October":
	 	case "December":
	 		pickerLength = 31;
	 		break;
	 	case "April":
	 	case "June":
	 	case "September":
	 	case "November":
	 		pickerLength = 30;
	 		break;
	 	case "February":
	 		if(year % 4 == 0) {
	 			pickerLength = 29;
	 		} else {
	 			pickerLength = 28;
	 		}
	 		break;
	 	default:
	 		pickerLength = 31;
	 		break;
	 	}

		dayComboBox.removeAllItems();

	 	for(int i = 0; i < pickerLength; i++) {
			dayComboBox.addItem(i+1);
	 	}
 }

    public void updateYearPicker(int startingYear, int range) {
		int pickerLength = range;

		yearComboBox.removeAllItems();

		for(int i = 0; i < range;i++){
			yearComboBox.addItem(startingYear+i);
		}
    }

    public int monthToInt(String month){
    	switch(month){
			case "January": return 1;
			case "February": return 2;
			case "March": return 3;
			case "April": return 4;
			case "May": return 5;
			case "June": return 6;
			case "July": return 7;
			case "August": return 8;
			case "September": return 9;
			case "October": return 10;
			case "November": return 11;
			case "December": return 12;
			default: return 0;
		}
	}

	public JComboBox<Integer> getDayComboBox() {
		return dayComboBox;
	}

	public JComboBox<Integer> getYearComboBox() {
		return yearComboBox;
	}

	public JComboBox<String> getMonthComboBox() {
		return monthComboBox;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}
}

class monthChangeListener implements ActionListener {

	JComboBox<String> monthComboBox;
	DatePicker datePicker;

	public monthChangeListener(JComboBox<String> monthComboBox, DatePicker datePicker){
		 this.monthComboBox = monthComboBox;
		 this.datePicker = datePicker;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Day picker updating");
		datePicker.updateDayPicker((String)monthComboBox.getSelectedItem(),(int)datePicker.getYearComboBox().getSelectedItem());
	}
}
package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class addScheduleController {
	
	//Create the 
	@FXML DatePicker datePicker;
	@FXML TextField timeTextField;
	
	@FXML 
	private String handleTimeInput() {
		String timeInput = timeTextField.getText();
		
		//Converting input String to localTime
		
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
		//LocalTime localTime = LocalTime.parse(timeInput, formatter);
		
		return timeInput;
	}
	
	@FXML 
	private void getTimeInput(ActionEvent e) {
		String input = handleTimeInput();
		System.out.println(input);
	}
}

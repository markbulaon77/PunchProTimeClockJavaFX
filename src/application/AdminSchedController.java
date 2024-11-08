package application;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminSchedController {

	@FXML private TableView<PunchPro_Employee> employeeTable;
	@FXML private TableColumn<PunchPro_Employee, Integer> colEmployeeNumber;
	@FXML private TableColumn<PunchPro_Employee, String> colFirstName;
	@FXML private TableColumn<PunchPro_Employee, String> colLastName;
	@FXML private DatePicker datePicker;
	@FXML private TextField startShiftTF, firstBreakTF, lunchBreakTF, secondBreakTF, endShiftTF;
	@FXML private Text year;
	@FXML private Text month;
	@FXML private FlowPane calendar;
	ZonedDateTime dateFocus, today;
	
	private Connection conn;
	private ObservableList<PunchPro_Employee> employeeData;
	private ViewUsersDAO viewEmployeeDB;
	private DatabaseAddScheduleDAO schedAddDB;
	
	public void setDatabaseConnection(DatabaseConnection dbConnection) throws ClassNotFoundException {
		this.conn = dbConnection.connect_to_database();	
		this.viewEmployeeDB = new ViewUsersDAO(conn);
		this.schedAddDB = new DatabaseAddScheduleDAO(conn);
	}
	
	@FXML
	public void initialize() {
		dateFocus = ZonedDateTime.now();
		today = ZonedDateTime.now();
		configurableTableColumns();
		employeeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection !=null) {
				int userNumber = newSelection.getEmployee_number();
				drawCalendar(userNumber);
			}
		});
	}
	
	private void configurableTableColumns() {
		colEmployeeNumber.setCellValueFactory(new PropertyValueFactory<>("employee_number"));
		colFirstName.setCellValueFactory(new PropertyValueFactory<>("employee_first_name"));
		colLastName.setCellValueFactory(new PropertyValueFactory<>("employee_last_name"));
	}
	
	public void viewEmployeeList() {
		if(viewEmployeeDB != null) {
			employeeData = FXCollections.observableArrayList(viewEmployeeDB.viewUsers2());
			employeeTable.setItems(employeeData);
		}else {
			System.out.println("VIEWusers DAO is not initialized.");
		}
	}
	
	@FXML
	private void addScheduleForm(ActionEvent e) throws IOException {
		PunchPro_Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
		
		if(selectedEmployee !=null) {
			PunchPro_Schedule newSchedule = new PunchPro_Schedule(
			selectedEmployee.getEmployee_number(),
			selectedEmployee.getEmployee_first_name(),
			selectedEmployee.getEmployee_last_name(),
			datePicker.getValue(),
			startShiftTF.getText(),
			firstBreakTF.getText(),
			lunchBreakTF.getText(),
			secondBreakTF.getText(),
			endShiftTF.getText()
			);
				
			if(datePicker.getValue() == null) {
				System.out.println("Please select a date");
				return;
			}
			
			if(schedAddDB.addUserSchedule(newSchedule)) {
				System.out.println("Schedule added sucessfully.");
			}else {
				System.out.println("Unable to add user.");
			}
 		}else {
 			System.out.println("Please select a new an employee first...");
 		}
	}
	
	/*
	private boolean areAllFieldsEmpty() {
		return startShiftTF.getText().isEmpty() || firstBreakTF.getText().isEmpty() || secondBreakTF.getText().isEmpty() || lunchBreakTF.getText().isEmpty() || secondBreakTF.getText().isEmpty() || endShiftTF.getText().isEmpty();
	}*/
	
	@FXML
	public void backToAdminDashBoard(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("adminDashBoard.fxml"));
		Parent adminDashBoard = loader.load();
		
		Scene backToAdminDashBoard = new Scene(adminDashBoard);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(backToAdminDashBoard);
		window.show();
		
		System.out.println("Going back to admin dashboard...");
	}
	
	@FXML
	public void backOneMonth(ActionEvent e) {
		dateFocus = dateFocus.minusMonths(1);
		calendar.getChildren().clear();
		drawCalendar(0);
	}
	
	@FXML
	public void forwardOneMonth(ActionEvent e) {
		dateFocus = dateFocus.plusMonths(1);
		calendar.getChildren().clear();
		drawCalendar(0);
	}
	
	public void drawCalendar(int userNumber) {
		year.setText(String.valueOf(dateFocus.getYear()));
		month.setText(String.valueOf(dateFocus.getMonth()));
		
		double calendarWidth = calendar.getPrefWidth();	
		double calendarHeight = calendar.getPrefHeight();
		double strokeWidth = 1;
		double spacingH = calendar.getHgap();
		double spacingV = calendar.getVgap();
		//String days[] = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
		
		int monthMaxDate = dateFocus.getMonth().maxLength();
		
		if(dateFocus.getYear() % 4 !=0 && monthMaxDate == 29) {
			monthMaxDate = 28;
		}
		
        int dateOffSet = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0,dateFocus.getZone()).getDayOfWeek().getValue();
        
        calendar.getChildren().clear();
        
        //Fetch the user's schedule for the current month.
        DatabaseViewUsersScheduleDAO scheduleDAO = new DatabaseViewUsersScheduleDAO(conn);
        ArrayList<PunchPro_Schedule> scheduleList = new ArrayList<>();
        
        //Looping through each day of the month.
        for(int day = 1; day <= monthMaxDate; day++) {
        	LocalDate workDate = LocalDate.of(dateFocus.getYear(), dateFocus.getMonthValue(), day);
        	ArrayList<PunchPro_Schedule> dailySchedule = scheduleDAO.viewUsersSchedule(userNumber, workDate, "Start Time: ", "End Time: "); //updating to actual shift times.
        	
        	
        	//StackPane for eachDay.
        	StackPane stackPane = new StackPane();
        	Rectangle rectangle = new Rectangle();
        	rectangle.setFill(Color.TRANSPARENT);
        	rectangle.setStroke(Color.BLACK);
        	rectangle.setStrokeWidth(strokeWidth);
        	
        	double rectangleWidth = (calendarWidth / 8) - strokeWidth - spacingH;
        	double rectangleHeight = (calendarHeight / 8) - strokeWidth - spacingV;
        	rectangle.setWidth(rectangleWidth);
        	rectangle.setHeight(rectangleHeight);
        	
        	stackPane.getChildren().add(rectangle);
        	
        	// Determine the position in the calendar grid.
        	int row = (day + dateOffSet - 1 ) / 7;
        	int col = (day + dateOffSet - 1 ) % 7;
        	
        	//Add date number
        	Text dateText = new Text(String.valueOf(day));
        	dateText.setTranslateY(-rectangleHeight / 2 * 0.75);
        	stackPane.getChildren().add(dateText);
        	
        	//Check if there is a schedule for this date.
        	if(!dailySchedule.isEmpty()) {
        		StringBuilder scheduleDetails = new StringBuilder();
        		
        		for(PunchPro_Schedule schedule : dailySchedule) {
        			scheduleDetails.append("Shift: ").append(schedule.getShift_start_time()).append("\n");
        		}
        		
        		
        		Text scheduleText = new Text(scheduleDetails.toString());
        		scheduleText.setTranslateY(rectangleHeight / 4); // Adjust if needed...
        		stackPane.getChildren().add(scheduleText);
        	}
        	
        	//HighLight today's date/
        	if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == day) {
        		rectangle.setStroke(Color.BLUE);
        	}
        	
        	//Add the stackPane to calendar grid
        	calendar.getChildren().add(stackPane);
        }   
        
        /*
        for(int i = 0; i < 6; i++) {
        	for(int j = 0; j < 7; j++) {
        		
        		StackPane stackPane = new StackPane();
        		Rectangle rectangle = new Rectangle();
        		rectangle.setFill(Color.TRANSPARENT);
        		rectangle.setStroke(Color.BLACK);
        		rectangle.setStrokeWidth(strokeWidth);
        		
        		double rectangleWidth = (calendarWidth/7) - strokeWidth - spacingH;
        		rectangle.setWidth(rectangleWidth);
        		
        		double rectangleHeight = (calendarHeight/7) - strokeWidth - spacingV;
        		rectangle.setHeight(rectangleHeight);
        		
        		stackPane.getChildren().add(rectangle);
        		
        		int calculatedDate = (j+1)+(7*i);
        		if(calculatedDate > dateOffset) {
        			int currentDate = calculatedDate - dateOffset;
        			if(currentDate <= monthMaxDate){
        				Text date = new Text(String.valueOf(currentDate));
        				double textTranslationY = - (rectangleHeight/2) * 0.75;
        				date.setTranslateY(textTranslationY);
        				stackPane.getChildren().add(date);
        			}
        			
        			if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                         rectangle.setStroke(Color.BLUE);
                    }
        		}
   
        		calendar.getChildren().add(stackPane);
        	}
        	
        }*/ 
        //Old loop...
	}
}
	

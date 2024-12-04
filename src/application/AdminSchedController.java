package application;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
	@FXML private DatePicker datePicker; // Get from "from" date;
	@FXML private DatePicker datePicker2; // Get the "to" date;
	@FXML private TextField startShiftTF, firstBreakTF, lunchBreakTF, secondBreakTF, endShiftTF;
	@FXML private Text year;
	@FXML private Text month;
	@FXML private FlowPane calendar;
	ZonedDateTime dateFocus, today;
	
	private Connection conn;
	private ObservableList<PunchPro_Employee> employeeData;
	private ViewUsersDAO viewEmployeeDB;
	private DatabaseAddScheduleDAO schedAddDB;
	private DatabaseUpdateUserScheduleDAO schedUpdateDB;
	private DatabaseDelScheduleDAO schedDelDB;
	private PunchPro_Employee selectedEmployee;
	private PunchPro_Schedule newSchedule;
	private int userNumber;
	private LocalDate fromDate, toDate;
	
	public void setDatabaseConnection(DatabaseConnection dbConnection) throws ClassNotFoundException {
		this.conn = dbConnection.connect_to_database();	
		this.viewEmployeeDB = new ViewUsersDAO(conn);
		this.schedAddDB = new DatabaseAddScheduleDAO(conn);
		this.schedUpdateDB = new DatabaseUpdateUserScheduleDAO(conn);
		this.schedDelDB = new DatabaseDelScheduleDAO(conn);
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
	
	//METHOD TO GENERATE DATES BETWEEN 'FROM' and 'TO'
	private List<LocalDate> generateDateRange(LocalDate fromDate, LocalDate toDate) {
	    List<LocalDate> dateList = new ArrayList<>();

	    if (toDate == null || fromDate.equals(toDate)) {
	        // If the end date is null or the same as the start date, return a single date
	        dateList.add(fromDate);
	    } else {
	        // Add all dates in the range
	        while (!fromDate.isAfter(toDate)) {
	            dateList.add(fromDate);
	            fromDate = fromDate.plusDays(1);
	        }
	    }

	    return dateList;
	}
	
	@FXML
	private void addScheduleForm(ActionEvent e) throws IOException {
		selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
		
		if(selectedEmployee != null) {
			 fromDate = datePicker.getValue();
			 toDate = datePicker2.getValue();
			
			if(fromDate == null) {
				alert();
				return;
			}
			
			//GENERATE THE RANGE OF DATE, INCLUDING THE SINGLE DATE HANDLING...
			List<LocalDate> dateRange = generateDateRange(fromDate, toDate);
			for(LocalDate date : dateRange) {
				 newSchedule = new PunchPro_Schedule(
			                selectedEmployee.getEmployee_number(),
			                selectedEmployee.getEmployee_first_name(),
			                selectedEmployee.getEmployee_last_name(),
			                date,
			                date, // Use the same date for single dates
			                startShiftTF.getText(),
			                firstBreakTF.getText(),
			                lunchBreakTF.getText(),
			                secondBreakTF.getText(),
			                endShiftTF.getText()
						 );

				 //ADD SCHEDULE TO DATABASE.
				 if(schedAddDB.addUserSchedule(newSchedule)) {
					 System.out.println("Schedule added for: " + date);
				 }else {
					 System.out.println("Unable to add schedule for: " + date);
				 }
			}
			
			userNumber = selectedEmployee.getEmployee_number();
	        drawCalendar(userNumber);
		}else {
	        System.out.println("Please select an employee first...");
	    }	
	}
	
	@FXML
	private void updateUserSchedule() {
		selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
		
		if(selectedEmployee != null) {
			fromDate = datePicker.getValue();
			toDate = datePicker2.getValue();
			
			if(fromDate == null) {
				alert();
				return;
			}
			
			List<LocalDate> dateRange = generateDateRange(fromDate, toDate);
			for(LocalDate date : dateRange) {
				newSchedule = new PunchPro_Schedule(
						selectedEmployee.getEmployee_number(),
						selectedEmployee.getEmployee_first_name(),
						selectedEmployee.getEmployee_last_name(),
						date,
						date,
						startShiftTF.getText(),
						firstBreakTF.getText(), 
						lunchBreakTF.getText(), 
						secondBreakTF.getText(), 
						endShiftTF.getText()
						);
				
				if(schedUpdateDB.updateUserSchedule(newSchedule)) {
					System.out.println("Date Updated: " + date);
				}else {
					System.out.println("Unable to update date: " + date);
				}
				
				userNumber = selectedEmployee.getEmployee_number();
				drawCalendar(userNumber);
			}
		}else {
			System.out.println("Please select an employee first.");
		}
	}
	
	
	@FXML
	private void deleteUserSchedule() {
		selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
		
		if(selectedEmployee != null) {
			 fromDate = datePicker.getValue();
			 toDate = datePicker2.getValue();
			
			if(fromDate == null) {
				alert();
				return;
			}
			
			//GENERATE THE RANGE OF DATE, INCLUDING THE SINGLE DATE HANDLING...
			List<LocalDate> dateRange = generateDateRange(fromDate, toDate);
			for(LocalDate date : dateRange) {
				  newSchedule = new PunchPro_Schedule(
			                selectedEmployee.getEmployee_number(),
			                selectedEmployee.getEmployee_first_name(),
			                selectedEmployee.getEmployee_last_name(),
			                date,
			                date // Use the same date for single dates
						 );
				//DELETE SCHEDULE FROM DATABASE.
				if(schedDelDB.delUserSchedule(newSchedule)) {
					System.out.println("Schedule selected is deleted" + date);
				}else {
					System.out.println("Unable to delete selected" + date);
				}
			}
			
			userNumber = selectedEmployee.getEmployee_number();
			drawCalendar(userNumber);
		}else {
			System.out.println("Please select an Employee first.");
		}
	}
	
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
	public void goToCrudForm(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("adminCrudForm.fxml"));
		Parent crudForm = loader.load();
		
		Scene goToCrudForm = new Scene(crudForm);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(goToCrudForm);
		window.show();
	}
	
	
	@FXML
	public void backOneMonth(ActionEvent e) {
		dateFocus = dateFocus.minusMonths(1);
		calendar.getChildren().clear();
		drawCalendar(-1);
	}
	
	@FXML
	public void forwardOneMonth(ActionEvent e) {
		dateFocus = dateFocus.plusMonths(1);
		calendar.getChildren().clear();
		drawCalendar(-1);
	}
	
	public void drawCalendar(int userNumber) {
		
	    year.setText(String.valueOf(dateFocus.getYear()));
	    month.setText(String.valueOf(dateFocus.getMonth()));

	    double calendarWidth = calendar.getPrefWidth();
	    double calendarHeight = calendar.getPrefHeight();
	    double strokeWidth = 0.5;
	    double spacingH = calendar.getHgap();
	    double spacingV = calendar.getVgap();

	    int monthMaxDate = dateFocus.getMonth().maxLength();
	    if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
	        monthMaxDate = 28;
	    }

	    // Fetch user's schedule for the current month
	    DatabaseViewUsersScheduleDAO scheduleDAO = new DatabaseViewUsersScheduleDAO(conn);
	    ArrayList<PunchPro_Schedule> scheduleList = scheduleDAO.viewUsersScheduleForMonth(userNumber, dateFocus.getYear(), dateFocus.getMonthValue());

	    int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue();
	    calendar.getChildren().clear();

	    for (int i = 0; i < 6; i++) {
	        for (int j = 0; j < 7; j++) {
	            StackPane stackPane = new StackPane();
	            Rectangle rectangle = new Rectangle();
	            rectangle.setFill(Color.TRANSPARENT);
	            rectangle.setStroke(Color.BLACK);
	            rectangle.setStrokeWidth(strokeWidth);

	            double rectangleWidth = (calendarWidth / 8) - strokeWidth - spacingH;
	            rectangle.setWidth(rectangleWidth);

	            double rectangleHeight = (calendarHeight / 8) - strokeWidth - spacingV;
	            rectangle.setHeight(rectangleHeight);
	            
	            stackPane.getChildren().add(rectangle);

	            int calculatedDate = (j + 1) + (7 * i);
	            if (calculatedDate > dateOffset) {
	                int currentDate = calculatedDate - dateOffset;
	                if (currentDate <= monthMaxDate) {
	                    Text dateText = new Text(String.valueOf(currentDate));
	                    dateText.setTranslateY(-(rectangleHeight / 2) * 0.75);
	                    stackPane.getChildren().add(dateText);
 
	                    // Check of the employee schedules for the current date.
	                    LocalDate currentLocalDate = LocalDate.of(dateFocus.getYear(), dateFocus.getMonthValue(), currentDate);
	                    for (PunchPro_Schedule schedule : scheduleList) {
	                        if (schedule.getWork_date().equals(currentLocalDate)) {
	                            Text scheduleText = new Text(schedule.getShift_start_time() + "\n" 
	                            		+ schedule.getShift_clock_outTime());	
	                            scheduleText.setTranslateY((rectangleHeight / 2) * 0.5); // Position below the date
	                            stackPane.getChildren().add(scheduleText);
   
	                            break;
	                        }
	                    }
	                    // Highlight today's date
	                    if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
	                        rectangle.setStroke(Color.BLUE);
	                    }
	                }
	            }
	            calendar.getChildren().add(stackPane);
	        }
	    }
	} 
	
	private void alert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("No Date Selected");
		alert.setHeaderText("Please select a date");
		alert.setContentText("Please select a date to be added");
		alert.showAndWait();
	}
	
}
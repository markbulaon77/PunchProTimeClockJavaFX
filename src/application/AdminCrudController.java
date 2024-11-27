package application;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminCrudController {
	
	@FXML Label totalEmployeeLbl; 
	@FXML TextField empFirstNameTF, empLastNameTF, empContactNumTF, empUserNameTF, empJobDescTF;
	@FXML private TableView<PunchPro_Employee> employeeTable;
	@FXML private TableColumn<PunchPro_Employee, Integer> colEmployeeNumber;
	@FXML private TableColumn<PunchPro_Employee, String> colFirstName, colLastName, colContactNum,
	colUserName, colJobDescription;
	
	private Connection conn;
	private DatabaseUserAddDAO userAddDB;
	private ViewUsersDAO viewEmployeeDB;
	private DatabaseDeleteUsersDAO delEmployeeDB;
	private DatabaseUpdateUsersDAO updateEmployeeDB;
	private ObservableList<PunchPro_Employee> employeeData;
	private ArrayList<PunchPro_Employee> employeeList;
	
	public void setDatabaseConnection(DatabaseConnection dbConnection) throws ClassNotFoundException   {
		this.conn = dbConnection.connect_to_database(); //Created a single instance of the sql connection to streamline it...
		this.userAddDB = new DatabaseUserAddDAO(conn);
		this.viewEmployeeDB = new ViewUsersDAO(conn);
		this.delEmployeeDB = new DatabaseDeleteUsersDAO(conn);
		this.updateEmployeeDB = new DatabaseUpdateUsersDAO(conn);
		System.out.println("Database connected and DAO's initiliazed"); 
	}
	
	@FXML
	public void initialize() {
	    //Adding a listener to the tableView for row selection.
	    employeeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	    	if(newSelection !=null) populateFields(newSelection);
	    });
	    configureTableColumns();
	}
	
	@FXML
	public void addNewEmployee(ActionEvent e) 
	{
		if(areAllFieldsEmpty()){
			System.out.println("Please complete all details...");
		}else{
			PunchPro_Employee newEmployee = new PunchPro_Employee(
					empFirstNameTF.getText(),
					empLastNameTF.getText(),
					empContactNumTF.getText(),
					empUserNameTF.getText(),
					empJobDescTF.getText()
					);
			if(userAddDB.addNewUser(newEmployee)) {
				System.out.println("User added sucessfully.");
				refreshEmployeeList();
			}else {
				System.out.println("Unable to add user.");
			}
		}
	}
	
	//Check if all fields are !notEmpty.
	private boolean areAllFieldsEmpty() 
	{	
		return empFirstNameTF.getText().isEmpty() || empLastNameTF.getText().isEmpty() || 
				empContactNumTF.getText().isEmpty() || empUserNameTF.getText().isEmpty() || 
				empJobDescTF.getText().isEmpty();
	}
	
	//Map to PunchPro_Employee getter methods.
	private void configureTableColumns() 
	{	
		colEmployeeNumber.setCellValueFactory(new PropertyValueFactory<>("employee_number"));
		colFirstName.setCellValueFactory(new PropertyValueFactory<>("employee_first_name"));
		colLastName.setCellValueFactory(new PropertyValueFactory<>("employee_last_name"));
		colContactNum.setCellValueFactory(new PropertyValueFactory<>("employee_contact_number"));
		colUserName.setCellValueFactory(new PropertyValueFactory<>("employee_username"));
		colJobDescription.setCellValueFactory(new PropertyValueFactory<>("employee_job_description"));
	}
	
	private void refreshEmployeeList() 
	{
		viewEmployeeList();
	}
	
	@FXML
	public void deleteEmployee() {
		PunchPro_Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
		if(selectedEmployee !=null){
			delEmployeeDB.deleteUser(selectedEmployee);// Delete from database...			
			//Remove from the Observable List.
			employeeData.remove(selectedEmployee);
		}	
		employeeTable.refresh();
	}
	
	@FXML
	public void updateEmployee() {
		PunchPro_Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
		if(selectedEmployee != null) {
			updateEmployeeFromFields(selectedEmployee);
			if(updateEmployeeDB.updateUserRecord(selectedEmployee)) {
				System.out.println("Sucessfully updated user records....");
				employeeTable.refresh();
			}else {
				System.out.println("Failed to update employee records...");
			}
		}else {
			System.out.println("No Employee selected for updating");
		}
	}
	
	private void updateEmployeeFromFields(PunchPro_Employee employee) {
		employee.setEmployee_first_name(empFirstNameTF.getText());
		employee.setEmployee_last_name(empLastNameTF.getText());
		employee.setEmployee_contact_number(empContactNumTF.getText());
		employee.setEmployee_username(empUserNameTF.getText());
		employee.setEmployee_job_description(empJobDescTF.getText());
	}
	
	public void viewEmployeeList() {
		if(viewEmployeeDB !=null) {
			employeeList = viewEmployeeDB.viewUsers();
			employeeData = FXCollections.observableArrayList(employeeList);
			employeeTable.setItems(employeeData);
			setTotalEmployees(employeeList);
			/*
			employeeData = FXCollections.observableArrayList(viewEmployeeDB.viewUsers());
			employeeList = viewEmployeeDB.viewUsers();
			employeeTable.setItems(employeeData);
			setTotalEmployees(employeeList);*/
		}else {
			System.out.println("ViewUsersDAO is inot initialized.employeeData");
		}
	}

	private void populateFields(PunchPro_Employee selectedEmployee) {
		empFirstNameTF.setText(selectedEmployee.getEmployee_first_name());
		empLastNameTF.setText(selectedEmployee.getEmployee_last_name());
		empContactNumTF.setText(selectedEmployee.getEmployee_contact_number());
		empUserNameTF.setText(selectedEmployee.getEmployee_username());
		empJobDescTF.setText(selectedEmployee.getEmployee_job_description());
	}
	
	public void setTotalEmployees(ArrayList<PunchPro_Employee> employeeList) 
	{	
		totalEmployeeLbl.setText("Total Number of Employees: " + employeeList.size());
	}
	
	
	@FXML
	public void backToDashBoard(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("adminDashBoard.fxml"));
		Parent adminDashBoard = loader.load();
		
		Scene backToAdminDashBoard = new Scene(adminDashBoard);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(backToAdminDashBoard);
		window.show();	
		System.out.println("Going back to admin dashboard...");
	}
}


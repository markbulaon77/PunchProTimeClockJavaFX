package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginFormController {

	@FXML
	TextField usernameField;
	@FXML
	PasswordField passwordField;
	
	private PunchPro_Employee employee;
	private DatabaseConnection dbConnection = new DatabaseConnection();
	private UserVerificationDAO userDAO = new UserVerificationDAO(dbConnection);
	
	@FXML
	public void login(ActionEvent e) throws ClassNotFoundException, IOException {
		
		String employee_username = usernameField.getText();
		String employee_password = passwordField.getText();
		
		if(employee_username.isEmpty() || employee_username == null || employee_username.isEmpty() || employee_username == null) {
			System.out.println("Please enter your credentials");
			return;
		}
		
		//CREATE EMPLOYEE OBJECT.
		employee = new PunchPro_Employee(employee_username, employee_password);
		
		//VERIFY USER CREDENTIALS
		boolean isVerified = userDAO.verify_user(employee);

		if(isVerified) {
			System.out.println("Welcome ..." + employee.getEmployee_username());
			handleUserRole(employee, e);
		}else {
			System.out.println("Invalid, check your login credentials ...");
		}
	}
	
	// TO DO...
	public void handleUserRole(PunchPro_Employee employee, ActionEvent e) {
		
		String userRole = employee.getUser_role();		
		
		if("admin".equalsIgnoreCase(userRole)) {	
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("adminDashBoard.fxml"));
				Parent adminDashBoard = loader.load();
				AdminDashBoardController controller = loader.getController();
				controller.setWelcomeMessage(employee.getEmployee_first_name());
				
				Scene adminScene = new Scene(adminDashBoard);
				Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
				window.setScene(adminScene);
				window.show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if("user".equalsIgnoreCase(userRole)){
			System.out.println("Welcome user: " + employee.getEmployee_first_name());
			System.out.println(getClass().getResource("UserLoginDashBoard.fxml"));
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLoginDashBoard.fxml"));
				Parent userDashBoard = loader.load();				
				Scene userScene = new Scene(userDashBoard);
				Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
				window.setScene(userScene);
				window.show();
				
			} catch (IOException e1) {
				System.err.println("Failed to load UserLoginDashBoard.fxml: " + e1.getMessage());
				e1.printStackTrace();
			}
		}else{
			System.out.println("Invalid login credentials...");
		}
	}
}
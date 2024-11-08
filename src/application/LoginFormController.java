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
	
	private DatabaseConnection dbConnection = new DatabaseConnection();
	private UserDAO userDAO = new UserDAO(dbConnection);
	
	@FXML
	public void login(ActionEvent e) throws ClassNotFoundException, IOException {
		
		String employee_username = usernameField.getText();
		String employee_password = passwordField.getText();
		
		//boolean isVerified = userDAO.verify_user(username, password);
		PunchPro_Employee employee = new PunchPro_Employee(employee_username, employee_password);
		boolean isVerified = userDAO.verify_user(employee);
		
		if(employee_username.isEmpty() || employee_username == null || employee_username.isEmpty() || employee_username == null) {
			System.out.println("Please enter your credentials");
		}else {
			if(isVerified) {
				System.out.println("Welcome ..." + employee.getEmployee_username());
				
				//Load the FXML loader to access the controller.
				FXMLLoader loader = new FXMLLoader(getClass().getResource("adminDashBoard.fxml"));
				Parent adminDashBoard = loader.load();
				
				//Get the controller and pass the username.
				AdminDashBoardController controller = loader.getController();
				controller.setWelcomeMessage(employee.getEmployee_username());
				
				//Switch Scene...
				Scene adminScene = new Scene(adminDashBoard);
				Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
				window.setScene(adminScene);
				window.show();
			}else {
				System.out.println("Invalid, check your login credentials ...");
			}
		}
	
	}
	
}

package application;

import java.io.IOException;
import java.sql.Connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AdminDashBoardController {
			
	@FXML private Button goToCrudForm, scheduleMgmtForm;
	@FXML private Label nameLabel;
	
	Connection conn;
	DatabaseConnection dbConnection = new DatabaseConnection();
	
	@FXML
	private void goToCrudForm(ActionEvent e) throws IOException, ClassNotFoundException{
		
		this.conn = dbConnection.connect_to_database();		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("adminCrudForm.fxml"));
		Parent adminCrudForm = loader.load();
		
		//After loading the fxml file, pass the databaseConnection by instantiating the class and accessing it's method.
		AdminCrudController controller = loader.getController();
		controller.setDatabaseConnection(dbConnection);		
		controller.viewEmployeeList();
		//Switch Scene
		Scene adminAddEmployeeScene = new Scene (adminCrudForm);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(adminAddEmployeeScene);
		window.show();
	}
	
	@FXML
	private void scheduleMgmtForm(ActionEvent e) throws ClassNotFoundException, IOException {
	
		this.conn = dbConnection.connect_to_database();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("adminScheduleMgmtForm.fxml"));
		Parent adminSchedMgmt = loader.load();
			
		AdminSchedController schedController = loader.getController();
		schedController.setDatabaseConnection(dbConnection);
		schedController.viewEmployeeList();
		
		Scene manageScheduleScene = new Scene(adminSchedMgmt);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(manageScheduleScene);
		window.show();
		
	}
	
	public void setWelcomeMessage(String username) {
		nameLabel.setText("Welcome: " + username);
	}
	
}

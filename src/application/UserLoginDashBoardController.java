package application;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class UserLoginDashBoardController {

	@FXML
	private VBox menuContainer;
	
	@FXML
	private VBox schedulesSubmenu;
	
	private boolean isMenuVisible = true;
	
	@FXML
	private void toggleMenuVisibility() {
		//Toggle visibility of the entire menu once the humburger icon is click on aciton.
		/*
		isMenuVisible = !isMenuVisible;
		menuContainer.setVisible(isMenuVisible);
		menuContainer.setManaged(isMenuVisible);
		menuContainer.setPrefWidth(250);*/
		
		if(isMenuVisible) {
			menuContainer.setPrefWidth(50);
		}else {
			menuContainer.setPrefWidth(250);
		}
		
		isMenuVisible = !isMenuVisible;
	}
	
	@FXML
	private void toggleSchedulesSubmenu() {
		/*
		// Toggle visibility of the schedules submenu
		boolean isVisible = schedulesSubmenu.isVisible();
		schedulesSubmenu.setVisible(!isVisible);
		schedulesSubmenu.setManaged(isVisible);
		*/
	}

}

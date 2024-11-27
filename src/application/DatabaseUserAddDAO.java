package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUserAddDAO {

	private Connection conn;
	
	public DatabaseUserAddDAO(Connection conn) {
		this.conn = conn;
	}

	public boolean addNewUser(PunchPro_Employee newEmployee) 
	{	
		boolean isAdded = false;		
		try {
			String sql = "insert into punchprodatabase.users " +
					"(employee_first_name, employee_last_name, employee_contact_number, username, employee_job_description)" +
					"values (?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, newEmployee.getEmployee_first_name());
			stmt.setString(2, newEmployee.getEmployee_last_name());
			stmt.setString(3, newEmployee.getEmployee_contact_number());
			stmt.setString(4, newEmployee.getEmployee_username());
			stmt.setString(5, newEmployee.getEmployee_job_description());		
			
			int rowsAffected = stmt.executeUpdate();
		
			if(rowsAffected > 0) {
				isAdded = true;
			}
		}catch(SQLException e){
			System.out.println("Error while adding user: " + e.getMessage());
		}
		
		return isAdded;
	}

	
	
}

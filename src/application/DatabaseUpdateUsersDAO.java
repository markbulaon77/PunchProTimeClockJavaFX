package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUpdateUsersDAO {
	
	private Connection conn;
	
	DatabaseUpdateUsersDAO(Connection conn){
		this.conn = conn;
	}
	
	public boolean updateUserRecord(PunchPro_Employee employee) {
		
	    String sql = "UPDATE punchprodatabase.users SET " + 
	    "employee_first_name = ?, employee_last_name = ?, employee_contact_number = ?, employee_job_description = ?, username =?" +
	    " WHERE employee_number = ?";
		boolean isUpdate = false;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, employee.getEmployee_first_name());
			stmt.setString(2, employee.getEmployee_last_name());
			stmt.setString(3, employee.getEmployee_contact_number());
			stmt.setString(4, employee.getEmployee_job_description());
			stmt.setString(5, employee.getEmployee_username());
			stmt.setInt(6, employee.getEmployee_number());
			
			System.out.println("Executing query: " + stmt);  
			
			int rowsUpdated = stmt.executeUpdate();
			
			if(rowsUpdated > 0) {
				System.out.println("Sucessfully updated employee information..."); // Debugging log for the query
				isUpdate = true;
			}else {
				System.out.println("no rows were updated...");
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage() + " " + e.getErrorCode());
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Error closing the connection: " + e.getMessage() + " " + e.getErrorCode());
				}
			}
		}
		return isUpdate;
	}
	
}

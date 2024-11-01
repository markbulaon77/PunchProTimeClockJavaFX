package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteUsersDAO {
	
	private Connection conn;
	
	public DeleteUsersDAO(Connection conn) {
		this.conn = conn;
	}
	
	public void deleteUser(PunchPro_Employee employee) {
		String sql = "Delete from punchprodatabase.users where employee_number = ? ";
	
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, employee.getEmployee_number());
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected > 0) {
				System.out.println("Employee deleted sucessfully.");
			}else {
				System.out.println("No employee found with that number..");
			}
			
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage() + " " + e.getErrorCode());
		}
	}
}



package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserVerificationDAO {

	private DatabaseConnection dbConnection;
	
	public UserVerificationDAO(DatabaseConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public boolean verify_user(PunchPro_Employee employee) throws ClassNotFoundException {
		boolean is_user_verified = false;
		
		String query = "select * from punchprodatabase.users where username = ? and password = ?";
		Connection conn = dbConnection.connect_to_database();
		
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, employee.getEmployee_username());	
			stmt.setString(2, employee.getEmployee_password());
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				is_user_verified = true;
				String userRole = rs.getString("user_role");
				String employee_first_name = rs.getString("employee_first_name");
				employee.setUser_role(userRole);
				employee.setEmployee_first_name(employee_first_name);
			}
		}catch(SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}finally {
			try {
				if(conn !=null) 
				{
					conn.close();
				}
			}catch (SQLException e) 
			{
				System.out.println("Error: " + e.getMessage());
			}
		}
		return is_user_verified;
	}
	
	
}
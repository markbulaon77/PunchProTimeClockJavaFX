package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private Connection conn;

	public Connection connect_to_database() throws ClassNotFoundException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/punchprodatabase","root","admin");
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage() + "contact your local system administrator.");
		}
		return conn;
	}
	
	public void close_connection() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Error: " + e.getMessage() + " contact your local system administrator.");
			}
		}
	}

}

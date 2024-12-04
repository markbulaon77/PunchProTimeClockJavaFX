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
			System.out.println("Connection established successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getMessage() + "contact your local system administrator.");
		}
		return conn;
	}
	
	public void close_connection() {
		if(conn != null) {
			try {
				conn.close();
				System.out.println("Connection closed successfully");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error: " + e.getMessage() + " contact your local system administrator.");
			}
		}
	}

}

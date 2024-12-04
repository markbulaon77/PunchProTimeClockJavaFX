package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseDelScheduleDAO {
	
	private Connection conn;
	
	public DatabaseDelScheduleDAO(Connection conn) 
	{
		this.conn = conn;
	}
	
	public boolean delUserSchedule(PunchPro_Schedule userSchedule) 
	{
		boolean isDeleted = false;
		
		try {
			String sql = "delete from punchprodatabase.userschedule where user_number = ? and schedule_date = ? ";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userSchedule.getUser_number());
			stmt.setDate(2, java.sql.Date.valueOf(userSchedule.getWork_date()));
			
			int rowsDeleted = stmt.executeUpdate();
				if(rowsDeleted > 0) {
					isDeleted = true;
				}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return isDeleted;
	}
}

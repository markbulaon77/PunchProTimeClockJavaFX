package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUpdateUserScheduleDAO {
	
	private Connection conn;
	
	public DatabaseUpdateUserScheduleDAO(Connection conn) {
		this.conn = conn;
	}
	
	public boolean updateUserSchedule(PunchPro_Schedule userSchedule) {
		
		boolean isUpdated = false;
		
		try {
			String sql =  "UPDATE punchprodatabase.userschedule SET " +
					"schedule_date = ?, shift_start_time = ?, shift_1st_break = ?, " +
					"shift_lunch_break = ?, shift_2nd_break = ?, shift_end_time = ? " +
					"WHERE user_number = ? AND schedule_date = ? ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDate(1, java.sql.Date.valueOf(userSchedule.getWork_date()));
			stmt.setString(2, userSchedule.getShift_start_time());
			stmt.setString(3, userSchedule.getShift_1st_break());
			stmt.setString(4, userSchedule.getLunch_break());
			stmt.setString(5, userSchedule.getShift_2nd_break());
			stmt.setString(6, userSchedule.getShift_clock_outTime());
			stmt.setInt(7, userSchedule.getUser_number());
			stmt.setDate(8, java.sql.Date.valueOf(userSchedule.getWork_date()));
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected > 0) {
				isUpdated = true;
			}
		}catch(SQLException e) {
			System.out.println("Error: " + e.getLocalizedMessage());
		}
		
		return isUpdated;
		
	}
}

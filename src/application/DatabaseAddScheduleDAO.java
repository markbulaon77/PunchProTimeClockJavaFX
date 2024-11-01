package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseAddScheduleDAO {

	private Connection conn;
	
	public DatabaseAddScheduleDAO(Connection conn) {
		this.conn = conn;
	}
	
	public boolean addUserSchedule(PunchPro_Schedule userSchedule) {
		
		boolean isAdded = false;
		
		String sql = "insert into punchprodatabase.userschedule (user_number, user_name, schedule_date, shift_start_time, shift_1st_break, shift_lunch_break, shift_2nd_break, shift_end_time) values (?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userSchedule.getUser_number());
			stmt.setString(2, userSchedule.getUser_first_name() + " " + userSchedule.getUser_last_name());
			stmt.setDate(3, java.sql.Date.valueOf(userSchedule.getWork_date()));
			stmt.setString(4, userSchedule.getShift_start_time());
			stmt.setString(5, userSchedule.getShift_1st_break());
			stmt.setString(6, userSchedule.getLunch_break());
			stmt.setString(7, userSchedule.getShift_2nd_break());
			stmt.setString(8, userSchedule.getShift_clock_outTime());
			
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected > 0) {
				isAdded = true;
			}
			stmt.close();
			
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage() + " " + e.getErrorCode());
		}
		
		return isAdded;	
	}
	
}

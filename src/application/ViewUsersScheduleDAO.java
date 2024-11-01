package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ViewUsersScheduleDAO {
	
	private Connection conn;
	private ArrayList<PunchPro_Schedule> userSchedList;
	
	public ViewUsersScheduleDAO(Connection conn) {
		this.conn = conn;
	}
	//public ArrayList<PunchPro_Schedule> viewUsersSchedule(int user_number, LocalDate work_date, String shift_start_time, String shift_end_time) 
	public ArrayList<PunchPro_Schedule> viewUsersSchedule(int user_number, LocalDate work_date, String shift_start_time, String shift_end_time){
		userSchedList = new ArrayList<>();
		
		String query = "SELECT * FROM punchprodatabase.userschedule WHERE user_number = ? AND schedule_date = ? AND shift_start_time = ? AND shift_end_time = ? ORDER BY day(schedule_date)";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, user_number);
			//Convert LocalDate to javaSQL.
			java.sql.Date sqlDate = java.sql.Date.valueOf(work_date);
			stmt.setDate(2, sqlDate);
			stmt.setString(3, shift_start_time);
			stmt.setString(4, shift_end_time);
		
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				PunchPro_Schedule user_schedule = new PunchPro_Schedule(
						rs.getInt("user_number"),
						rs.getDate("work_date").toLocalDate(), // Convert the java.sql.Date to LocalDate....
						rs.getString("shift_start_time"),
						rs.getString("shift_1st_break"),
						rs.getString("lunch_break"),
						rs.getString("shift_2nd_break"),
						rs.getString("shift_clock_outTime")
						);
				userSchedList.add(user_schedule);
			}
			rs.close();
			stmt.close();
			
		}catch(SQLException e) {
			System.out.println("Error: " + e.getMessage() + " " + e.getErrorCode());
		}
		
		return userSchedList;	
	}
}

package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseViewUsersScheduleDAO {
	
	private Connection conn;
	
	public DatabaseViewUsersScheduleDAO(Connection conn) {
		this.conn = conn;
	}

	public ArrayList<PunchPro_Schedule> viewUsersScheduleForMonth(int userNumber, int year, int month) {
		ArrayList<PunchPro_Schedule> userSchedList = new ArrayList<>();
		String query = "SELECT * FROM punchprodatabase.userschedule WHERE user_number = ? and YEAR(schedule_date) = ? and MONTH(schedule_date) = ? ORDER BY schedule_date"; 
		
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, userNumber);
			stmt.setInt(2, year);
			stmt.setInt(3, month);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				PunchPro_Schedule userSchedule = new PunchPro_Schedule(
						rs.getInt("user_number"),
						rs.getDate("schedule_date").toLocalDate(),
						rs.getString("shift_start_time"),
						rs.getString("shift_1st_break"),
						rs.getString("shift_lunch_break"),
						rs.getString("shift_2nd_break"),
						rs.getString("shift_end_time")
						);
				userSchedList.add(userSchedule);
			}
			rs.close();
			stmt.close();
			
		}catch(SQLException e) {
			System.out.println("Error: " + e.getMessage() + " " + e.getErrorCode());
		}
		
		return userSchedList;
	}
}

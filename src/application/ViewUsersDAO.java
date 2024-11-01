package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewUsersDAO {
	
	private Connection conn;
	private PunchPro_Employee employee;
	private ArrayList<PunchPro_Employee> employeeList;
	
	public ViewUsersDAO(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList<PunchPro_Employee> viewUsers(){
		
		employeeList = new ArrayList<>();
		
		String query = "select * from punchprodatabase.users";
		
		try(PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()){
			while(rs.next()) 
			{
				employee = new PunchPro_Employee(
						rs.getInt("employee_number"),
						rs.getString("employee_first_name"),
						rs.getString("employee_last_name"),
						rs.getString("employee_contact_number"),
						rs.getString("username"),
						rs.getString("employee_job_description"));
				employeeList.add(employee);
			}
		}catch(SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return employeeList;
	}
	
	public ArrayList<PunchPro_Employee> viewUsers2(){
		
		employeeList = new ArrayList<>();
		String query = "select * from punchprodatabase.users";
		
		try(PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery()){
			
			while(rs.next()) {
				employee = new PunchPro_Employee(rs.getInt("employee_number"),
						rs.getString("employee_first_name"),
						rs.getString("employee_last_name"));
				employeeList.add(employee);
			}
			
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return employeeList;
		
	}
}

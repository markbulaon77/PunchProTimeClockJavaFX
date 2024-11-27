package application;

import java.time.LocalDate;

public class PunchPro_Schedule {
	
	private int user_number; //Refers to the PunchPro_Employee = employee_number
	private String user_first_name; // Refers to employee_first_name
	private String user_last_name; // Refers to employee_last_name.
	private LocalDate work_date; // Schedule - day of work
	private LocalDate work_date2; //For overloaded constructor to add a schedule till the selected date.
	private String shift_start_time;
	private String shift_1st_break;
	private String lunch_break;
	private String shift_2nd_break;
	private String shift_clock_outTime;
	
	public PunchPro_Schedule(int user_number, String user_first_name, String user_last_name, LocalDate work_date, String shift_start_time, String shift_1st_break, String lunch_break, String shift_2nd_break, String shift_clock_outTime) 
	{
		this.user_number = user_number;
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
		this.work_date = work_date;
		this.shift_start_time = shift_start_time;
		this.shift_1st_break = shift_1st_break;
		this.lunch_break = lunch_break;
		this.shift_2nd_break = shift_2nd_break;
		this.shift_clock_outTime = shift_clock_outTime;
	}
	
	//For Schedule Viewing Constructor
	public PunchPro_Schedule(int user_number, LocalDate work_date, String shift_start_time, String shift_1st_break, String lunch_break, String shift_2nd_break, String shift_clock_outTime) {
	    this.user_number = user_number;
	    this.work_date = work_date;
	    this.shift_start_time = shift_start_time;
	    this.shift_1st_break = shift_1st_break;
	    this.lunch_break = lunch_break;
	    this.shift_2nd_break = shift_2nd_break;
	    this.shift_clock_outTime = shift_clock_outTime;
	}
	
	//For Deleting Schedule Constructor
	public PunchPro_Schedule(int user_number, String user_first_name, String user_last_name,
			LocalDate work_date, LocalDate work_date2, String shift_start_time, String shift_1st_break, String lunch_break, String shift_2nd_break, String shift_clock_outTime) {
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
		this.user_number = user_number;
	    this.work_date = work_date;
	    this.setWork_date2(work_date2);
	    this.shift_start_time = shift_start_time;
	    this.shift_1st_break = shift_1st_break;
	    this.lunch_break = lunch_break;
	    this.shift_2nd_break = shift_2nd_break;
	    this.shift_clock_outTime = shift_clock_outTime;
	}

	public PunchPro_Schedule(int user_number, String user_first_name, String user_last_name, LocalDate work_date,
			LocalDate work_date2) {
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
		this.user_number = user_number;
	    this.work_date = work_date;
	    this.setWork_date2(work_date2);		
	}

	public int getUser_number() {
		return user_number;
	}
	public void setUser_number(int user_number) {
		this.user_number = user_number;
	}
	
	public String getUser_first_name() {
		return user_first_name;
	}
	public void setUser_first_name(String user_first_name) {
		this.user_first_name = user_first_name;
	}
	
	public String getUser_last_name() {
		return user_last_name;
	}

	public void setUser_last_name(String user_last_name) {
		this.user_last_name = user_last_name;
	}

	public LocalDate getWork_date() {
		return work_date;
	}
	public void setWork_date(LocalDate work_date) {
		this.work_date = work_date;
	}
	
	public String getShift_start_time() {
		return shift_start_time;
	}
	public void setShift_start_time(String shift_start_time) {
		this.shift_start_time = shift_start_time;
	}
	public String getShift_1st_break() {
		return shift_1st_break;
	}
	public void setShift_1st_break(String shift_1st_break) {
		this.shift_1st_break = shift_1st_break;
	}
	public String getLunch_break() {
		return lunch_break;
	}
	public void setLunch_break(String lunch_break) {
		this.lunch_break = lunch_break;
	}
	public String getShift_2nd_break() {
		return shift_2nd_break;
	}
	public void setShift_2nd_break(String shift_2nd_break) {
		this.shift_2nd_break = shift_2nd_break;
	}
	public String getShift_clock_outTime() {
		return shift_clock_outTime;
	}
	public void setShift_clock_outTime(String shift_clock_outTime) {
		this.shift_clock_outTime = shift_clock_outTime;
	}

	public LocalDate getWork_date2() {
		return work_date2;
	}

	public void setWork_date2(LocalDate work_date2) {
		this.work_date2 = work_date2;
	}
}

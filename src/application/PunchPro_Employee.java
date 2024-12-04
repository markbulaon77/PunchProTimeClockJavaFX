package application;

public class PunchPro_Employee {
	
	private int employee_number;
	private String employee_first_name;
	private String employee_last_name;
	private String employee_contact_number;
	private String employee_username;
	private String employee_password;
	private String employee_job_description;
	private String user_role;


	//This constructor for employee login_validation.
	public PunchPro_Employee(String employee_username, String employee_password) {
		this.employee_username = employee_username;
		this.employee_password = employee_password;
	}
	
	//This constructor for retrieving the username of the user to be display on the admin Dashboard if user role is admin.
	public PunchPro_Employee(String employee_username) {
		this.employee_username = employee_username;
	}
	
	//Overloaded#2: for UserAddDAO
	public PunchPro_Employee(String employee_first_name, String employee_last_name, String employee_contact_number,
		String employee_username, String employee_job_description) 
	{	
		 this.employee_first_name = employee_first_name;
		 this.employee_last_name = employee_last_name;
		 this.employee_contact_number = employee_contact_number;
		 this.employee_username = employee_username;
		  this.employee_job_description = employee_job_description;
	}

	//Overloaded#3: for ViewUserDAO
	public PunchPro_Employee(int employee_number, String employee_first_name, String employee_last_name, String employee_contact_number, String employee_username, String employee_job_description) {
		this.employee_number = employee_number;
		this.employee_first_name = employee_first_name;
		this.employee_last_name = employee_last_name;
		this.employee_contact_number = employee_contact_number;
		this.employee_username = employee_username;
		this.employee_job_description = employee_job_description;
	}
	
	public PunchPro_Employee(int employee_number, String employee_first_name, String employee_last_name) {
		this.employee_number = employee_number;
		this.employee_first_name = employee_first_name;
		this.employee_last_name = employee_last_name;
	}

	public int getEmployee_number() {
		return employee_number;
	}

	public void setEmployee_number(int employee_number) {
		this.employee_number = employee_number;
	}

	public String getEmployee_first_name() {
		return employee_first_name;
	}

	public void setEmployee_first_name(String employee_first_name) {
		this.employee_first_name = employee_first_name;
	}

	public String getEmployee_last_name() {
		return employee_last_name;
	}

	public void setEmployee_last_name(String employee_last_name) {
		this.employee_last_name = employee_last_name;
	}

	public String getEmployee_contact_number() {
		return employee_contact_number;
	}

	public void setEmployee_contact_number(String employee_contact_number) {
		this.employee_contact_number = employee_contact_number;
	}

	public String getEmployee_username() {
		return employee_username;
	}

	public void setEmployee_username(String employee_username) {
		this.employee_username = employee_username;
	}

	public String getEmployee_password() {
		return employee_password;
	}

	public void setEmployee_password(String employee_password) {
		this.employee_password = employee_password;
	}

	public String getEmployee_job_description() {
		return employee_job_description;
	}

	public void setEmployee_job_description(String employee_job_description) {
		this.employee_job_description = employee_job_description;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
}

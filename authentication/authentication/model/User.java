package authentication.model;

public class User {
	
	int id;
	String username;
	String password;
	int privilege;
	int employeeId;
	
	public User(String username, String password, int privilege, int employeeId){
		this.username = username;
		this.password = password;
		this. privilege = privilege;
		this.employeeId = employeeId;
	}

	public User() {
	}
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	

	
	
	
	
}

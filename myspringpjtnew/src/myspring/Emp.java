package myspring;

public class Emp {
	int employeeId, departmentId, salary;
	String firstName, lastName, hireDate, email;
	String departmentName;
	
	@Override
	public String toString() {
		return "Emp [empId=" + employeeId + ", deptId=" + departmentId + ", salary=" + salary + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", hireDate=" + hireDate + ", email=" + email + "]";
	}
}

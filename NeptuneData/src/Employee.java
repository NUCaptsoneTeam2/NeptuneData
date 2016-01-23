
public class Employee {

	private String name;
	private int employeeId;
	private int baseSalary;
	private Dealership dealership;
	private int dealershipId;

	public Employee(String name, int employeeId, int baseSalary, Dealership dealership) {
		this.name = name;
		this.employeeId = employeeId;
		this.baseSalary = baseSalary;
		this.dealership = dealership;
	}

	public Employee(String name, int employeeId, int baseSalary, int dealershipId) {
		this.name = name;
		this.employeeId = employeeId;
		this.baseSalary = baseSalary;
		this.dealershipId = dealershipId;
	}

	public String getName() {
		return name;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public int getBaseSalary() {
		return baseSalary;
	}

	public Dealership getDealership() {
		return dealership;
	}

	public int getDealershipId() {
		return dealershipId;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", employeeId=" + employeeId + ", baseSalary=" + baseSalary + ", dealership="
				+ dealershipId + "]";
	}
	
}


public class Employee {

	private String name;
	private int employeeId;
	private int baseSalary;
	private Dealership dealership;

	public Employee(String name, int employeeId, int baseSalary, Dealership dealership) {
		this.name = name;
		this.employeeId = employeeId;
		this.baseSalary = baseSalary;
		this.dealership = dealership;
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

	@Override
	public String toString() {
		return "Employee [name=" + name + ", employeeId=" + employeeId + ", baseSalary=" + baseSalary + ", dealership="
				+ dealership + "]";
	}
	
}

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

	public static List<Employee> getAllRaw() {

		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "raw_Employees";
		List<Employee> list = new ArrayList<Employee>();

		try {

			String sql = "select * from %s";

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();

			rs = stmt.executeQuery(String.format(sql, table));
			while (rs.next()) {
				Employee item = new Employee(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4));
				list.add(item);
			}

		}

		// Handle errors.
		catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (null != rs) try { rs.close(); } catch(Exception e) {}
			if (null != stmt) try { stmt.close(); } catch(Exception e) {}
			if (null != conn) try { conn.close(); } catch(Exception e) {}
		}

		return list;
	}
}



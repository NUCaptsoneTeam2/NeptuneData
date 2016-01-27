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
	private CustomerSatisfaction custSat;
	private Boolean isManager = false;
	private float bonusPct = 0;
	private float bonusPoints = 0;

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

	public void setCustSat(CustomerSatisfaction sat) {
		this.custSat = sat;
	}

	public CustomerSatisfaction getCustSat() {
		return custSat;
	}

	public Boolean hasCustomerSatisfactionRating() {
		if (null != this.getCustSat()) {
			return true;
		}
		return false;		
	}

	public static List<Employee> mergeCustomerSatisfactionRatings(List<Employee> employees, List<CustomerSatisfaction> ratings) {

		int i = 0;
		while (i < ratings.size()) {
			int j = 0;
			while (j < employees.size()) {
				if (employees.get(j).employeeId == ratings.get(i).getEmployeeId()) {
					employees.get(j).setCustSat(ratings.get(i));
					break;
				}
				j++;
			}
			i++;
		}

		return employees;
	}

	
	public static List<Employee> mergeManagerList(List<Employee> allEmployees, List<Employee> managers) {

		int i = 0;
		while (i < managers.size()) {
			int j = 0;
			while (j < allEmployees.size()) {
				if (allEmployees.get(j).employeeId == managers.get(i).getEmployeeId()) {
					allEmployees.get(j).setIsManager();
					break;
				}
				j++;
			}
			i++;
		}
		
		return allEmployees;
	}

	
	@Override
	public String toString() {
		return "Employee [name=" + name + ", employeeId=" + employeeId + ", baseSalary=" + baseSalary + ", dealership="
				+ dealershipId + ", bonusPct=" + bonusPct + ", bonusPoints=" + "]";
	}

	public Boolean getIsManager() {
		return isManager;
	}
	
	private void setIsManager(){
		this.isManager = true;
	}
	
	public void setBonusPct(float pct){
		this.bonusPct = pct;
	}
	
	public void setBonusPoints(int points){
		this.bonusPoints = points;
	}

	public float getBonusPct() {
		return bonusPct;
	}

	public float getBonusPoints() {
		return bonusPoints;
	}
	
	public static List<Employee> getAllRaw() {

		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "raw_Employees";
		List<Employee> list = new ArrayList<Employee>();

		try {

			String sql = "select * from %s order by dealershipid, employeeid ASC";

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
	
	public static List<Employee> getAllManagers() {

		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "raw_Managers"; //this is a db VIEW
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



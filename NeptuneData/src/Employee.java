import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Employee {

	private int employeeId;
	private String name;
	private int baseSalary;
	private int dealershipId;
	private int rating1;
	private int rating2;
	private int rating3;
	private int rating4;
	private int rating5;
	private String bandID;
	private int baseSalaryIncrease;
	private int newBaseSalary;
	private int newBandID;
	private float bonusPct = 0;
	private float bonusPctSatisfaction = 0;
	private int bonusPoints = 0;
	private int bonusAmount = 0;
	private CustomerSatisfaction custSat;
	private Boolean isManager = false;

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

	public int getDealershipId() {
		return dealershipId;
	}

	public int getRating1() {
		return rating1;
	}

	public int getRating2() {
		return rating2;
	}

	public int getRating3() {
		return rating3;
	}

	public int getRating4() {
		return rating4;
	}

	public int getRating5() {
		return rating5;
	}

	public String getBandID() {
		return bandID;
	}

	public int getBaseSalaryIncrease() {
		return baseSalaryIncrease;
	}

	public int getNewBaseSalary() {
		return newBaseSalary;
	}

	public int getNewBandID() {
		return newBandID;
	}

	public int getBonusAmount() {
		return bonusAmount;
	}

	public void setCustSat(CustomerSatisfaction sat) {
		this.custSat = sat;
		this.rating1 = this.custSat.getNum1stars();
		this.rating2 = this.custSat.getNum2stars();
		this.rating3 = this.custSat.getNum3stars();
		this.rating4 = this.custSat.getNum4stars();
		this.rating5 = this.custSat.getNum5stars();
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
		for (CustomerSatisfaction rating : ratings) {
			for (Employee emp : employees) {
				if (emp.employeeId == rating.getEmployeeId()) {
					emp.setCustSat(rating);
					break;
				}
			}
		}
		return employees;
	}

	public static List<Employee> mergeSalaryBands(List<Employee> employees, List<SalaryBand> bands) {
		for (Employee emp : employees) {
			for (SalaryBand band : bands) {
				//Calculate bonus before cust sat ratings
				if ((emp.getBaseSalary() >= band.getMinimum()) && 
						(emp.getBaseSalary() <= band.getMaximum())
						)
				{
					//set base bonus on emp object
					emp.setBonusPct(band.getBonusPercentage());
					emp.setBandID(band.getBand());
					break;
				}
			}
		}
		return employees;
	}

	public static List<Employee> mergeManagerList(List<Employee> allEmployees, List<Employee> managers) {
		for (Employee manager : managers) {
			for (Employee emp : allEmployees) {
				if (emp.employeeId == manager.getEmployeeId()) {
					emp.setIsManager();
					break;
				}
			}
		}
		
		return allEmployees;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", name=" + name + ", baseSalary=" + baseSalary
				+ ", dealershipId=" + dealershipId + ", rating1=" + rating1 + ", rating2=" + rating2 + ", rating3="
				+ rating3 + ", rating4=" + rating4 + ", rating5=" + rating5 + ", bandID=" + bandID
				+ ", baseSalaryIncrease=" + baseSalaryIncrease + ", newBaseSalary=" + newBaseSalary + ", newBandID="
				+ newBandID + ", bonusPct=" + bonusPct + ", bonusPctSatisfaction=" + bonusPctSatisfaction
				+ ", bonusPoints=" + bonusPoints + ", bonusAmount=" + bonusAmount + ", isManager=" + isManager + "]";
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
	
	public void setBandID(String band) {
		this.bandID = band;
	}
	
	public void setBonusPctSatisfaction(float pct){
		this.bonusPctSatisfaction = pct;
	}

	public void setBonusPoints(int points){
		this.bonusPoints = points;
	}

	public float getBonusPct() {
		return bonusPct;
	}

	public float getBonusPctSatisfaction() {
		return bonusPctSatisfaction;
	}

	public int getBonusPoints() {
		return bonusPoints;
	}
	
	public static List<Employee> getAllBase() {

		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Employee> list = new ArrayList<Employee>();

		try {

			String sql = "select name, employeeID, baseSalary, dealershipID from Employees order by dealershipid, employeeid ASC";

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);
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
		String table = "Managers"; //this is a db VIEW 
		List<Employee> list = new ArrayList<Employee>();

		try {

			String sql = "select name, employeeID, baseSalary, dealershipID  from %s order by dealershipid, employeeid ASC";

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



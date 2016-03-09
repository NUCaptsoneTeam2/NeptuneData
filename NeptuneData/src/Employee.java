import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Employee {

	private int employeeId;
	private String name;
	private double baseSalary;
	private int dealershipId;
	private int rating1;
	private int rating2;
	private int rating3;
	private int rating4;
	private int rating5;
	private String bandID;
	private double baseSalaryIncrease;
	private double newBaseSalary;
	private String newBandID;
	private double bonusPct = 0;
	private double bonusPctSatisfaction = 0;
	private int bonusPoints = 0;
	//private int bonusAmount = 0;
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

	public double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(int baseSalary) {
		this.baseSalary = baseSalary;
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

	public double getBaseSalaryIncrease() {
		return baseSalaryIncrease;
	}

	public double getNewBaseSalary() {
		return newBaseSalary;
	}
	
	private void setNewBaseSalary(int newBaseSalary) {
		this.newBaseSalary = newBaseSalary;
	}

	public String getNewBandID() {
		return newBandID;
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
			emp.setBandID(bands);
			emp.setBonusPct(bands);
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
				+ ", bonusPoints=" + bonusPoints + ", isManager=" + isManager + "]";
	}

	public Boolean getIsManager() {
		return isManager;
	}

	private void setIsManager(){
		this.isManager = true;
	}

	public void setBonusPct(List<SalaryBand> bands){
		for (SalaryBand band : bands) {
			if ((this.getBaseSalary() >= band.getMinimum()) && (this.getBaseSalary() <= band.getMaximum())) {
				this.bonusPct = band.getBonusPercentage();
				break;
			}
		}
	}

	public void setBandID(List<SalaryBand> bands) {
		for (SalaryBand band : bands) {
			if ((this.getBaseSalary() >= band.getMinimum()) && (this.getBaseSalary() <= band.getMaximum())) {
				this.setBandID(band.getBand());
				break;
			}
		}
	}

	private void setBandID(String bandId) {
		this.bandID = bandId;
	}

	public void setNewBandID(List<SalaryBand> bands) {
		for (SalaryBand band : bands) {
			if ((this.getNewBaseSalary() >= band.getMinimum()) && 
					(this.getNewBaseSalary() <= band.getMaximum())
					)
			{
				this.setNewBandID(band.getBand());
				break;
			}
		}
	}

	private void setNewBandID(String bandId) {
		this.newBandID = bandId;
	}

	public void setBonusPctSatisfaction(double d){
		this.bonusPctSatisfaction = d;
	}

	public void calculateAndSaveSalaryValues(List<SalaryBand> bands){
		
		//CREDIT on dealing with Currency in Java: http://blog.eisele.net/2011/08/working-with-money-in-java.html
		double tmpBaseSalaryIncrease = (this.baseSalary * (.05 + this.bonusPct));
		BigDecimal tmpBaseSalaryIncrease2 = new BigDecimal(tmpBaseSalaryIncrease).setScale(2, BigDecimal.ROUND_HALF_UP);
		this.baseSalaryIncrease = tmpBaseSalaryIncrease2.doubleValue();

		this.newBaseSalary = this.baseSalary + this.baseSalaryIncrease;
		//BigDecimal baseSalary2 = new BigDecimal(baseSalary).setScale(2, BigDecimal.ROUND_HALF_UP);

		this.setNewBandID(bands);
		this.saveCalculatedSalaryInfo();
	}

	public void setBonusPoints(int points){
		this.bonusPoints = points;
	}

	public double getBonusPct() {
		return bonusPct;
	}

	public void setBonusPct(double bonusPct) {
		this.bonusPct = bonusPct;
	}
	
	public double getBonusPctSatisfaction() {
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

	public static List<Employee> getAllBaseInfoByDealership(int dealershipId) {

		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Employee> list = new ArrayList<Employee>();

		try {

			String sql = String.format("select name, employeeID, baseSalary, dealershipID, bandID, baseSalaryIncrease, newBaseSalary, newBandID, bonusPct, bonusPctCustSat from Employees where dealershipID = %s order by dealershipid, employeeid ASC", dealershipId);

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Employee item = new Employee(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4));
				item.setBandID(rs.getString(5));
				item.setNewBaseSalary(rs.getInt(6));
				item.setNewBaseSalary(rs.getInt(7));
				item.setNewBandID(rs.getString(8));
				item.setBonusPct(rs.getDouble(9));
				item.setBonusPctSatisfaction(rs.getDouble(10));
				
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

	private void saveCalculatedSalaryInfo()
	{
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "Employees";

		try {

			String sqlTemplate = "update %s set "
					+ "baseSalaryIncrease = %s, "
					+ "newBaseSalary = %s, "
					+ "bonusPctCustSat = %s, "
					+ "newBandID = \'%s\' "
					+ "WHERE employeeID = %s";

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();

			String SQL = String.format(sqlTemplate, table, 
					this.getBaseSalaryIncrease(),
					this.getNewBaseSalary(),
					this.getBonusPctSatisfaction(),
					this.getNewBandID(),
					this.getEmployeeId());
			stmt.execute(SQL);

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

	}

}



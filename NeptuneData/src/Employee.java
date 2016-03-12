import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Create employee object from raw data. Also provides methods for calculating employee salary increases
 * based on customer satisfaction reviews and assigning salary bands based on the new employee base salary.
 *
 * @version 1.0
 */
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

	/**
	 * CONSTRUCTOR
	 *
	 * Create employee object.
	 *
	 * @param name			Name of the employee
	 * @param employeeId	Unique identification code for employee
	 * @param baseSalary	Employee base salary
	 * @param dealershipId	ID of the dealership employee works for
     */
	public Employee(String name, int employeeId, int baseSalary, int dealershipId) {
		this.name = name;
		this.employeeId = employeeId;
		this.baseSalary = baseSalary;
		this.dealershipId = dealershipId;
	}

    /**
     * @return  Employee name
     */
	public String getName() {
		return name;
	}

    /**
     * @return  Employee ID number
     */
	public int getEmployeeId() {
		return employeeId;
	}

    /**
     * @return  Employee base salary value
     */
	public double getBaseSalary() {
		return baseSalary;
	}

    /**
     * @param baseSalary Employee base salary amount
     */
	public void setBaseSalary(int baseSalary) {
		this.baseSalary = baseSalary;
	}

    /**
     * @return  Dealership ID employee works for.
     */
	public int getDealershipId() {
		return dealershipId;
	}

    /**
     * Count of 1 star customer satisfaction ratings
     * @return Rating count
     */
	public int getRating1() {
		return rating1;
	}

    /**
     * Count of 2 star customer satisfaction ratings
     * @return Rating count
     */
	public int getRating2() {
		return rating2;
	}

    /**
     * Count of 3 star customer satisfaction ratings
     * @return Rating count
     */
	public int getRating3() {
		return rating3;
	}

    /**
     * Count of 4 star customer satisfaction ratings
     * @return Rating count
     */
	public int getRating4() {
		return rating4;
	}

    /**
     * Count of 5 star customer satisfaction ratings
     * @return Rating count
     */
	public int getRating5() {
		return rating5;
	}

    /**
     * Get employee band ID
     * @return  Employee Band ID
     */
	public String getBandID() {
		return bandID;
	}

	/**
	 * Employee base salary increase
	 * @return Increase in employee base salary
     */
	public double getBaseSalaryIncrease() {
		return baseSalaryIncrease;
	}

	/**
	 * @return New base salary of current employee
     */
	public double getNewBaseSalary() {
		return newBaseSalary;
	}

	/**
	 * Set the new base salary for the current employee.
	 * @param newBaseSalary	New calculated employee base salary.
     */
	private void setNewBaseSalary(int newBaseSalary) {
		this.newBaseSalary = newBaseSalary;
	}

	/**
	 * @return	New employee salary band
     */
	public String getNewBandID() {
		return newBandID;
	}

	/**
	 * Set employee customer satisfaction values.
	 *
	 * @param sat	List object containing customer satifaction
	 *              values for the current employee
     */
	public void setCustSat(CustomerSatisfaction sat) {
		this.custSat = sat;
		this.rating1 = this.custSat.getNum1stars();
		this.rating2 = this.custSat.getNum2stars();
		this.rating3 = this.custSat.getNum3stars();
		this.rating4 = this.custSat.getNum4stars();
		this.rating5 = this.custSat.getNum5stars();
	}

	/**
	 * @return	Employee customer satisfaction values.
     */
	public CustomerSatisfaction getCustSat() {
		return custSat;
	}

	/**
	 * Determine whether the current employee object has been assigned
	 * customer satisfaction values
	 * @return	Boolean signifying whether or not the customer satisfaction
	 * 			values have been set
     */
	public Boolean hasCustomerSatisfactionRating() {
        return null != this.getCustSat();
    }

	/**
	 * Combine customer satisfaction values with their corresponding employee object.
	 *
	 * @param employees		List of employee objects
	 * @param ratings		List of employee customer satisfaction objects
     * @return	Employee object list with updated customer satisfaction values.
     */
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

	/**
	 * Merge salary band list with existing employee objects.
	 *
	 * @param employees		List of employee objects
	 * @param bands			List of employee salary bands
     * @return	Employee object list with updated salary band values
     */
	public static List<Employee> mergeSalaryBands(List<Employee> employees, List<SalaryBand> bands) {
		for (Employee emp : employees) {
			emp.setBandID(bands);
			emp.setBonusPct(bands);
		}
		return employees;
	}

	/**
	 * Combine manager list with employee listing
	 *
	 * @param allEmployees	List of employee objects
	 * @param managers		List of managers
     * @return	Employee object list with manager information attached.
     */
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

    /**
     * Convert employee object information into a readable format that can be used for
     * debugging purposes.
     *
     * @return  String displaying pertinent information about the employee object.
     */
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", name=" + name + ", baseSalary=" + baseSalary
				+ ", dealershipId=" + dealershipId + ", rating1=" + rating1 + ", rating2=" + rating2 + ", rating3="
				+ rating3 + ", rating4=" + rating4 + ", rating5=" + rating5 + ", bandID=" + bandID
				+ ", baseSalaryIncrease=" + baseSalaryIncrease + ", newBaseSalary=" + newBaseSalary + ", newBandID="
				+ newBandID + ", bonusPct=" + bonusPct + ", bonusPctSatisfaction=" + bonusPctSatisfaction
				+ ", bonusPoints=" + bonusPoints + ", isManager=" + isManager + "]";
	}

	/**
	 * @return	Boolean stating whether the current employee is also a manager.
     */
	public Boolean getIsManager() {
		return isManager;
	}

	/**
	 * Set variable signifying whether the current employee is also a manager.
	 */
	private void setIsManager(){
		this.isManager = true;
	}

	/**
	 * Set bonus percentages for employees.
	 * @param bands	Salary Bands
     */
	public void setBonusPct(List<SalaryBand> bands){
		for (SalaryBand band : bands) {
			if ((this.getBaseSalary() >= band.getMinimum()) && (this.getBaseSalary() <= band.getMaximum())) {
				this.bonusPct = band.getBonusPercentage();
				break;
			}
		}
	}

    /**
     * Set employee salary band ID
     * @param bands Employee salary band list
     */
	public void setBandID(List<SalaryBand> bands) {
		for (SalaryBand band : bands) {
			if ((this.getBaseSalary() >= band.getMinimum()) && (this.getBaseSalary() <= band.getMaximum())) {
				this.setBandID(band.getBand());
				break;
			}
		}
	}

    /**
     * Set employee salary band ID
     * @param bandId    Employee salary band ID
     */
	private void setBandID(String bandId) {
		this.bandID = bandId;
	}

    /**
     * @param bands     Employee salary band list
     */
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

    /**
     * @param bandId Salary band ID
     */
	private void setNewBandID(String bandId) {
		this.newBandID = bandId;
	}

    /**
     * @param d Customer satisfaction bonus percentages
     */
	public void setBonusPctSatisfaction(double d){
		this.bonusPctSatisfaction = d;
	}

	/**
	 * Calculate new employee salaries and persist final calculated values
	 * to the Neptune Auto application database.
	 * @param bands	Salary bands
     */
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

    /**
     * Set employee bonus points earned from customer satisfaction reviews.
     * @param points    Bonus point count value
     */
	public void setBonusPoints(int points){
		this.bonusPoints = points;
	}

    /**
     * @return  Employee bonus percentage.
     */
	public double getBonusPct() {
		return bonusPct;
	}

    /**
     * @param bonusPct Set bonus
     */
	public void setBonusPct(double bonusPct) {
		this.bonusPct = bonusPct;
	}

    /**
     * @return Employee bonus percentage from satisfaction bonus points.
     */
	public double getBonusPctSatisfaction() {
		return bonusPctSatisfaction;
	}

    /**
     * @return Employee satisfaction bonus points
     */
	public int getBonusPoints() {
		return bonusPoints;
	}

	/**
	 * Retrieve employee base salaries.
	 * @return	List of base salaries for all in the application database.
     */
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

    /**
     * Retrieve employee base salary information.
     * @param dealershipId  Dealership identifcation number
     * @return Employee list with base salary information.
     */
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

    /**
     * Retrieve list of all employees who are dealership managers.
     * @return  List of manager employee objects
     */
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

	/**
	 * Persist calculated salary information values to the Neptune Auto application
	 * database.
	 */
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
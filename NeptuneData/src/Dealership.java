import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Define objects containing information about individual dealerships.
 *
 * @version 1.0
 */
public class Dealership {

	private int dealershipId;
	private String city;
	private String state;
	private int zip;
	private Employee manager;
	private int managerId;
	private int operatingCosts;
	private List<String> promotionNames = new ArrayList<String>();
	private String promoIds; 
	private List<Employee> employees = new ArrayList<Employee>(); 
	private double netProfits = 0;

    /**
     * CONSTRUCTOR
     *
     * Create dealership object
     *
     * @param dealershipId      Unique dealership ID.
     * @param city              City dealership is located in
     * @param state             State dealership is located in
     * @param zip               Dealership Zip code
     * @param managerId         Employee ID of the dealership manager
     * @param operatingCosts    Dealership operating costs
     * @param promotionList     List of promotions offered by the dealership
     */
	public Dealership(int dealershipId, String city, String state, int zip, int managerId, int operatingCosts,
			String promotionList) {
		this.dealershipId = dealershipId;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.managerId = managerId;
		this.operatingCosts = operatingCosts;
		this.promoIds = promotionList;

		for (String promoName : promoIds.split(";")) {
			promotionNames.add(promoName);
		}

	}

    /**
     * CONSTRUCTOR
     *
     * Create dealership object.
     *
     * @param dealershipId      Unique dealership ID.
     * @param city              City dealership is located in
     * @param state             State dealership is located in
     * @param zip               Dealership Zip code
     * @param managerId         Employee ID of the dealership manager
     * @param operatingCosts    Dealership operating costs
     */
	public Dealership(int dealershipId, String city, String state, int zip, int managerId, int operatingCosts) {
		this.dealershipId = dealershipId;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.managerId = managerId;
		this.operatingCosts = operatingCosts;
	}

    /**
     * @return The dealership ID of the current dealership object.
     */
	public int getDealershipId() {
		return dealershipId;
	}

    /**
     * @return The city the dealership is located in
     */
	public String getCity() {
		return city;
	}

    /**
     * @return The state the dealership is located in
     */
	public String getState() {
		return state;
	}

    /**
     * @return Dealership zip code.
     */
	public int getZip() {
		return zip;
	}

    /**
     * @return Dealership manager
     */
	public Employee getManager() {
		return manager;
	}

    /**
     * @return Dealership manager ID
     */
	public int getManagerId() {
		return managerId;
	}

    /**
     * Set the manager for the current dealership.
     * @param dlrManager    Dealership manager employee object
     */
	public void setManager(Employee dlrManager){
		manager = dlrManager;
	}

    /**
     * @return Dealership operating costs.
     */
	public int getOperatingCosts() {
		return operatingCosts;
	}

    /**
     * @return List object containing the promotions offered by the
     *          current dealership
     */
	public List<String> getPromotions() {
		return promotionNames;
	}

    /**
     * @return Promotion IDs for the current dealership
     */
	public String getPromoIds() {
		return promoIds;
	}

    /**
     * Add a single employee to the current dealership list.
     * @param emp   Employee to add to dealership employee listing.
     */
	public void addEmployee(Employee emp){
		employees.add(emp);
	}

    /**
     * Add multiple employees to the current dealership
     * @param ees   List of employees to add to the current dealership
     */
	public void addEmployees(List<Employee> ees){
		employees = ees;
	}

    /**
     * @return List of employees who work at the current dealership.
     */
	public List<Employee> getEmployees(){
		return employees;
	}

    /**
     * @return Total net profits for the dealership
     */
	public double getNetProfits(){
		return this.netProfits;
	}

    /**
     * Define the total net profits for the current dealership
     *
     * @param netProfits2   Net profit value to assign to the current dealership
     */
	public void setNetProfits(double netProfits2){
		this.netProfits = netProfits2;
	}

    /**
     * Convert dealership object information into a readable format that can be used for
     * debugging purposes.
     *
     * @return  String displaying pertinent information about the dealership object.
     */
	@Override
	public String toString() {
		return "Dealership [dealershipId=" + dealershipId + ", city=" + city + ", state=" + state + ", zip=" + zip
				+ ", manager=" + managerId + ", operatingCosts=" + operatingCosts + ", promotions=" + promoIds + "]";
	}

    /**
     * Retrieves list of dealerships currently loaded into the application database.
     *
     * @return  List object of dealerships
     */
	public static List<Dealership> getAllBase() {

		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "Dealerships";
		List<Dealership> list = new ArrayList<Dealership>();

		try {

			String sql = "select dealershipID, city, state, zipCode, managerID, operatingCosts from %s order by dealershipid ASC";

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();

			rs = stmt.executeQuery(String.format(sql, table));
			while (rs.next()) {
				Dealership item = new Dealership(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
				//item.promotionNames = item.getDealershipPromotionNamesByDealershipId(item.getDealershipId());
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

//	private List<String> getDealershipPromotionNamesByDealershipId(int dealershipId) {
//
//		// Declare the JDBC objects.
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		String table = "DealershipPromotions";
//		ArrayList<String> list = new ArrayList<String>();
//
//		try {
//
//			String sql = String.format("select promotionName from %s where dealershipID = %s order by dealershipid ASC", table, dealershipId);
//
//			conn = ConnectionFactory.getConnection();  
//			stmt = conn.createStatement();
//
//			rs = stmt.executeQuery(String.format(sql, table));
//			while (rs.next()) {
//				list.add(rs.getString(1));
//			}
//
//		}
//
//		// Handle errors.
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		finally {
//			if (null != rs) try { rs.close(); } catch(Exception e) {}
//			if (null != stmt) try { stmt.close(); } catch(Exception e) {}
//			if (null != conn) try { conn.close(); } catch(Exception e) {}
//		}
//
//		return list;
//	}

    /**
     *  Persist net profit amounts to database.
     */
	public void saveNetProfit()
	{
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "Dealerships";

		try {

			String sqlTemplate = "update %s set "
					+ "netProfits = %s "
					+ "WHERE dealershipID = %s";

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();

			String SQL = String.format(sqlTemplate, table, 
					this.getNetProfits(),
					this.getDealershipId());
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

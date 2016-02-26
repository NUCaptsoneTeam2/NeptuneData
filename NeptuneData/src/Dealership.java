import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

	public Dealership(int dealershipId, String city, String state, int zip, int managerId, int operatingCosts) {
		this.dealershipId = dealershipId;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.managerId = managerId;
		this.operatingCosts = operatingCosts;
	}

	public int getDealershipId() {
		return dealershipId;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public int getZip() {
		return zip;
	}
	public Employee getManager() {
		return manager;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManager(Employee dlrManager){
		manager = dlrManager;
	}
	public int getOperatingCosts() {
		return operatingCosts;
	}
	public List<String> getPromotions() {
		return promotionNames;
	}
	public String getPromoIds() {
		return promoIds;
	}
	public void addEmployee(Employee emp){
		employees.add(emp);
	}

	public void addEmployees(List<Employee> ees){
		employees = ees;
	}

	public List<Employee> getEmployees(){
		return employees;
	}
	
	public double getNetProfits(){
		return this.netProfits;
	}
	
	public void setNetProfits(double netProfits2){
		this.netProfits = netProfits2;
	}

	@Override
	public String toString() {
		return "Dealership [dealershipId=" + dealershipId + ", city=" + city + ", state=" + state + ", zip=" + zip
				+ ", manager=" + managerId + ", operatingCosts=" + operatingCosts + ", promotions=" + promoIds + "]";
	}

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

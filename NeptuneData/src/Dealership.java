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
	
	@Override
	public String toString() {
		return "Dealership [dealershipId=" + dealershipId + ", city=" + city + ", state=" + state + ", zip=" + zip
				+ ", manager=" + managerId + ", operatingCosts=" + operatingCosts + ", promotions=" + promoIds + "]";
	}
	
	public static List<Dealership> getAllRaw() {

		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "raw_Dealerships";
		List<Dealership> list = new ArrayList<Dealership>();

		try {

			String sql = "select * from %s order by dealershipid ASC";

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();

			rs = stmt.executeQuery(String.format(sql, table));
			while (rs.next()) {
				Dealership item = new Dealership(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getString(7));
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

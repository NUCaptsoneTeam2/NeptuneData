import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VehicleSale {

	private int employeeId;
	private int month;
	private String modelId;
	private int dealershipId;
	private int promotionId;
	private int totalSalesCount;
	private int totalSalesAmount;
	private int totalCost;
	private int totalProfit;

	
	public VehicleSale(int employeeId, int month, String modelId, int dealershipId, int promotionId,
			int totalSalesCount, int totalSalesAmount, int totalCost, int totalProfit) {
		super();
		this.employeeId = employeeId;
		this.month = month;
		this.modelId = modelId;
		this.dealershipId = dealershipId;
		this.promotionId = promotionId;
		this.totalSalesCount = totalSalesCount;
		this.totalSalesAmount = totalSalesAmount;
		this.totalCost = totalCost;
		this.totalProfit = totalProfit;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public int getMonth() {
		return month;
	}

	public String getModelId() {
		return modelId;
	}

	public int getDealershipId() {
		return dealershipId;
	}

	public int getPromotionId() {
		return promotionId;
	}

	public int getTotalSalesCount() {
		return totalSalesCount;
	}

	public int getTotalSalesAmount() {
		return totalSalesAmount;
	}

	public int getTotalCost() {
		return totalCost;
	}

	public int getTotalProfit() {
		return totalProfit;
	}
	
	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}

	public void setTotalSalesAmount(int totalSalesAmount) {
		this.totalSalesAmount = totalSalesAmount;
	}

	public void setTotalProfit(int totalProfit) {
		this.totalProfit = totalProfit;
	}
	
	@Override
	public String toString() {
		return "VehicleSale [employeeId=" + employeeId + ", month=" + month + ", modelId=" + modelId + ", dealershipId="
				+ dealershipId + ", promotionId=" + promotionId + ", totalSalesCount=" + totalSalesCount
				+ ", totalSalesAmount=" + totalSalesAmount + ", totalCost=" + totalCost + ", totalProfit=" + totalProfit
				+ "]";
	}

	public static List<VehicleSale> getAllSalesByDealershipID(int dealershipId, boolean isCalculated) {
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<VehicleSale> list =  new ArrayList<VehicleSale>();

		try {

			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();

			rs = stmt.executeQuery(String.format("SELECT * FROM VehicleSales WHERE dealershipID = %s AND totalSalesCount > 0 AND IsCalculated = %s", dealershipId, isCalculated ? 1:0));
			while (rs.next()) {
				VehicleSale item = new VehicleSale(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
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
	
	public static List<VehicleSale> getAllSalesWithPromotions() {
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<VehicleSale> list =  new ArrayList<VehicleSale>();

		try {

			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT * FROM VehicleSales WHERE ISNULL(promotionId, 0) > 0 AND IsCalculated = 0");
			while (rs.next()) {
				VehicleSale item = new VehicleSale(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
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
	
	public static List<VehicleSale> getAllWithSales() {
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<VehicleSale> list =  new ArrayList<VehicleSale>();;

		try {

			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();

			rs = stmt.executeQuery("select * from VehicleSales WHERE totalSalesCount > 0");
			while (rs.next()) {
				VehicleSale item = new VehicleSale(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
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

	public static List<VehicleSale> getAllSalesWithoutPromotions() {
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<VehicleSale> list =  new ArrayList<VehicleSale>();;

		try {

			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT * FROM VehicleSales WHERE ISNULL(promotionID, 0) = 0 AND IsCalculated = 0  AND totalSalesCount > 0");
			while (rs.next()) {
				VehicleSale item = new VehicleSale(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
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

	public static void updateVehicleSalesCalculations(List<VehicleSale> sales)
	{
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "VehicleSales";

		try {

			String sqlTemplate = "update %s set "
					+ "promotionId = %s, "
					+ "totalSalesAmount = %s, "
					+ "totalProfit = %s, "
					+ "IsCalculated = 1 "
					+ "WHERE employeeID = %s AND month = %s AND modelId = \'%s\' AND dealershipId = %s";

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();
			
			int i=0; // used for batching
			for (VehicleSale sale : sales) {
				String SQL = String.format(sqlTemplate, table, 
						sale.getPromotionId(),
						sale.getTotalSalesAmount(),
						sale.getTotalProfit(),
						sale.getEmployeeId(),
						sale.getMonth(),
						sale.getModelId(),
						sale.getDealershipId());
				stmt.addBatch(SQL);

				//process in batches of 500 records; this volume seemed about right after a few different tests
				if (i % 2000 == 0 && i != 0){
					System.out.println("Beginning commit " + i + " @ " + LocalDateTime.now().toString());
					stmt.executeBatch();					
					stmt.clearBatch();
					System.out.println("Completed @ " + LocalDateTime.now().toString());
				}
				i++;
			}

			System.out.println("Beginning commit " + i + " @ " + LocalDateTime.now().toString());
			//execute final batch			
			stmt.executeBatch();
			System.out.println("Completed @ " + LocalDateTime.now().toString());

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

	public static void updateVehicleSalesWithoutSales()
	{
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();
			stmt.executeUpdate("exec UPDATE_VehicleSalesWithoutSales");
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

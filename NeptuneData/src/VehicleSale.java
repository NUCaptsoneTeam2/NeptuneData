import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Create VehicleSale object from raw data.
 *
 * @version 1.0
 */
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

	/**
	 * CONSTRUCTOR
	 *
	 * Create vehicle sales object
	 *
	 * @param employeeId		Unique employee identification number
	 * @param month				Numeric representation of the month of the sale
	 * @param modelId			Vehicle model number
	 * @param dealershipId		Dealership identification number
	 * @param promotionId		ID of the promotion applied to the sale
	 * @param totalSalesCount	Total count of vehicles sold
	 * @param totalSalesAmount	Total amount of the sales for the vehicles sold
	 * @param totalCost			Cost of the vehicles sold
     * @param totalProfit		Total amount of profit earned from the vehicle sales
     */
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

	/**
	 * @return employee identification number for the vehicle sale record
     */
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * @return numeric value of the month of the vehicle sales
     */
	public int getMonth() {
		return month;
	}

	/**
	 * @return vehicle model ID of the sales record
     */
	public String getModelId() {
		return modelId;
	}

	/**
	 * @return dealership ID where the vehicles were sold at
     */
	public int getDealershipId() {
		return dealershipId;
	}

	/**
	 * @return identifier of the promotion applied to the vehicle sales record
     */
	public int getPromotionId() {
		return promotionId;
	}

	/**
	 * @return total number of vehicles sold
     */
	public int getTotalSalesCount() {
		return totalSalesCount;
	}

	/**
	 * @return total value of the vehicle sales amount
     */
	public int getTotalSalesAmount() {
		return totalSalesAmount;
	}

	/**
	 * @return total dealership cost of the vehicles sold
     */
	public int getTotalCost() {
		return totalCost;
	}

	/**
	 * @return total profit amount earned by the dealership for the vehicle sales
     */
	public int getTotalProfit() {
		return totalProfit;
	}

	/**
	 * Set promotion ID for the vehicles sales object
	 * @param promotionId	unique identifier for the promotion that is to be applied for the vehicle sales
     */
	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}

	/**
	 * Set total sales value. Implemented in PromotionCalc class
	 * {@link PromotionCalc#calculateSalesProfitsWithoutPromotions(List)}
	 * {@link PromotionCalc#calculateSalesProfitsWithPromotions(List, List)}
	 * @param totalSalesAmount total dollar value of vehicle sales.
     */
	public void setTotalSalesAmount(int totalSalesAmount) {
		this.totalSalesAmount = totalSalesAmount;
	}

	/**
	 * Set total profit amount for vehicle sales. Implemented in PromotionCalc class
	 * {@link PromotionCalc#calculateSalesProfitsWithoutPromotions(List)}
	 * {@link PromotionCalc#calculateSalesProfitsWithPromotions(List, List)}
	 * @param totalProfit total profit earned on vehicle sales.
     */
	public void setTotalProfit(int totalProfit) {
		this.totalProfit = totalProfit;
	}

	/**
	 * Convert VehicleSales object information into a readable format that can be used for
	 * debugging purposes.
	 *
	 * @return  String displaying pertinent information about the VehicleSales object.
	 */
	@Override
	public String toString() {
		return "VehicleSale [employeeId=" + employeeId + ", month=" + month + ", modelId=" + modelId + ", dealershipId="
				+ dealershipId + ", promotionId=" + promotionId + ", totalSalesCount=" + totalSalesCount
				+ ", totalSalesAmount=" + totalSalesAmount + ", totalCost=" + totalCost + ", totalProfit=" + totalProfit
				+ "]";
	}

	/**
	 * Create a list of all vehicle sales for a specified dealership.
	 * @param dealershipId		Dealership identification number
	 * @param isCalculated		Specifies whether or not the current record has been processed/calculated
     * @return list object of vehicles sales for the specified dealership
     */
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

	/**
	 * @return list of vehicle sales that have promotions associated with them.
     */
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

	/**
	 * @return list of all vehicle sales in the application database (with promotions applied)
     */
	public static List<VehicleSale> getAllWithSales() {
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<VehicleSale> list =  new ArrayList<VehicleSale>();

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

	/**
	 * @return list of sales and amounts without promotions having been applied
	*/
	public static List<VehicleSale> getAllSalesWithoutPromotions() {
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<VehicleSale> list =  new ArrayList<VehicleSale>();

		try {

			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT * FROM VehicleSales WHERE ISNULL(promotionID, 0) = 0 AND IsCalculated = 0"); //AND totalSalesCount > 0
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

	/**
	 * Update the calculated values for vehicle sales in the Neptune Auto application database.
	 * @param sales	list of vehicle sales
     */
	public static void updateVehicleSalesCalculations(List<VehicleSale> sales)
	{
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "VehicleSales";
		int lastDealerID = 0;

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
				lastDealerID = sale.dealershipId;

				//process in batches of 500 records; this volume seemed about right after a few different tests
				if (i % 2000 == 0 && i != 0){
					System.out.println("Beginning commit on dealership " + sale.dealershipId + i + " @ " + LocalDateTime.now().toString());
					stmt.executeBatch();					
					stmt.clearBatch();
					System.out.println("Completed @ " + LocalDateTime.now().toString());
				}
				i++;
			}

			System.out.println(String.format("Beginning commit %s for dealership %s @ %s",i, lastDealerID, LocalDateTime.now()));
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

	/**
	 * Execute the UPDATE_VehicleSalesWithoutSales stored SQL procedure.
	 */
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

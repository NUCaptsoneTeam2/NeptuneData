import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Create promotion object from raw data.
 *
 * @version 1.0
 */
public class Promotion {

	private int promotionId;
	private String promotionName;
	private int month;
	private int cashBackBonus;
	private String vehicleClass;
	private int dealershipId;

	/**
	 * CONSTRUCTOR
	 *
	 * Create promotions object.
	 *
	 * @param promotionName	Name of the promotion being offered
	 * @param monthNum		Numeric value of the month the promotion is being offered in
	 * @param cashBackBonus	The amount of cashback given to the customer during the promotional period
	 * @param vehicleClass	Class of the vehicle eligible for the promotion
     */
	public Promotion(String promotionName, int monthNum, int cashBackBonus, String vehicleClass) {
		this.promotionId = -1;
		this.promotionName = promotionName;
		this.month = monthNum;
		this.cashBackBonus = cashBackBonus;
		this.vehicleClass = vehicleClass;
		this.dealershipId = -1; //unassigned
	}

	/**
	 * CONSTRUCTOR
	 *
	 * Create promotions object.
	 *
	 * @param promotionId	Unique identification number for the promotion
	 * @param promotionName	Name of the promotion being offered
	 * @param monthNum		Numeric value of the month the promotion is being offered in
	 * @param cashBackBonus	The amount of cashback given to the customer during the promotional period
	 * @param vehicleClass	Class of the vehicle eligible for the promotion
     * @param dealershipId	Identification number of the dealership offering the promotion.
     */
	public Promotion(int promotionId, String promotionName, int monthNum, int cashBackBonus, String vehicleClass, int dealershipId) {
		this.promotionId = promotionId;
		this.promotionName = promotionName;
		this.month = monthNum;
		this.cashBackBonus = cashBackBonus;
		this.vehicleClass = vehicleClass;
		this.dealershipId = dealershipId;
	}

	/**
	 * @return promotion identification number
     */
	public int getPromotionId() {
		return promotionId;
	}

	/**
	 * @return name of the promotion
     */
	public String getPromotionName() {
		return promotionName;
	}

	/**
	 * @return month promition was offered/eligible in
     */
	public int getMonth() {
		return month;
	}

	/**
	 * @return dollar amount of the cashback bonus offered to the customer during the promotion
     */
	public int getCashbackBonus() {
		return cashBackBonus;
	}

	/**
	 * @return type of vehicle eligible for the promotion offer (i.e. Car, Truck, SUV, etc.)
     */
	public String getVehicleClass() {
		return vehicleClass;
	}

	/**
	 * @return identification number of the dealership offering the promotion
     */
	public int getDealershipId() {
		return dealershipId;
	}

	/**
	 * Convert promotion object information into a readable format that can be used for
	 * debugging purposes.
	 *
	 * @return  String displaying pertinent information about the promotion object.
	 */
	@Override
	public String toString() {
		return "Promotion [promotionId=" + promotionId + ", month=" + month + ", cashBackBonus=" + cashBackBonus
				+ ", vehicleClass=" + vehicleClass + ", dealershipId=" + dealershipId + "]";
	}

	/**
	 * Retrieve list of all promotions recorded in the application database.
	 * @return  List of promotions
	 */
	public static List<Promotion> getAll() {

		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Promotion> list = new ArrayList<Promotion>();

		try {

			String sql = "SELECT p.promotionID, p.promotionName, p.month, p.cashbackBonus, p.vehicleClass, dp.dealershipID "
					+ "FROM Promotions AS p "
					+ "INNER JOIN DealershipPromotions AS dp ON p.promotionName = dp.promotionName "
					+ "ORDER BY dp.dealershipID";

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Promotion item = new Promotion(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getInt(6));
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
	 * Returns a list of promotions offered by an individual dealership
	 * @param dealershipId	Unique dealership identification number
	 * @return list of promotions offered by the specified dealership
     */
	public static List<Promotion> getAllPromotionsByDealershipID(int dealershipId) {
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Promotion> list =  new ArrayList<Promotion>();

		try {

			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();

			String sql = "SELECT p.promotionID, p.promotionName, p.month, p.cashbackBonus, p.vehicleClass, dp.dealershipID "
					+ "FROM Promotions AS p "
					+ "INNER JOIN DealershipPromotions AS dp ON p.promotionName = dp.promotionName "
					+ "WHERE dp.dealershipID = %s";

			rs = stmt.executeQuery(String.format(sql, dealershipId));
			while (rs.next()) {
				Promotion item = new Promotion(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getInt(6));
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


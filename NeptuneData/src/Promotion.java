import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Promotion {

	private int promotionId;
	private String promotionName;
	private int month;
	private int cashBackBonus;
	private String vehicleClass;
	private int dealershipId;


	public Promotion(String promotionName, int monthNum, int cashBackBonus, String vehicleClass) {
		this.promotionId = -1;
		this.promotionName = promotionName;
		this.month = monthNum;
		this.cashBackBonus = cashBackBonus;
		this.vehicleClass = vehicleClass;
		this.dealershipId = -1; //unassigned
	}

	public Promotion(int promotionId, String promotionName, int monthNum, int cashBackBonus, String vehicleClass, int dealershipId) {
		this.promotionId = promotionId;
		this.promotionName = promotionName;
		this.month = monthNum;
		this.cashBackBonus = cashBackBonus;
		this.vehicleClass = vehicleClass;
		this.dealershipId = dealershipId;
	}

	public int getPromotionId() {
		return promotionId;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public int getMonth() {
		return month;
	}

	public int getCashbackBonus() {
		return cashBackBonus;
	}

	public String getVehicleClass() {
		return vehicleClass;
	}

	public int getDealershipId() {
		return dealershipId;
	}

	@Override
	public String toString() {
		return "Promotion [promotionId=" + promotionId + ", month=" + month + ", cashBackBonus=" + cashBackBonus
				+ ", vehicleClass=" + vehicleClass + ", dealershipId=" + dealershipId + "]";
	}
	
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


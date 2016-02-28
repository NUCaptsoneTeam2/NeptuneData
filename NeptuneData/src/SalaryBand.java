import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SalaryBand {

	private String band;
	private double minimum;
	private double maximum = -1;
	private int paidTimeOffDays;
	private double bonusPercentage;
	
	public SalaryBand(String band, double min, double max, int paidTimeOffDays, double bonusPercentage) {
		this.band = band;
		this.minimum = min;
		this.maximum = max;
		this.paidTimeOffDays = paidTimeOffDays;
		this.bonusPercentage = bonusPercentage;
	}

	public String getBand() {
		return band;
	}

	public double getMinimum() {
		return minimum;
	}

	public double getMaximum() {
		if (maximum == -1)
			return Integer.MAX_VALUE;
		else
			return maximum;
	}

	public int getPaidTimeOffDays() {
		return paidTimeOffDays;
	}

	public double getBonusPercentage() {
		return bonusPercentage;
	}

	@Override
	public String toString() {
		return "SalaryBand [band=" + band + ", minimum=" + minimum + ", maximum=" + maximum + ", paidTimeOffDays="
				+ paidTimeOffDays + ", bonusPercentage=" + bonusPercentage + "]";
	}
	
	public static List<SalaryBand> getAllRaw() {

		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "SalaryBand";
		List<SalaryBand> list = new ArrayList<SalaryBand>();

		try {

			String sql = "select * from %s";

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();

			rs = stmt.executeQuery(String.format(sql, table));
			while (rs.next()) {
				SalaryBand item = new SalaryBand(rs.getString(1), rs.getDouble(2), rs.getDouble(3), rs.getInt(4), rs.getDouble(5));
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SalaryBand {

	private String band;
	private int minimum;
	private int maximum;
	private int paidTimeOffDays;
	private float bonusPercentage;
	
	public SalaryBand(String band, int minimum, int maximum, int paidTimeOffDays, float bonusPercentage) {
		this.band = band;
		this.minimum = minimum;
		this.maximum = maximum;
		this.paidTimeOffDays = paidTimeOffDays;
		this.bonusPercentage = bonusPercentage;
	}

	public String getBand() {
		return band;
	}

	public int getMinimum() {
		return minimum;
	}

	public int getMaximum() {
		return maximum;
	}

	public int getPaidTimeOffDays() {
		return paidTimeOffDays;
	}

	public float getBonusPercentage() {
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
		String table = "raw_SalaryBands";
		List<SalaryBand> list = new ArrayList<SalaryBand>();

		try {

			String sql = "select * from %s";

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();

			rs = stmt.executeQuery(String.format(sql, table));
			while (rs.next()) {
				SalaryBand item = new SalaryBand(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getFloat(5));
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

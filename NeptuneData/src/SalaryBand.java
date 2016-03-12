import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Create salary band object from raw data.
 *
 * @version 1.0
 */
public class SalaryBand {

	private String band;
	private double minimum;
	private double maximum = -1;
	private int paidTimeOffDays;
	private double bonusPercentage;

    /**
     * CONSTRUCTOR
     *
     * Create Salary Band Object
     *
     * @param band              Unique salary band identfier
     * @param min               Minimum salary in band range
     * @param max               Maximum salary in band range
     * @param paidTimeOffDays   Number of paid days off employee in salary band receives
     * @param bonusPercentage   Bonus percentage employee in salary band is eligible for
     */
	public SalaryBand(String band, double min, double max, int paidTimeOffDays, double bonusPercentage) {
		this.band = band;
		this.minimum = min;
		this.maximum = max;
		this.paidTimeOffDays = paidTimeOffDays;
		this.bonusPercentage = bonusPercentage;
	}

    /**
     * @return salary band unique identifier
     */
	public String getBand() {
		return band;
	}

    /**
     * @return minimum salary for current band
     */
	public double getMinimum() {
		return minimum;
	}

    /**
     * @return maximum salary for current band
     */
	public double getMaximum() {
		if (maximum == -1)
			return Integer.MAX_VALUE;
		else
			return maximum;
	}

    /**
     *
     * @return number of days, according to the salary band, the employee receives in paid time off
     */
	public int getPaidTimeOffDays() {
		return paidTimeOffDays;
	}

    /**
     *
     * @return salary band bonus percentage
     */
	public double getBonusPercentage() {
		return bonusPercentage;
	}

	/**
	 * Convert SalaryBand object information into a readable format that can be used for
	 * debugging purposes.
	 *
	 * @return  String displaying pertinent information about the SalaryBand object.
	 */
	@Override
	public String toString() {
		return "SalaryBand [band=" + band + ", minimum=" + minimum + ", maximum=" + maximum + ", paidTimeOffDays="
				+ paidTimeOffDays + ", bonusPercentage=" + bonusPercentage + "]";
	}

    /**
     * Retrieve list of all salary bands recorded in the application database.
     * @return  List of salary bands
     */
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Define objects containing information about individual customer satisfaction
 * ratings for individual employees.
 *
 * @version 1.0
 */
public class CustomerSatisfaction {

	private int employeeId;
	private int num5stars;
	private int num4stars;
	private int num3stars;
	private int num2stars;
	private int num1stars;

    /**
     * CONSTRUCTOR
     *
     * Create customer satisfaction object
     *
     * @param employeeId    Unique employee identification number
     * @param num5stars     Number of 5 star reviews received by employee.
     * @param num4stars     Number of 4 star reviews received by employee.
     * @param num3stars     Number of 3 star reviews received by employee.
     * @param num2stars     Number of 2 star reviews received by employee.
     * @param num1stars     Number of 1 star reviews received by employee.
     */
	public CustomerSatisfaction(int employeeId, int num5stars, int num4stars, int num3stars, int num2stars,
			int num1stars) {
		this.employeeId = employeeId;
		this.num5stars = num5stars;
		this.num4stars = num4stars;
		this.num3stars = num3stars;
		this.num2stars = num2stars;
		this.num1stars = num1stars;
	}

    /**
     * @return Employee identification number
     */
	public int getEmployeeId() {
		return employeeId;
	}

    /**
     * @return count of 5 star ratings received by employee
     */
	public int getNum5stars() {
		return num5stars;
	}

    /**
     * @return count of 4 star ratings received by employee
     */
	public int getNum4stars() {
		return num4stars;
	}

    /**
     * @return count of 3 star ratings received by employee
     */
	public int getNum3stars() {
		return num3stars;
	}

    /**
     * @return count of 2 star ratings received by employee
     */
	public int getNum2stars() {
		return num2stars;
	}

    /**
     * @return count of 1 star ratings received by employee
     */
	public int getNum1stars() {
		return num1stars;
	}

    /**
     * Convert CustomerSatisfaction object information into a readable format that can be used for
     * debugging purposes.
     *
     * @return  String displaying pertinent information about the CustomerSatisfaction object.
     */
	@Override
	public String toString() {
		return "CustomerSatisfaction [employee=" + employeeId + ", num5stars=" + num5stars + ", num4stars=" + num4stars
				+ ", num3stars=" + num3stars + ", num2stars=" + num2stars + ", num1stars=" + num1stars + "]";
	}

    /**
     * @return list of all customer satifaction counts stored in the Neptune Auto
     * application database.
     */
	public static List<CustomerSatisfaction> getAllRaw() {

		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "raw_CustomerSatisfaction";
		List<CustomerSatisfaction> list = new ArrayList<CustomerSatisfaction>();

		try {

			String sql = "select * from %s";

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();

			rs = stmt.executeQuery(String.format(sql, table));
			while (rs.next()) {
				CustomerSatisfaction item = new CustomerSatisfaction(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
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

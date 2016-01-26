import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerSatisfaction {

	private Employee employee;
	private int employeeId;
	private int num5stars;
	private int num4stars;
	private int num3stars;
	private int num2stars;
	private int num1stars;

	public CustomerSatisfaction(Employee employee, int num5stars, int num4stars, int num3stars, int num2stars,
			int num1stars) {
		this.employee = employee;
		this.num5stars = num5stars;
		this.num4stars = num4stars;
		this.num3stars = num3stars;
		this.num2stars = num2stars;
		this.num1stars = num1stars;
	}

	public CustomerSatisfaction(int employeeId, int num5stars, int num4stars, int num3stars, int num2stars,
			int num1stars) {
		this.employeeId = employeeId;
		this.num5stars = num5stars;
		this.num4stars = num4stars;
		this.num3stars = num3stars;
		this.num2stars = num2stars;
		this.num1stars = num1stars;
	}

	public Employee getEmployee() {
		return employee;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public int getNum5stars() {
		return num5stars;
	}
	public int getNum4stars() {
		return num4stars;
	}
	public int getNum3stars() {
		return num3stars;
	}
	public int getNum2stars() {
		return num2stars;
	}
	public int getNum1stars() {
		return num1stars;
	}

	@Override
	public String toString() {
		return "CustomerSatisfaction [employee=" + employeeId + ", num5stars=" + num5stars + ", num4stars=" + num4stars
				+ ", num3stars=" + num3stars + ", num2stars=" + num2stars + ", num1stars=" + num1stars + "]";
	}


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

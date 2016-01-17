
import java.sql.*;

public class Application {

	public static void main(String[] args) {

		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = ConnectionFactory.getConnection();  

			// Create and execute an SQL statement that returns some data.
			String SQL = "SELECT TOP 10 * FROM People";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);

			// Iterate through the data in the result set and display it.
			while (rs.next()) {
				System.out.println(rs.getString(2) + " " + rs.getString(3));
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


		// Salary Bands
		try {
			ParseSalaryBand.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Dealership
		try {
			ParseDealership.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Utils {

	public static void truncateRawData() {
		
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			String sql = "exec TruncateRawData";

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

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

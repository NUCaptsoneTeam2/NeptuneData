import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse raw Promotions text file to create Promotion objects
 * for each promotion listed in the file.
 *
 * @version 1.0
 */
public class ParsePromotion {

	private static String path = Constants.FILE_PROMOTIONS;

    /**
     *
     * @return list of promotions objects created from the raw text file data.
     * @throws NumberFormatException
     * @throws IOException
     */
	public static List<Promotion> parse() throws NumberFormatException, IOException {

		List<Promotion> list = new ArrayList<Promotion>();

		BufferedReader br = new BufferedReader(new FileReader(path));
		String line;
		while ((line = br.readLine()) != null) {
			String[] fields = line.split("[|]");

			//Create local variables for fields
			String promotionID = fields[0];
			int month = Integer.parseInt(fields[1]);
			int cashBackBonus = Integer.parseInt(fields[2]);
			String vehicleClass = fields[3];

			Promotion item = new Promotion(promotionID, month, cashBackBonus, vehicleClass);
			list.add(item);
		}
		br.close();

		//Write to console
		list.forEach(System.out::println);

		return list;
	}

	/**
	 * Load raw promotions data into the Promotions database table.
	 */
	public static void loadRaw() {
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "Promotions";

		try {

			String sqlTemplate = "insert into %s (promotionName, month, cashbackBonus, vehicleClass) "
					+ "VALUES (\'%s\', %s, %s, \'%s\')";

			List<Promotion> items = ParsePromotion.parse();

			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();

			//handle case where data exists
			rs = stmt.executeQuery(String.format("select count(*) from %s", table));
			rs.next();
			if (rs.getInt(1) > 0) {
				stmt.execute(String.format("TRUNCATE table %s", table));
			}

			int i = 0;
			while (i < items.size()) {
				String SQL = String.format(sqlTemplate, table, items.get(i).getPromotionName(), items.get(i).getMonth(),
						items.get(i).getCashbackBonus(), items.get(i).getVehicleClass());

				stmt.addBatch(SQL);
				i++;
			}

			stmt.executeBatch();
			stmt.clearBatch();
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
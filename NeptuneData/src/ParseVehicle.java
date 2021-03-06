import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse raw Vehicle text file to create Vehicle objects
 * for each vehicle listed in the file.
 *
 * @version 1.0
 */
public class ParseVehicle {

    private static String path = Constants.FILE_CARMODELS;

    /**
     *
     * @return list of vehicle objects created from the raw text file data
     * @throws NumberFormatException
     * @throws IOException
     */
    private static List<Vehicle> parse() throws NumberFormatException, IOException {

        List<Vehicle> list = new ArrayList<Vehicle>();

        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            String[] fields = line.split("[|]");

            //Create local variables for fields
            String model = fields[0];
            String make = fields[1];
            int cost = Integer.parseInt(fields[2]);
            int tagPrice = Integer.parseInt(fields[3]);

            Vehicle item = new Vehicle(model, make, cost, tagPrice);
            list.add(item);
        }
        br.close();

        //Write to console
        list.forEach(System.out::println);
        
        return list;
    }

    /**
     * Load raw vehicle data into the Vehicles database table.
     */
    public static void loadRaw() {
        // Declare the JDBC objects.
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String table = "Vehicles";

        try {

            String sqlTemplate = "insert into %s (modelID, vehicleClass, cost, tagPrice) "
                    + "VALUES (\'%s\', \'%s\', %s, %s)";

            List<Vehicle> items = ParseVehicle.parse();

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
                String SQL = String.format(sqlTemplate, table, items.get(i).getModel(), items.get(i).getMake(),
                        items.get(i).getCost(), items.get(i).getTagPrice());

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
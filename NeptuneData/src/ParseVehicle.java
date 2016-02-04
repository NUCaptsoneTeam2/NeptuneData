import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ParseVehicle {

    private static String path = Constants.FILE_CARMODELS;

    public static List<Vehicle> parse() throws NumberFormatException, IOException {

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

    public static void loadRaw() {
        // Declare the JDBC objects.
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String table = "raw_SalesTotals";

        try {

            String sqlTemplate = "insert into %s (Model, Make, Cost, TagPrice) "
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
            //stmt.executeUpdate(SQL);

        }

        // Handle errors.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != rs) try {
                rs.close();
            } catch (Exception e) {
            }
            if (null != stmt) try {
                stmt.close();
            } catch (Exception e) {
            }
            if (null != conn) try {
                conn.close();
            } catch (Exception e) {
            }
        }

    }
}
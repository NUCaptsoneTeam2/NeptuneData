import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ParsePromotion {

    private static String path = Constants.FILE_PROMOTIONS;

    public static List<Promotion> parse() throws NumberFormatException, IOException {

        List<Promotion> list = new ArrayList<Promotion>();

        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            String[] fields = line.split("[|]");

            //Create local variables for fields
            String promotionID = fields[0];
            String month = fields[1];
            int cashBackBonus = Integer.parseInt(fields[2]);
            String vehicleMake = fields[3];

            Promotion item = new Promotion(promotionID, month, cashBackBonus, vehicleMake);
            list.add(item);

            //Write to console
            System.out.println(item.toString());

        }
        br.close();
        return list;
    }

    public static void loadRaw() {
        // Declare the JDBC objects.
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String table = "raw_Promotions";

        try {

            String sqlTemplate = "insert into %s (PromotionID, MonthNums, CashBackBonus, VehicleMake) "
                    + "VALUES (\'%s\', \'%s\', %s, \'%s\')";

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
                String SQL = String.format(sqlTemplate, items.get(i).getPromotionId(), items.get(i).getMonthIds(),
                        items.get(i).getCashbackBonus(), items.get(i).getMakeIDs());

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
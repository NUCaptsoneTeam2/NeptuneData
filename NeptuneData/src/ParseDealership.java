import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ParseDealership {
	
	private static String path = Constants.FILE_DEALERSHIP;

	public static List<Dealership> parse() throws NumberFormatException, IOException
	{
	   List<Dealership> list = new ArrayList<Dealership>();
	   BufferedReader br = new BufferedReader(new FileReader(path));
	   String line = "";
	   while((line = br.readLine()) != null) 
	   {   
		   String[] fields = line.split("[|]");
	       
	       //Create local variables for fields
	       int id = Integer.parseInt(fields[0]);
	       String city = (String)fields[1];
	       String state = (String)fields[2];
	       int zip = Integer.parseInt(fields[3]);
	       int managerId = Integer.parseInt(fields[4]);
	       int operatingCosts = Integer.parseInt(fields[5]);
	       String promosRaw = (String)fields[6];
	       
	       Dealership item = new Dealership(id, city, state, zip, managerId, operatingCosts, promosRaw);
	       list.add(item);
		
	       //Write to console
	       System.out.println(item.toString());

	   }
	   br.close();
	   return list;
	}
	
	public static void loadRaw()
	{
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			String sqlTemplate = "insert into raw_Dealerships (DealershipID, City, State, Zip, ManagerID, OperatingCosts, PromotionIDs) "
					+ "VALUES (%s, \'%s\', \'%s\', %s, %s, %s, \'%s\')";

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();

			List<Dealership> items = ParseDealership.parse();

			int i = 0;
			while (i < items.size()) {
				String SQL = String.format(sqlTemplate, items.get(i).getDealershipId(), 
						items.get(i).getCity(), 
						items.get(i).getState(), 
						items.get(i).getZip(), 
						items.get(i).getManagerId(), 
						items.get(i).getOperatingCosts(), 
						items.get(i).getPromoIds());

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
		}

		finally {
			if (null != rs) try { rs.close(); } catch(Exception e) {}
			if (null != stmt) try { stmt.close(); } catch(Exception e) {}
			if (null != conn) try { conn.close(); } catch(Exception e) {}
		}

	}
}

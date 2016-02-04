import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParseVehicleSale {

	private static String path = Constants.FILE_AUTOSALES;

	public static List<VehicleSale> parse() throws NumberFormatException, IOException
	{
		List<VehicleSale> list = new ArrayList<VehicleSale>();
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = "";
		while((line = br.readLine()) != null) 
		{  
			String[] fields = line.split("[|]");

			//Create local variables for fields
			int employeeId = Integer.parseInt(fields[0]);
			int monthNum = Integer.parseInt(fields[1]);
			int ns100 = Integer.parseInt(fields[2]);
			int ns200 = Integer.parseInt(fields[3]);
			int ns300 = Integer.parseInt(fields[4]);
			int nc150 = Integer.parseInt(fields[5]);
			int nc250 = Integer.parseInt(fields[6]);
			int nc350 = Integer.parseInt(fields[7]);
			int np400 = Integer.parseInt(fields[8]);
			int np500 = Integer.parseInt(fields[9]);
			int nu600 = Integer.parseInt(fields[10]);
			int nu700 = Integer.parseInt(fields[11]);

			VehicleSale item = new VehicleSale(employeeId, monthNum, ns100, ns200, ns300, 
					nc150, nc250, nc350, np400, np500, nu600, nu700);
			list.add(item);
		}
		br.close();

        //Write to console
        list.forEach(System.out::println);
        
		return list;
	}

	public static void loadRaw()
	{
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "raw_SalesTotals";

		try {

			String sqlTemplate = "insert into %s (EmployeeID, MonthNum, " 
					+ "NS100_Total, NS200_Total, NS300_Total, "
					+ "NC150_Total, NC250_Total, NC350_Total, "
					+ "NP400_Total, NP500_Total, NU600_Total, NU700_Total) "
					+ "VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)";

			List<VehicleSale> items = ParseVehicleSale.parse();
			
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
				String SQL = String.format(sqlTemplate, table, items.get(i).getEmployeeId(), items.get(i).getMonthNum(), 
						items.get(i).getNs100(), items.get(i).getNs200(), items.get(i).getNs300(), 
						items.get(i).getNc150(), items.get(i).getNc250(), items.get(i).getNc350(), 
						items.get(i).getNp400(), items.get(i).getNp500(), 
						items.get(i).getNu600(), items.get(i).getNu700());

				stmt.addBatch(SQL);

				//process in batches of 2000 records; this volume seemed about right after a few different tests
				if (i % 2000 == 0 && i != 0){
					System.out.println("Beginning commit " + i + " @ " + LocalDateTime.now().toString());
					stmt.executeBatch();					
					stmt.clearBatch();
					System.out.println("Completed @ " + LocalDateTime.now().toString());
				}
				i++;
			}

			System.out.println("Beginning commit " + i + " @ " + LocalDateTime.now().toString());
			//execute final batch			
			stmt.executeBatch();
			System.out.println("Completed @ " + LocalDateTime.now().toString());

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

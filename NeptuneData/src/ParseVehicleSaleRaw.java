import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse raw Vehicle Sales text file to create Vehicle Sales record objects
 * for each record listed in the file.
 *
 * @version 1.0
 */
public class ParseVehicleSaleRaw {

	private static String path = Constants.FILE_AUTOSALES;

	/**
	 *
	 * @return list of vehicle sales record objects created from the raw text file data.
	 * @throws NumberFormatException
	 * @throws IOException
     */
	private static List<VehicleSaleRaw> parse() throws NumberFormatException, IOException
	{
		List<VehicleSaleRaw> list = new ArrayList<VehicleSaleRaw>();
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

			VehicleSaleRaw item = new VehicleSaleRaw(employeeId, monthNum, ns100, ns200, ns300, 
					nc150, nc250, nc350, np400, np500, nu600, nu700);
			list.add(item);
		}
		br.close();

		//Write to console
		list.forEach(System.out::println);

		return list;
	}

	/**
	 * Load raw vehicle sales data into the VehicleSales database table.
	 */
	public static void loadRaw()
	{
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "VehicleSales";

		try {

			String sqlTemplate = "insert into %s (employeeID, month, modelID, dealershipID, totalSalesCount, totalCost) " 
					+ "VALUES (%s, %s, \'%s\', %s, %s, %s)";

			List<VehicleSaleRaw> items = ParseVehicleSaleRaw.parse();			
			List<Employee> employees = Employee.getAllBase();
			
			items = VehicleSaleRaw.mergeDealershipId(items, employees);

			conn = ConnectionFactory.getConnection();  
			stmt = conn.createStatement();
			
			//handle case where data exists
			rs = stmt.executeQuery(String.format("select count(*) from %s", table));
			rs.next();
			if (rs.getInt(1) > 0) {
				stmt.execute(String.format("TRUNCATE table %s", table));
			}

			//TODO: if time, this needs to be refactored to be less hard-coded...ugh
			Vehicle ns100 = Vehicle.getByModelID("ns100");
			Vehicle ns200 = Vehicle.getByModelID("ns200");
			Vehicle ns300 = Vehicle.getByModelID("ns300");
			Vehicle nc150 = Vehicle.getByModelID("nc150");
			Vehicle nc250 = Vehicle.getByModelID("nc250");
			Vehicle nc350 = Vehicle.getByModelID("nc350");
			Vehicle np400 = Vehicle.getByModelID("np400");
			Vehicle np500 = Vehicle.getByModelID("np500");
			Vehicle nu600 = Vehicle.getByModelID("nu600");
			Vehicle nu700 = Vehicle.getByModelID("nu700");

			int i=0;
			for (VehicleSaleRaw item : items) {
				String sql1 = String.format(sqlTemplate, table, item.getEmployeeId(), item.getMonthNum(), ns100.getModel(), item.getDealershipId(), item.getNs100(), (item.getNs100() * ns100.getCost()));
				String sql2 = String.format(sqlTemplate, table, item.getEmployeeId(), item.getMonthNum(), ns200.getModel(), item.getDealershipId(), item.getNs200(), (item.getNs200() * ns200.getCost()));
				String sql3 = String.format(sqlTemplate, table, item.getEmployeeId(), item.getMonthNum(), ns300.getModel(), item.getDealershipId(), item.getNs300(), (item.getNs300() * ns300.getCost()));
				String sql4 = String.format(sqlTemplate, table, item.getEmployeeId(), item.getMonthNum(), nc150.getModel(), item.getDealershipId(), item.getNc150(), (item.getNc150() * nc150.getCost()));
				String sql5 = String.format(sqlTemplate, table, item.getEmployeeId(), item.getMonthNum(), nc250.getModel(), item.getDealershipId(), item.getNc250(), (item.getNc250() * nc250.getCost()));
				String sql6 = String.format(sqlTemplate, table, item.getEmployeeId(), item.getMonthNum(), nc350.getModel(), item.getDealershipId(), item.getNc350(), (item.getNc350() * nc350.getCost()));
				String sql7 = String.format(sqlTemplate, table, item.getEmployeeId(), item.getMonthNum(), np400.getModel(), item.getDealershipId(), item.getNp400(), (item.getNp400() * np400.getCost()));
				String sql8 = String.format(sqlTemplate, table, item.getEmployeeId(), item.getMonthNum(), np500.getModel(), item.getDealershipId(), item.getNp500(), (item.getNp500() * np500.getCost()));
				String sql9 = String.format(sqlTemplate, table, item.getEmployeeId(), item.getMonthNum(), nu600.getModel(), item.getDealershipId(), item.getNu600(), (item.getNu600() * nu600.getCost()));
				String sql10 = String.format(sqlTemplate, table, item.getEmployeeId(), item.getMonthNum(), nu700.getModel(), item.getDealershipId(), item.getNu700(), (item.getNu700() * nu700.getCost()));

				stmt.addBatch(sql1);
				stmt.addBatch(sql2);
				stmt.addBatch(sql3);
				stmt.addBatch(sql4);
				stmt.addBatch(sql5);
				stmt.addBatch(sql6);
				stmt.addBatch(sql7);
				stmt.addBatch(sql8);
				stmt.addBatch(sql9);
				stmt.addBatch(sql10);

				//process in batches of 2000 records; this volume seemed about right after a few different tests
				if (i % 2000 == 0 && i != 0){
					System.out.println("Beginning commit " + i*10 + " @ " + LocalDateTime.now().toString());
					stmt.executeBatch();
					stmt.clearBatch();
					System.out.println("Completed @ " + LocalDateTime.now().toString());
				}
				i++;
			}
			System.out.println("Beginning commit " + i*10 + " @ " + LocalDateTime.now().toString());
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParseEmployee {

	private static String path = Constants.FILE_SALESMAN;

	public static List<Employee> parse() throws NumberFormatException, IOException
	{
		List<Employee> list = new ArrayList<Employee>();
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = "";
		while((line = br.readLine()) != null) 
		{  
			String[] fields = line.split("[|]");

			//Create local variables for fields
			String name = (String)(fields[0]);
			int employeeId = Integer.parseInt(fields[1]);
			int baseSalary = Integer.parseInt(fields[2]);
			int dealershiId = Integer.parseInt(fields[3]);

			Employee item = new Employee(name, employeeId, baseSalary, dealershiId);
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
		String table = "raw_Employees";

		try {

			String sqlTemplate = "insert into %s (Name, EmployeeID, BaseSalary, DealershipID) "
					+ "VALUES (\'%s\', %s, %s, %s)";

			List<Employee> items = ParseEmployee.parse();

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
				String SQL = String.format(sqlTemplate, table, 
						items.get(i).getName().replace("'","''"), // < note escape pattern for SQL inserts on eg: O'Brien 
						items.get(i).getEmployeeId(), 
						items.get(i).getBaseSalary(), items.get(i).getDealershipId());

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
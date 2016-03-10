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
 * Parse raw Customer Satisfaction text files to create Customer Satisfaction objects
 * for each employee.
 */
public class ParseCustomerSatisfaction {
	
	private static String path = Constants.FILE_SATISFACTION;

    /**
     *
     * @return list of Customer Satisfaction objects for each employee listed in the raw
     * customer satisfaction file.
     * @throws NumberFormatException
     * @throws IOException
     */
	public static List<CustomerSatisfaction> parse() throws NumberFormatException, IOException
	{
	   List<CustomerSatisfaction> list = new ArrayList<CustomerSatisfaction>();
	   BufferedReader br = new BufferedReader(new FileReader(path));
	   String line = "";
	   while((line = br.readLine()) != null) 
	   {  
	       String[] fields = line.split("[|]");
	       
	       //Create local variables for fields
	       int employeeId = Integer.parseInt(fields[0]);
	       int num5stars = Integer.parseInt(fields[1]);
	       int num4stars = Integer.parseInt(fields[2]);
	       int num3stars = Integer.parseInt(fields[3]);
	       int num2stars = Integer.parseInt(fields[4]);
	       int num1stars = Integer.parseInt(fields[5]);
	       
	       CustomerSatisfaction item = new CustomerSatisfaction(employeeId, 
	    		   num5stars, num4stars, num3stars, num2stars, num1stars);
	       list.add(item);
	   }
	   br.close();

       //Write to console
       list.forEach(System.out::println);
       
	   return list;
	}

    /**
     * Load raw customer satisfaction data into the raw_CustomerSatisfaction database table.
     */
	public static void loadRaw()
	{
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "raw_CustomerSatisfaction";

		try {

			String sqlTemplate = "insert into %s (EmployeeID, Num5Stars, Num4Stars, Num3Stars, Num2Stars, Num1Stars) "
					+ "VALUES (%s, %s, %s, %s, %s, %s)";

			List<CustomerSatisfaction> items = ParseCustomerSatisfaction.parse();

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
				String SQL = String.format(sqlTemplate, table, items.get(i).getEmployeeId(), 
						items.get(i).getNum5stars(), 
						items.get(i).getNum4stars(), 
						items.get(i).getNum3stars(), 
						items.get(i).getNum2stars(), 
						items.get(i).getNum1stars());


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

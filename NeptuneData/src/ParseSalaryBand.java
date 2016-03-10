import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse raw SalaryBand text file to create Salary Band objects
 * for each Salary Band listed in the file.
 */
public class ParseSalaryBand {
	
	private static String path = Constants.FILE_SALARYBAND;

    /**
     *
     * @return list of salary band objects created from the raw text file data.
     * @throws NumberFormatException
     * @throws IOException
     */
	public static List<SalaryBand> parse() throws NumberFormatException, IOException
	{
	   List<SalaryBand> list = new ArrayList<SalaryBand>();
	   BufferedReader br = new BufferedReader(new FileReader(path));
	   String line = "";
	   while((line = br.readLine()) != null) 
	   {  
	       String[] fields = line.split("[|]");
	       
	       //Create local variables for fields
	       String band = fields[0];
	       double min = Double.parseDouble(fields[1]);
	       double max = Double.parseDouble(fields[2]);
	       int pdTimeOff = Integer.parseInt(fields[3]);
	       double pct = Double.parseDouble(fields[4]);
	       
	       SalaryBand item = new SalaryBand(band, min, max, pdTimeOff, pct);
	       list.add(item);
	   }
	   br.close();

       //Write to console
       list.forEach(System.out::println);
       
	   return list;
	}

	/**
	 * Load raw Salary Band data into the SalaryBand database table.
	 */
	public static void loadRaw()
	{
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String table = "SalaryBand";

		try {

			String sqlTemplate = "insert into %s (bandID, salMin, salMax, paidTimeOff, bonusPercentage) "
					+ "VALUES (\'%s\', %s, %s, %s, %s)";

			List<SalaryBand> items = ParseSalaryBand.parse();

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
				String SQL = String.format(sqlTemplate, table, items.get(i).getBand(), items.get(i).getMinimum(), 
						items.get(i).getMaximum(), items.get(i).getPaidTimeOffDays(), 
						items.get(i).getBonusPercentage());

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

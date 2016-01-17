import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseDealership {
	
	private static String path = Constants.FILE_DEALERSHIP;

	public static void parse() throws NumberFormatException, IOException
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
	       
	       Dealership item = new Dealership(id, city, state, zip, managerId, operatingCosts, promosRaw.split("\\;"));
	       list.add(item);
		
	       //Write to console
	       System.out.println(item.toString());

	   }
	   br.close();
	}
}

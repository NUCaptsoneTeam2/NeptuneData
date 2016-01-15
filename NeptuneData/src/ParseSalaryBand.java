
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseSalaryBand {
	
	private static String path = Constants.FILE_SALARYBAND;

	public static void parse() throws NumberFormatException, IOException
	{
	   List<SalaryBand> list = new ArrayList<SalaryBand>();
	   BufferedReader br = new BufferedReader(new FileReader(path));
	   String line = "";
	   while((line = br.readLine()) != null) {  
	       String[] fields = line.split("[|]");
	       String band = (String)fields[0];
	       int min = Integer.parseInt(fields[1]);
	       int max = Integer.parseInt(fields[2]);
	       int pdTimeOff = Integer.parseInt(fields[3]);
	       float pct = Float.parseFloat(fields[4]);
	       
	       SalaryBand item = new SalaryBand(band, min, max, pdTimeOff, pct);
	       list.add(item);
		
	       //Write to console
	       System.out.println(item.toString());

	   }
	   br.close();
	}
}

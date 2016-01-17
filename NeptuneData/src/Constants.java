
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Constants {

	//Get once
	public static Properties properties = Constants.getProperties();

	public static String FILE_AUTOSALES = properties.getProperty("FILE_AUTOSALES");
	public static String FILE_CARMODELS = properties.getProperty("FILE_CARMODELS");
	public static String FILE_PROMOTIONS = properties.getProperty("FILE_PROMOTIONS");
	public static String FILE_SATISFACTION = properties.getProperty("FILE_SATISFACTION");
	public static String FILE_DEALERSHIP = properties.getProperty("FILE_DEALERSHIP");
	public static String FILE_SALARYBAND = properties.getProperty("FILE_SALARYBAND");
	
	public static String CONNECTION_URL = properties.getProperty("CONNECTION_URL");
	public static String USERNAME = properties.getProperty("USERNAME");
	public static String PASSWORD = properties.getProperty("PASSWORD");
	public static String CATALOG = properties.getProperty("CATALOG");
	public static String DRIVER = properties.getProperty("DRIVER");

	private static Properties getProperties()
	{
		if (null == properties || properties.isEmpty())
		{
			Properties p = new Properties();
			try {
				//TODO: make path dynamic/relative
				p.load(new FileInputStream("/Users/ccaswell/git/Neptune/NeptuneData/src/resources/config.properties"));
				return p;
			} 
			catch (IOException e) {
				System.out.println(e);
				return properties;
			}
		}
		else
			return properties;

	}
}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	// ConnectionFactory pattern found @ http://theopentutorials.com/tutorials/java/jdbc/jdbc-examples-introduction/#Design_Patterns_Used

	// Create variables for the connection string
	private static String connectionUrl = Constants.CONNECTION_URL;
	private static String username = Constants.USERNAME;
	private static String password = Constants.PASSWORD;
	private static String catalog = Constants.CATALOG;
	private static String driver = Constants.DRIVER;

	//reference self
	private static ConnectionFactory instance = new ConnectionFactory();

	//private constructor
	private ConnectionFactory() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection createConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(connectionUrl, username, password);
			connection.setCatalog(catalog);
		} catch (SQLException e) {
			System.out.println("ERROR: Unable to Connect to Database.");
		}
		return connection;
	}   

	public static Connection getConnection() {
		return instance.createConnection();
	}
}


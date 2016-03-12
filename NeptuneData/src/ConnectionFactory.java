import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides the necessary methods to allow the Neptune Auto application
 * to connect to a Microsoft SQL Server database.
 *
 * ConnectionFactory pattern found @
 * http://theopentutorials.com/tutorials/java/jdbc/jdbc-examples-introduction/#Design_Patterns_Used
 *
 * @version 1.0
 */
public class ConnectionFactory {

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

    /**
     * Build database connection using the connectionUrl, username, password
     * and catalog constants defined in config.properties
     *
     * @return Microsoft SQL Server Database connection object.
     * @throws SQLException
     * @throws Exception
     */
	private Connection createConnection() throws Exception {
		Connection connection = null;
			connection = DriverManager.getConnection(connectionUrl, username, password);
			connection.setCatalog(catalog);
		return connection;
	}

    /**
     *
     * @return Instance of a Microsoft SQL Server database connection
     * @throws SQLException
     * @throws Exception
     */
	public static Connection getConnection() throws Exception {
		return instance.createConnection();
	}
}


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Create vehicle object from raw data.
 *
 * @version 1.0
 */
public class Vehicle {

	private String model;
	private String make;
	private int cost;
	private int tagPrice;

    /**
     * CONSTRUCTOR
     *
     * @param model     Vehicle model number
     * @param make      Type of vehicle (i.e. Car, SUV, truck, etc.)
     * @param cost      Actual cost of the vehicle
     * @param tagPrice  Price the vehicle is listed to sell for.
     */
	public Vehicle(String model, String make, int cost, int tagPrice)
	{
		this.model = model;
		this.make = make;
		this.cost = cost;
		this.tagPrice = tagPrice;		
	}

    /**
     *
     * @return vehicle model number
     */
	public String getModel() {
		return model;
	}

    /**
     *
     * @return make of the vehicle
     */
	public String getMake() {
		return make;
	}

    /**
     * @return cost of the vehicle
     */
	public int getCost() {
		return cost;
	}

    /**
     * @return tag price of the vehicle
     */
	public int getTagPrice() {
		return tagPrice;
	}

    /**
     * Enumeration of all valid vehicle makes
     */
	public enum Make
	{
		ALL, Coupe, Pickup, Sedan, SUV
    }

    /**
     * Convert vehicle object information into a readable format that can be used for
     * debugging purposes.
     *
     * @return  String displaying pertinent information about the vehicle object.
     */
	@Override
	public String toString() {
		return "Vehicle [model=" + model + ", make=" + make + ", cost=" + cost + ", tagPrice=" + tagPrice + "]";
	}

    /**
     *
     * @param modelID   Vehicle model ID
     * @return vehicle object matching specified modelID
     */
	public static Vehicle getByModelID(String modelID) {
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Vehicle vehicle = null;

		try {

			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();

			rs = stmt.executeQuery(String.format("select modelID, vehicleClass, cost, tagPrice from Vehicles WHERE modelID = \'%s\'", modelID));
			rs.next(); //need to advance the rs
			vehicle = new Vehicle(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
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

		return vehicle;
	}

    /**
     * @return list of all vehicle models in the application database.
     */
	public static List<Vehicle> getAll() {
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Vehicle> list =  new ArrayList<Vehicle>();

        try {

			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();

			rs = stmt.executeQuery("select * from Vehicles");
			while (rs.next()) {
				Vehicle item = new Vehicle(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
				list.add(item);
			}
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

		return list;
	}
}

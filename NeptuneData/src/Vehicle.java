import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {

	private String model;
	private String make;
	private String strMake;
	private int cost;
	private int tagPrice;


	public Vehicle(String model, String make, int cost, int tagPrice)
	{
		this.model = model;
		this.make = make;
		this.cost = cost;
		this.tagPrice = tagPrice;		
	}

	public String getModel() {
		return model;
	}

	public String getMakeString() {
		return strMake;
	}

	public String getMake() {
		return make;
	}

	public int getCost() {
		return cost;
	}

	public int getTagPrice() {
		return tagPrice;
	}

	public enum Make
	{
		ALL, Coupe, Pickup, Sedan, SUV;
	}

	@Override
	public String toString() {
		return "Vehicle [model=" + model + ", make=" + make + ", cost=" + cost + ", tagPrice=" + tagPrice + "]";
	}


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

	public static List<Vehicle> getAll() {
		// Declare the JDBC objects.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Vehicle> list =  new ArrayList<Vehicle>();;

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

/**
 * The Data class contains the method to load data from raw text files,
 * parse the data based on data type, and persist the information to
 * the production application database to await processing and incorporation
 * into Neptune Auto metric calculations
 *
 * @version 1.0
 */
public class Data {

	/**
	 * Parse raw data files and persist the data into the application
	 * database.
	 * @param SalaryBand 	(required) Specify whether salary band information
	 *                   	should be parsed and imported into the database.
	 * @param Promotion		(required) Specify whether promotions data should
	 *                  	be parsed and imported into the database.
	 * @param Vehicle 		(required) Specify whether vehicle information should
	 *                		be parsed and imported into the database.
	 * @param Employee		(required) Specify whether employee information should
	 *                      be parsed and imported into the database.
	 * @param Dealership	(required) Specify whether dealership information should
	 *                      be parsed and imported into the database.
	 * @param VehicleSales	(required) Specify whether vehicle sales information should
	 *                      be parsed and imported into the database.
	 */
	public static void loadBaseData(boolean SalaryBand,
									boolean Promotion,
									boolean Vehicle,
									boolean Employee,
									boolean Dealership,
									boolean VehicleSales) {

		// Load raw SalaryBand data
		if(SalaryBand){
			try {
				ParseSalaryBand.loadRaw();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		// Load raw Promotion data
		if(Promotion) {
			try {
				ParsePromotion.loadRaw();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}

		// Load raw Vehicle data
		if(Vehicle) {
			try {
				ParseVehicle.loadRaw();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}

		// Load raw Employee data
		if(Employee) {
			try {
				ParseEmployee.loadRaw();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Load raw Dealership data
		if(Dealership) {
			try {
				ParseDealership.loadRaw();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//WARNING: Takes 20-25 minutes to complete.
 		// Load raw VehicleSales data
		if(VehicleSales) {
			try {
				ParseVehicleSaleRaw.loadRaw();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

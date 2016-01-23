
public class Application {

	public static void main(String[] args) {

		// Uncomment to delete (reset) all raw_ data 
		//Utils.truncateRawData();
		
		// ParseVehicleSale
/*		try {
			ParseVehicleSale.loadRaw();
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		// ParseSalaryBand
/*		try {
			ParseSalaryBand.loadRaw();
		} catch (Exception e) {
			e.printStackTrace();
		}		
*/
		
		try {
			ParseCustomerSatisfaction.loadRaw();
		} catch (Exception e) {
			e.printStackTrace();
		}		

		
		// Dealership
		try {
			ParseDealership.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

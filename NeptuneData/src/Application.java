
public class Application {

	public static void main(String[] args) {

		// ParseVehicleSale
		try {
			ParseVehicleSale.loadRaw();
		} catch (Exception e) {
			e.printStackTrace();
		}		

		// Salary Bands
		try {
			ParseSalaryBand.parse();
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

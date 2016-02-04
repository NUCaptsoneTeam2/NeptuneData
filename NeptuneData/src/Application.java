
public class Application {

	public static void main(String[] args) {

		// Uncomment to delete (reset) all raw_ data 
		//Utils.truncateRawData();
		

		// ParseVehicle
		try {
			ParseVehicle.parse();
			//ParseVehicle.loadRaw();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ParseVehicleSale
		try {
			ParseVehicleSale.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ParseSalaryBand
		try {
			ParseSalaryBand.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//CustomerSatisfaction
		try {
			ParseCustomerSatisfaction.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Dealership
		try {
			ParseDealership.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}		

		// Employee
		try {
			ParseEmployee.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}


		// Promotion
		try {
			ParsePromotion.loadRaw();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		try {
			EmployeeCalc.run();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}

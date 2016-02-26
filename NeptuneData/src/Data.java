
public class Data {

	public static void loadBaseData() {

		// Load raw SalaryBand data
		try {
			ParseSalaryBand.loadRaw();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		// Load raw Promotion data
		try {
			ParsePromotion.loadRaw();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		// Load raw Vehicle data
		try {
			ParseVehicle.loadRaw();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// Load raw Employee data
		try {
			ParseEmployee.loadRaw();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// Load raw Dealership data
		try {
			ParseDealership.loadRaw();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		//WARNING: Takes 20-25 minutes to complete. 

 		// Load raw VehicleSales data
//		try {
//			ParseVehicleSaleRaw.loadRaw();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
//		
	}
}

import java.util.List;

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
		
		// Parse customer satisfaction ratings
		List<CustomerSatisfaction> ratings; 
		try {
			ratings = ParseCustomerSatisfaction.parse();
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
		
		// Load non-calculated data into the Employees table
		List<Employee> employees = Employee.getAllBase();
		List<SalaryBand> bands = SalaryBand.getAllRaw();
		
		//Load Customer Satisfaction ratings
		employees = Employee.mergeCustomerSatisfactionRatings(employees, ratings);
		ParseEmployee.updateRatings(employees);

		//Load Salary Band data into Employee
		employees = Employee.mergeSalaryBands(employees, bands);
		ParseEmployee.updateSalaryBands(employees);

		
		// Load raw Dealership data
		try {
			ParseDealership.loadRaw();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Load raw VehicleSales data
		try {
			ParseVehicleSale.loadRaw();
		} catch (Exception e) {
			e.printStackTrace();
		}		

		
	}
}

/**
 * Calculate pre-calculated data fields for employees, promotions, and dealerships.
 *
 * Implements the run method of the EmployeeCalc Class: {@link EmployeeCalc#run()}
 * Implements the run method of the PromotionCalc Class: {@link PromotionCalc#run()}
 * Implements the run method of the DealershipCalc Class: {@link DealershipCalc#run()}
 *
 * @version 1.0
 */
public class Calculate {

	public static void run() {

		// Employee-related calculations
		try {
			EmployeeCalc.run();
		} catch (Exception e) {
			e.printStackTrace();
		}	

		// Promotion-related calculations
		try {
			PromotionCalc.run();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Dealership-related calculations
		try {
			DealershipCalc.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

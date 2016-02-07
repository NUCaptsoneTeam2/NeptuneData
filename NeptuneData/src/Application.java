
public class Application {

	public static void main(String[] args) {

		// Data
		try {
			Data.loadBaseData();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Calculations
		try {
			Calculate.run();
		} catch (Exception e) {
			e.printStackTrace();
		}	


	}
}


/**
 * Primary class to load and calculate Neptune Auto application data.
 *
 * @version 1.0
 **/
public class Application {

	public static void main(String[] args) {

		// Load data from text files.
		try {
			Data.loadBaseData(true, true, true, true, true, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Calculate pre-calculated fields.
		try {
			Calculate.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

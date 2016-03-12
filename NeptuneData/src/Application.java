
/**
 * Primary class to load and calculate Neptune Auto application data.
 *
 * Implements the loadBaseData Method of the Data class {@link Data#loadBaseData(boolean, boolean, boolean, boolean, boolean, boolean)}
 * Implements the run method of the Calculate class {@link Calculate#run()}
 *
 * @version 1.0
 **/
public class Application {
	
	/*
	 * Neptune Automobiles
	 * Northwestern University
	 * CIS 498
	 * Group 2
	 * Sandip Patil, Chad Caswell, Drew Collier, Corey Spacht
	 * 
	 */

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

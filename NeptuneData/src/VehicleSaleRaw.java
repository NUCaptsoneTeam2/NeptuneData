import java.util.List;

/**
 * Define objects Vehicle sales information.
 *
 * @version 1.0
 */
public class VehicleSaleRaw {

	private int employeeId;
	private int dealershipId;
	private int monthNum;
	private int ns100;
	private int ns200;
	private int ns300;
	private int nc150;
	private int nc250;
	private int nc350;
	private int np400;
	private int np500;
	private int nu600;
	private int nu700;

    /**
     * CONSTRUCTOR
     *
     * Create vehicle sales objects from raw data.
     *
     * @param employeeId    Employee identification number
     * @param monthNum      Numeric representation of the month of the sales record
     * @param ns100         Count of NS100 vehicle sales for the month
     * @param ns200         Count of NS200 vehicle sales for the month
     * @param ns300         Count of NS300 vehicle sales for the month
     * @param nc150         Count of NC150 vehicle sales for the month
     * @param nc250         Count of NC250 vehicle sales for the month
     * @param nc350         Count of NC350 vehicle sales for the month
     * @param np400         Count of NP400 vehicle sales for the month
     * @param np500         Count of NP500 vehicle sales for the month
     * @param nu600         Count of NU600 vehicle sales for the month
     * @param nu700         Count of NU700 vehicle sales for the month
     */
	public VehicleSaleRaw(int employeeId, int monthNum, int ns100, int ns200, int ns300, int nc150, int nc250, int nc350,
			int np400, int np500, int nu600, int nu700) {
		this.employeeId = employeeId;
		this.monthNum = monthNum;
		this.ns100 = ns100;
		this.ns200 = ns200;
		this.ns300 = ns300;
		this.nc150 = nc150;
		this.nc250 = nc250;
		this.nc350 = nc350;
		this.np400 = np400;
		this.np500 = np500;
		this.nu600 = nu600;
		this.nu700 = nu700;
	}

    /**
     * @return Identification number of the employee responsible for the dales
     */
	public int getEmployeeId() {
		return employeeId;
	}

    /**
     * @return Dealership identification number
     */
	public int getDealershipId() {
		return dealershipId;
	}

    /**
     * Set dealership ID of the sales record.
     * @param id    Dealership Identification number
     */
	public void setDealershipId(int id) {
		this.dealershipId = id;
	}

    /**
     * @return numeric representation of the month the sales were made.
     */
	public int getMonthNum() {
		return monthNum;
	}

    /**
     * @return count of NS100 vehicle model sales
     */
	public int getNs100() {
		return ns100;
	}

    /**
     * @return count of NS200 vehicle model sales
     */
	public int getNs200() {
		return ns200;
	}

    /**
     * @return count of NS300 vehicle model sales
     */
	public int getNs300() {
		return ns300;
	}

    /**
     * @return count of NC150 vehicle model sales
     */
	public int getNc150() {
		return nc150;
	}

    /**
     * @return count of NC250 vehicle model sales
     */
	public int getNc250() {
		return nc250;
	}

    /**
     * @return count of NC350 vehicle model sales
     */
	public int getNc350() {
		return nc350;
	}

    /**
     * @return count of NP400 vehicle model sales
     */
	public int getNp400() {
		return np400;
	}

    /**
     * @return count of NP500 vehicle model sales
     */
	public int getNp500() {
		return np500;
	}

    /**
     * @return count of NU600 vehicle model sales
     */
	public int getNu600() {
		return nu600;
	}

    /**
     * @return count of NU700 vehicle model sales
     */
	public int getNu700() {
		return nu700;
	}

	/**
	 * Convert vehicle sales object information into a readable format that can be used for
	 * debugging purposes.
	 *
	 * @return  String displaying pertinent information about the vehicle sales object.
	 */
	@Override
	public String toString() {
		return "VehicleSaleRaw [employeeId=" + employeeId + ", monthNum=" + monthNum + ", ns100=" + ns100 + ", ns200="
				+ ns200 + ", ns300=" + ns300 + ", nc150=" + nc150 + ", nc250=" + nc250 + ", nc350=" + nc350 + ", np400="
				+ np400 + ", np500=" + np500 + ", nu600=" + nu600 + ", nu700=" + nu700 + "]";
	}

	/**
	 * The mergeDealershipId object attaches the dealership ID to each vehicle sales record. Sales records
	 * do not contain this information by default. This allows dealership-level data to be pre-calculated.
	 * @param sales		List of vehicle sales records
	 * @param employees	List of employees
     * @return list of sales with dealership ID attached to the object
     */
	public static List<VehicleSaleRaw> mergeDealershipId(List<VehicleSaleRaw> sales, List<Employee> employees) {
		for (VehicleSaleRaw sale : sales) {
			for (Employee emp : employees) {
				if (emp.getEmployeeId() == sale.getEmployeeId()) {
					sale.setDealershipId(emp.getDealershipId());
					break;
				}
			}
		}
		return sales;
	}
	
}


public class VehicleSale {

	private int employeeId;
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
	
	public VehicleSale(int employeeId, int monthNum, int ns100, int ns200, int ns300, int nc150, int nc250, int nc350,
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

	public int getEmployeeId() {
		return employeeId;
	}

	public int getMonthNum() {
		return monthNum;
	}

	public int getNs100() {
		return ns100;
	}

	public int getNs200() {
		return ns200;
	}

	public int getNs300() {
		return ns300;
	}

	public int getNc150() {
		return nc150;
	}

	public int getNc250() {
		return nc250;
	}

	public int getNc350() {
		return nc350;
	}

	public int getNp400() {
		return np400;
	}

	public int getNp500() {
		return np500;
	}

	public int getNu600() {
		return nu600;
	}

	public int getNu700() {
		return nu700;
	}

	@Override
	public String toString() {
		return "VehicleSale [employeeId=" + employeeId + ", monthNum=" + monthNum + ", ns100=" + ns100 + ", ns200="
				+ ns200 + ", ns300=" + ns300 + ", nc150=" + nc150 + ", nc250=" + nc250 + ", nc350=" + nc350 + ", np400="
				+ np400 + ", np500=" + np500 + ", nu600=" + nu600 + ", nu700=" + nu700 + "]";
	}

	
}

import java.util.List;

public class Dealership {

	private int dealershipId;
	private String city;
	private String state;
	private int zip;
	private Employee manager;
	private int operatingCosts;
	private List<Promotion> promotions;
	
	public Dealership(int dealershipId, String city, String state, int zip, Employee manager, int operatingCosts,
			List<Promotion> promotions) {
		this.dealershipId = dealershipId;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.manager = manager;
		this.operatingCosts = operatingCosts;
		this.promotions = promotions;
	}
	
	public int getDealershipId() {
		return dealershipId;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public int getZip() {
		return zip;
	}
	public Employee getManager() {
		return manager;
	}
	public int getOperatingCosts() {
		return operatingCosts;
	}
	public List<Promotion> getPromotions() {
		return promotions;
	}
	
}

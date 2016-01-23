import java.util.List;

public class Dealership {

	private int dealershipId;
	private String city;
	private String state;
	private int zip;
	private Employee manager;
	private int managerId;
	private int operatingCosts;
	private List<Promotion> promotions;
	private String promoIds; 
	
	public Dealership(int dealershipId, String city, String state, int zip, Employee manager, int operatingCosts,
			List<Promotion> promotions) {
		this.dealershipId = dealershipId;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.manager = manager;
		//TODO: do we need to set managerId for any reason?
		//this.managerId = this.manager.
		this.operatingCosts = operatingCosts;
		this.promotions = promotions;
	}

	public Dealership(int dealershipId, String city, String state, int zip, int managerId, int operatingCosts,
			String promotionList) {
		this.dealershipId = dealershipId;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.managerId = managerId;
		this.operatingCosts = operatingCosts;
		this.promoIds = promotionList;
		
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
	public int getManagerId() {
		return managerId;
	}
	public int getOperatingCosts() {
		return operatingCosts;
	}
	public List<Promotion> getPromotions() {
		return promotions;
	}
	public String getPromoIds() {
		return promoIds;
	}

	@Override
	public String toString() {
		return "Dealership [dealershipId=" + dealershipId + ", city=" + city + ", state=" + state + ", zip=" + zip
				+ ", manager=" + managerId + ", operatingCosts=" + operatingCosts + ", promotions=" + promoIds + "]";
	}
	
}

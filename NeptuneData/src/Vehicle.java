
public class Vehicle {

	private String model;
	private Make make;
	private int cost;
	private int tagPrice;
	
	public Vehicle(String model, Make make, int cost, int tagPrice)
	{
		this.model = model;
		this.make = make;
		this.cost = cost;
		this.tagPrice = tagPrice;
		
	}
	
	public String getModel() {
		return model;
	}

	public Make getMake() {
		return make;
	}

	public int getCost() {
		return cost;
	}

	public int getTagPrice() {
		return tagPrice;
	}
	
	public enum Make
	{
	  ALL, Coupe, Pickup, Sedan, SUV;
	}

}

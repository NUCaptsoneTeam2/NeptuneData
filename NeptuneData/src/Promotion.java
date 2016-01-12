import java.util.List;

public class Promotion {

	private String promotionId;
	private List<Month> months;
	private int cashbackBonus;
	private List<Vehicle.Make> make;
	
	
	public Promotion(String promotionId, List<Month> months, int cashbackBonus, List<Vehicle.Make> make) {
		this.promotionId = promotionId;
		this.months = months;
		this.cashbackBonus = cashbackBonus;
		this.make = make;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public List<Month> getMonths() {
		return months;
	}

	public int getCashbackBonus() {
		return cashbackBonus;
	}

	public List<Vehicle.Make> getMake() {
		return make;
	}
	

	public enum Month
	{
	  JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4), MAY(5), JUNE(6),
	  JULY(7), AUGUST(8), SEPTEMBER(9), OCTOBER(10), NOVEMBER(11), DECEMBER(12);
		
		private int value;

		private Month(int value) { this.value = value; }

		public int getValue() {
			return value;
		}	
		
	}

}


import java.util.List;

public class Promotion {

	private String promotionId;
	private List<Month> months;
	private String monthIds;
	private int cashBackBonus;
	private List<Vehicle.Make> make;
	private String makeIds;


	public Promotion(String promotionId, List<Month> months, int cashBackBonus, List<Vehicle.Make> make) {
		this.promotionId = promotionId;
		this.months = months;
		this.cashBackBonus = cashBackBonus;
		this.make = make;
	}

	public Promotion(String promotionId, String monthIds, int cashBackBonus, String makeList) {
		this.promotionId = promotionId;
		this.monthIds = monthIds;
		this.cashBackBonus = cashBackBonus;
		this.makeIds = makeList;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public List<Month> getMonths() {
		return months;
	}

	public String getMonthIds() {
		return monthIds;
	}

	public int getCashbackBonus() {
		return cashBackBonus;
	}

	public List<Vehicle.Make> getMake() {
		return make;
	}

	public String getMakeIDs() {
		return makeIds;
	}

	@Override
	public String toString() {
		return "Promotion [promotionId=" + promotionId + ", months=" + monthIds + ", cashbackBonus=" + cashBackBonus
				+ ", make=" + makeIds + "]";
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


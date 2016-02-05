public class Promotion {

	private String promotionId;
	private int month;
	private int cashBackBonus;
	private String make;


	public Promotion(String promotionId, int monthNum, int cashBackBonus, String make) {
		this.promotionId = promotionId;
		this.month = monthNum;
		this.cashBackBonus = cashBackBonus;
		this.make = make;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public int getMonth() {
		return month;
	}

	public int getCashbackBonus() {
		return cashBackBonus;
	}

	public String getMake() {
		return make;
	}


	@Override
	public String toString() {
		return "Promotion [promotionId=" + promotionId + ", month=" + month + ", cashBackBonus=" + cashBackBonus
				+ ", make=" + make + "]";
	}


}


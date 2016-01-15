
public class SalaryBand {

	private String band;
	private int minimum;
	private int maximum;
	private int paidTimeOffDays;
	private float bonusPercentage;
	
	public SalaryBand(String band, int minimum, int maximum, int paidTimeOffDays, float bonusPercentage) {
		this.band = band;
		this.minimum = minimum;
		this.maximum = maximum;
		this.paidTimeOffDays = paidTimeOffDays;
		this.bonusPercentage = bonusPercentage;
	}

	public String getBand() {
		return band;
	}

	public int getMinimum() {
		return minimum;
	}

	public int getMaximum() {
		return maximum;
	}

	public int getPaidTimeOffDays() {
		return paidTimeOffDays;
	}

	public float getBonusPercentage() {
		return bonusPercentage;
	}

	@Override
	public String toString() {
		return "SalaryBand [band=" + band + ", minimum=" + minimum + ", maximum=" + maximum + ", paidTimeOffDays="
				+ paidTimeOffDays + ", bonusPercentage=" + bonusPercentage + "]";
	}
	

}

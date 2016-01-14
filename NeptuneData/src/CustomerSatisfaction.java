
public class CustomerSatisfaction {

	private Employee employee;
	private int num5stars;
	private int num4stars;
	private int num3stars;
	private int num2stars;
	private int num1stars;
	
	public CustomerSatisfaction(Employee employee, int num5stars, int num4stars, int num3stars, int num2stars,
			int num1stars) {
		this.employee = employee;
		this.num5stars = num5stars;
		this.num4stars = num4stars;
		this.num3stars = num3stars;
		this.num2stars = num2stars;
		this.num1stars = num1stars;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	public int getNum5stars() {
		return num5stars;
	}
	public int getNum4stars() {
		return num4stars;
	}
	public int getNum3stars() {
		return num3stars;
	}
	public int getNum2stars() {
		return num2stars;
	}
	public int getNum1stars() {
		return num1stars;
	}
	
	
	
}

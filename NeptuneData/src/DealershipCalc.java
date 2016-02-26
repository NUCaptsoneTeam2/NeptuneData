import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DealershipCalc {
	public static void run() throws NumberFormatException, IOException 
	{
		//Prepare objects
		List<Dealership> dealerships = Dealership.getAllBase();
		
		System.out.println();System.out.println();
		
		// Assign employees/managers to dealerships
		for (Dealership dealer : dealerships) {
			double netProfits = 0; 
			int carSales = 0;
			int carCost = 0;
			int operatingCost = dealer.getOperatingCosts();
			int salaries = 0;
			double bonuses = 0;
			
			List<VehicleSale> sales = VehicleSale.getAllSalesByDealershipID(dealer.getDealershipId(), true);
			for (VehicleSale sale : sales) {
				carSales += sale.getTotalSalesAmount();
				carCost += sale.getTotalCost();
			}
			
			List<Employee> employees = Employee.getAllBaseInfoByDealership(dealer.getDealershipId());
			for (Employee emp : employees) {
				salaries += emp.getBaseSalary();
				bonuses += (emp.getBaseSalary() * emp.getBonusPct());
			}
			
			netProfits = carSales - (carCost + operatingCost + salaries + bonuses);
			
			//CREDIT on dealing with Currency in Java: http://blog.eisele.net/2011/08/working-with-money-in-java.html
			BigDecimal netProfits2 = new BigDecimal(netProfits).setScale(2, BigDecimal.ROUND_HALF_UP);
			NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
			BigDecimal bonuses2 = new BigDecimal(bonuses).setScale(2, BigDecimal.ROUND_HALF_UP);

			
			System.out.println(String.format("Dealership %s Net Profits: %s; Formula: %s - (%s + %s + %s + %s)", 
					dealer.getDealershipId(),
					fmt.format(netProfits2), carSales, carCost, operatingCost, salaries, fmt.format(bonuses2)));
			
			dealer.setNetProfits(netProfits2.doubleValue());
			dealer.saveNetProfit();

		}
		
	}
}

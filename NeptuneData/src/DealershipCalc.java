import java.io.IOException;
import java.util.List;

public class DealershipCalc {
	public static void run() throws NumberFormatException, IOException 
	{
		//Prepare objects
		List<Dealership> dealerships = Dealership.getAllBase();
		
		// Assign employees/managers to dealerships
		for (Dealership dealer : dealerships) {
			long netProfits = 0; 
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
			
			netProfits = carSales - (carCost + operatingCost + salaries + Math.round(bonuses));
			
			System.out.println(String.format("Dealership %s Net Profits: %s; Formula: %s - (%s + %s + %s + %s)", 
					dealer.getDealershipId(),
					Math.round(netProfits), carSales, carCost, operatingCost, salaries, Math.round(bonuses)));
			
			dealer.setNetProfits(netProfits);
			dealer.saveNetProfit();

		}
		
	}
}

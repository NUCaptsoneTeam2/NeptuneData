import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
//import java.util.HashMap;
import java.util.List;

public class EmployeeCalc {

	public static void run() throws NumberFormatException, IOException 
	{
		//Prepare objects
		List<Employee> employees = Employee.getAllRaw();
		List<SalaryBand> bands = SalaryBand.getAllRaw();
		List<CustomerSatisfaction> ratings = CustomerSatisfaction.getAllRaw();
		List<Employee> managers = Employee.getAllManagers();
		employees = Employee.mergeCustomerSatisfactionRatings(employees, ratings);	
		employees = Employee.mergeManagerList(employees, managers);
		List<Dealership> dealerships = Dealership.getAllRaw();

		//Calculate bonus before cust sat ratings
		for (Employee emp : employees) {
			for (SalaryBand band : bands) {
				if ((emp.getBaseSalary() >= band.getMinimum()) && 
						(emp.getBaseSalary() <= band.getMaximum())
						)
				{
					//set base bonus on emp object
					emp.setBonusPct(band.getBonusPercentage());
					System.out.println(String.format("Base bonus for employee %s is %s; satisfaction bonus is %s; total points is %s. (Range is %s - %s)", 
							emp, 
							emp.getBonusPct(),
							emp.getBonusPctSatisfaction(),
							emp.getBonusPoints(),
							band.getMinimum(), 
							band.getMaximum()));
					break;
				}
			}
		}

		//Calculate bonus including cust sat ratings
		for (Employee emp : employees) {
			//RULE: If employee is a manager, do not include in calc.
			if (emp.getIsManager()) {
				System.out.println(String.format("Employee %s (%s) is a manager (ineligible)", emp.getEmployeeId(), emp.getName()));
			}
			else {
				CustomerSatisfaction sat = emp.getCustSat();
				int pts5stars = sat.getNum5stars() * 2;
				int pts4stars = sat.getNum4stars() * 1;
				//skip 3 stars as multiplier is always 0
				int pts2stars = sat.getNum2stars() * -1;
				int pts1stars = sat.getNum1stars() * -2;

				int totalPoints = pts5stars + pts4stars + pts2stars + pts1stars;

				float bonusPct = 0;
				if (pts5stars != 0)
					bonusPct += 0.02;
				if (pts4stars != 0)
					bonusPct += 0.01;
				if (pts2stars != 0)
					bonusPct += -0.01;
				if (pts1stars != 0)
					bonusPct += -0.02;

				if (bonusPct < 0) //did we go negative? No can do! 
					bonusPct = 0;

				//set base bonus points/percentage
				emp.setBonusPoints(totalPoints);
				emp.setBonusPctSatisfaction(bonusPct);

				System.out.println(String.format("Employee %s (%s) earned %s points (%s + %s + 0 + %s + %s = %s)", 
						emp.getEmployeeId(), 
						emp.getName(), 
						totalPoints,
						pts5stars, pts4stars, pts2stars, pts1stars, totalPoints));
			}
		}

		// Assign employees/managers to dealerships
		for (Dealership dealer : dealerships) {

			for (Employee emp : employees) {
				if (emp.getIsManager() && (dealer.getDealershipId() == emp.getDealershipId())) {
					dealer.setManager(emp);
					System.out.println(String.format("Employee %s (%s) is a manager at dealership %s", 
							emp.getEmployeeId(), 
							emp.getName(), 
							dealer.getDealershipId()));
					break; //there is only one manager per dealership, so a break is acceptable here
				}
			}

			//Add list of EEs for this dealership to the Dealership.EE list 
			dealer.addEmployees(EmployeeCalc.filterEmployeesByDealershipId(employees, dealer.getDealershipId()));

		}
		
/*		for (Dealership dealer : dealerships) {
			if (dealer.getDealershipId() == dealershipId)
				Collections.sort(dealer.getEmployees(), new Comparator()) {
		                (i2.intValue() > i1.intValue()) ? 1 : -1;
		            }
		}*/
		
	}



	/*	private static List<Dealership> filterDealerships(List<Dealership> dealerships, int dealershipId) {

		List<Dealership> list = new ArrayList<Dealership>();

		for (Dealership dealer : dealerships) {
			if (dealer.getDealershipId() == dealershipId)
				list.add(dealer);
		}
		return list;
	}*/

	private static List<Employee> filterEmployeesByDealershipId(List<Employee> employees, int dealershipId) {
		List<Employee> list = new ArrayList<Employee>();
		for (Employee emp : employees) {
			if (emp.getDealershipId() == dealershipId)
				list.add(emp);
		}
		return list;
	}
}

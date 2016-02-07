import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
//import java.util.HashMap;
import java.util.List;

public class EmployeeCalc {

	public static void run() throws NumberFormatException, IOException 
	{
		//Prepare objects
		List<Employee> employees = Employee.getAllBase();
		List<SalaryBand> bands = SalaryBand.getAllRaw();
		List<Employee> managers = Employee.getAllManagers();
		employees = Employee.mergeManagerList(employees, managers);
		List<Dealership> dealerships = Dealership.getAllBase();
		List<CustomerSatisfaction> ratings; 
		try {
			ratings = ParseCustomerSatisfaction.parse();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		//More prep of ratigs and salary band data on the employees list
		employees = Employee.mergeCustomerSatisfactionRatings(employees, ratings);
		employees = Employee.mergeSalaryBands(employees, bands);

		//Save/update DB with calculations
		calculateAndStoreCustomerSatisfactionRatings(employees);
		calculateAndStoreSalaryBands(employees);
		
		//this how far we've gotten with programming calculations; method works but is not yet complete
		calculateBonusWithCustomerRatings(employees, dealerships);

	}


	static private void calculateAndStoreCustomerSatisfactionRatings(List<Employee> employees) {
		ParseEmployee.updateRatings(employees);
	}
	
	static private void calculateAndStoreSalaryBands(List<Employee> employees) {
		ParseEmployee.updateSalaryBands(employees);
	}
	
	static private void calculateBonusWithCustomerRatings(List<Employee> employees, List<Dealership> dealerships) {
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


		for (Dealership dealer : dealerships) {
			//sorting algorithm, found in comments on http://www.mkyong.com/java8/java-8-lambda-comparator-example/
			dealer.getEmployees().sort(Comparator.comparing(Employee::getBonusPctSatisfaction).reversed());
			
			//TODO: complete bonus logic for picking top [n] earners

		}
	}
	
	private static List<Employee> filterEmployeesByDealershipId(List<Employee> employees, int dealershipId) {
		List<Employee> list = new ArrayList<Employee>();
		for (Employee emp : employees) {
			if (emp.getDealershipId() == dealershipId)
				list.add(emp);
		}
		return list;
	}
}

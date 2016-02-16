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
//		calculateAndStoreCustomerSatisfactionRatings(employees);
//		calculateAndStoreSalaryBands(employees);
		
		//this how far we've gotten with programming calculations; method works but is not yet complete
		calculateBonusWithCustomerRatings(employees, dealerships);

	}


	private static void calculateAndStoreCustomerSatisfactionRatings(List<Employee> employees) {
		ParseEmployee.updateRatings(employees);
	}
	
	private static void calculateAndStoreSalaryBands(List<Employee> employees) {
		ParseEmployee.updateSalaryBands(employees);
	}
	
	static private void calculateBonusWithCustomerRatings(List<Employee> employees, List<Dealership> dealerships) {
		
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

/*				float bonusPct = 0;
				if (pts5stars != 0)
					bonusPct += 0.02;
				if (pts4stars != 0)
					bonusPct += 0.01;
				if (pts2stars != 0)
					bonusPct += -0.01;
				if (pts1stars != 0)
					bonusPct += -0.02;

				if (bonusPct < 0) //did we go negative? No can do! 
					bonusPct = 0;*/

				//set base bonus points/percentage
				emp.setBonusPoints(totalPoints);
				//emp.setBonusPctSatisfaction(bonusPct);

				System.out.println(String.format("Employee %s (%s) earned %s points (%s + %s + 0 + %s + %s = %s)", 
						emp.getEmployeeId(), 
						emp.getName(), 
						totalPoints,
						pts5stars, pts4stars, pts2stars, pts1stars, totalPoints));
			}
		}


		float pct3 = (float)0.03;
		float pct2 = (float)0.02;
		float pct1 = (float)0.01;
		
		for (Dealership dealer : dealerships) {
			List<Employee> empsNoManagers = new ArrayList<>();
			for (Employee emp : dealer.getEmployees()) {
				if (!emp.getIsManager().booleanValue()) 
					empsNoManagers.add(emp);
			}

			
			System.out.println();
			System.out.println();
			System.out.println(String.format("*** Possible list of employees (excluding managers) for dealership %s ***", dealer.getDealershipId()));
			
			//sorting algorithm, found in comments on http://www.mkyong.com/java8/java-8-lambda-comparator-example/
			empsNoManagers.sort(Comparator.comparing(Employee::getBonusPoints).reversed());

			for (Employee emp : empsNoManagers) {
				System.out.println(String.format("Employee %s (%s) has %s bonus points and a possible %s pct bonus.", 
						emp.getEmployeeId(), 
						emp.getName(), 
						emp.getBonusPoints(),
						emp.getBonusPct()));
			}			
			
			System.out.println();
			System.out.println(String.format("*** Final list of employees for dealership %s  earning a bonus ***", dealer.getDealershipId()));
			
			//TODO: complete bonus logic for picking top [n] earners
			int prev = empsNoManagers.get(0).getBonusPoints(); //get first emp bonus point
			int i = 1;
			for (Employee emp : empsNoManagers) {
				
				// top 3 processing
				if (i < 4) {
					if (i == 1)
						emp.setBonusPctSatisfaction(pct3);
					
					if (i == 2 && emp.getBonusPoints() < prev)
						emp.setBonusPctSatisfaction(pct2);
					else if (i == 2 && emp.getBonusPoints() == prev)
						emp.setBonusPctSatisfaction(pct3);
					else
						//do nothing
					
					if (i == 3 && emp.getBonusPoints() < prev)
						emp.setBonusPctSatisfaction(pct2);
					else if (i == 3 && emp.getBonusPoints() == prev)
						emp.setBonusPctSatisfaction(empsNoManagers.get(i-2).getBonusPctSatisfaction());
					else
						//do nothing?    emp.setBonusPctSatisfaction(pct1);
					
					System.out.println(String.format("Employee %s (%s) has %s bonus points and is in the top 3 empoyees. They will earn a %s pct bonus.", 
							emp.getEmployeeId(), 
							emp.getName(), 
							emp.getBonusPoints(),
							emp.getBonusPctSatisfaction()));
				}

				//greater than top 3
				if (i > 3) {
					if (emp.getBonusPoints() < prev) {
						System.out.println(String.format("Employee %s (%s) has %s bonus points, which is less than Employee %s who has %s points. We've reached our top candidates.", 
								emp.getEmployeeId(), 
								emp.getName(), 
								emp.getBonusPoints(),
								empsNoManagers.get(i-2).getEmployeeId(),
								empsNoManagers.get(i-2).getBonusPoints()));
						break; //we are done
					}
					if (emp.getBonusPoints() == prev) {
						System.out.println(String.format("Employee %s (%s) has %s bonus points, which is equal to Employee %s who also has %s points. Include this Employee for bonus.", 
								emp.getEmployeeId(), 
								emp.getName(), 
								emp.getBonusPoints(),
								empsNoManagers.get(i-2).getEmployeeId(),
								empsNoManagers.get(i-2).getBonusPoints()));
					}
				}
				
				prev = emp.getBonusPoints();
				i++;
			}
		
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

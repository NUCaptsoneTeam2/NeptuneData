import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class EmployeeCalc {

	public static void run() throws NumberFormatException, IOException 
	{
		//Prep objects
		List<Employee> employees = Employee.getAllRaw();
		List<SalaryBand> bands = SalaryBand.getAllRaw();
		List<CustomerSatisfaction> ratings = CustomerSatisfaction.getAllRaw();
		List<Employee> managers = Employee.getAllManagers();
		employees = Employee.mergeCustomerSatisfactionRatings(employees, ratings);	
		employees = Employee.mergeManagerList(employees, managers);
		List<Dealership> dealerships = Dealership.getAllRaw();
		//HashMap<Integer, Dealership> dlrMap = EmployeeCalc.getHmDealerships(dealerships);


		// Calculate bonus before cust sat ratings
		int i = 0;
		while (i < employees.size()) {
			int j = 0;
			while (j < bands.size()) {
				Employee emp = employees.get(i);
				SalaryBand band = bands.get(j);
				if ((emp.getBaseSalary() >= band.getMinimum()) && 
						(emp.getBaseSalary() <= band.getMaximum())
						)
				{
					//set base bonus on emp object
					employees.get(i).setBonusPct(bands.get(j).getBonusPercentage());
					System.out.println(String.format("Base bonus for employee %s is %s. (Range is %s - %s)", 
							employees.get(i), 
							bands.get(j).getBonusPercentage(), 
							bands.get(j).getMinimum(), 
							bands.get(j).getMaximum()));
					break;
				}
				j++;
			}
			i++;
		}

		// Calculate bonus including cust sat ratings
		i = 0; //reset i
		while (i < employees.size()) {
			Employee emp = employees.get(i);

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

				//set base bonus on emp object
				employees.get(i).setBonusPoints(totalPoints);

				System.out.println(String.format("Employee %s (%s) earned %s points (%s + %s + 0 + %s + %s = %s)", 
						emp.getEmployeeId(), 
						emp.getName(), 
						totalPoints,
						pts5stars, pts4stars, pts2stars, pts1stars, totalPoints));
			}
			i++;
		}

		// Assign employees/managers to dealerships
		i = 0; //reset i
		while (i < dealerships.size()) {
			int j = 0;
			Dealership dealer = dealerships.get(i);
			int dealerId = dealer.getDealershipId();
			
			while (j < employees.size()) {
				Employee emp = employees.get(j);
				if (dealerId != emp.getDealershipId())
					break;

				if (emp.getIsManager()) {
					dealerships.get(i).setManager(emp);
					System.out.println(String.format("Employee %s (%s) is a manager at dealership %s", 
							emp.getEmployeeId(), 
							emp.getName(), 
							dealer.getManagerId()));
				}
				else {
					dealerships.get(i).addEmployee(emp);
					System.out.println(String.format("Employee %s (%s) added to dealership %s", 
							emp.getEmployeeId(), 
							emp.getName(), 
							dealer.getDealershipId()));
				}
				j++;
			}
			i++;
		}

	}



/*	private static HashMap<Integer, Dealership> getHmDealerships(List<Dealership> dealerships){
		HashMap<Integer, Dealership> dlrMap = new HashMap<Integer, Dealership>();
		int i=0;
		while (dealerships.size() > i){
			dlrMap.put(dealerships.get(i).getDealershipId(), dealerships.get(i));
			i++;
		}
		return dlrMap;
	}*/

}

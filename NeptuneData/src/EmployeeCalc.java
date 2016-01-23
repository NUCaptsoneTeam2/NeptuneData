import java.io.IOException;
import java.util.List;

public class EmployeeCalc {

	public static void run() throws NumberFormatException, IOException 
	{
		List<Employee> employees = Employee.getAllRaw();
		List<SalaryBand> bands = SalaryBand.getAllRaw();

		int i = 0;
		while (i < employees.size()) {
			int j = 0;
			while (j < bands.size()) {
				if ((employees.get(i).getBaseSalary() >= bands.get(j).getMinimum()) && 
						(employees.get(i).getBaseSalary() <= bands.get(j).getMaximum())
						)
				{
					System.out.println(String.format("Bonus for employee %s is %s. (Range is %s - %s)", employees.get(i), bands.get(j).getBonusPercentage(), bands.get(j).getMinimum(), bands.get(j).getMaximum()));
					break;
				}
				j++;
			}
			i++;
		}


	}

	/*	private void calcBonus(list<Employee>) 
	{

	}
	 */

}

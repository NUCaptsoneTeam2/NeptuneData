import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Calculate the sales and profit amounts with and without taking promotions
 * into account.
 */
public class PromotionCalc {

    /**
     * This method runs the promotions calculation.
     *
     * Implements updateVehicleSalesWithoutSales method of the VehicleSale class {@link VehicleSale#updateVehicleSalesWithoutSales()}
     * Implements calculateSalesProfitsWithPromotions method of the PromotionCalc Class {@link PromotionCalc#calculateSalesProfitsWithPromotions(List, List)}
     * Implements calculateSalesProfitsWithoutPromotions method of the PromotionCalc Class {@link PromotionCalc#calculateSalesProfitsWithoutPromotions(List)}
     *
     * @throws NumberFormatException
     * @throws IOException
     */
	public static void run() throws NumberFormatException, IOException 
	{
		//Prepare objects
		List<Vehicle> vehicles = Vehicle.getAll();
		List<Dealership> dealers = Dealership.getAllBase();

		VehicleSale.updateVehicleSalesWithoutSales();
		PromotionCalc.calculateSalesProfitsWithPromotions(dealers, vehicles);
		PromotionCalc.calculateSalesProfitsWithoutPromotions(vehicles);

	}

    /**
     * Calculate the amount of sales & profits while taking promotions into account
     *
     * Implements updateVehicleSalesCalculations of the VehicleSale class {@link VehicleSale#updateVehicleSalesCalculations(List)}
     *
     * @param dealers   list of dealership objects
     * @param vehicles  list of vehicle objects
     */
	private static void calculateSalesProfitsWithPromotions(List<Dealership> dealers, List<Vehicle> vehicles) {
		List<List<VehicleSale>> masterSales = new ArrayList<List<VehicleSale>>(dealers.size());
		//Big, ugly, nested loop. This is better-suited for a TSQL join
		for (Dealership dealer : dealers) {
			System.out.println(String.format("Processing dealership %s @ %s", dealer.getDealershipId(), LocalDateTime.now()));
			List<VehicleSale> sales = VehicleSale.getAllSalesByDealershipID(dealer.getDealershipId(), false);
			if (sales.size() > 0) {
				masterSales.add(sales);
				List<Promotion> promotions = Promotion.getAllPromotionsByDealershipID(dealer.getDealershipId());

				for (Promotion promo : promotions) {
					for (VehicleSale sale : sales) {
						for (Vehicle vehicle : vehicles) {
							if ( (vehicle.getModel().compareToIgnoreCase(sale.getModelId()) == 0) && (promo.getMonth() == sale.getMonth()) ) {
								//Check to make sure this model is in the correct vehicle class/make for the promotion
								if (vehicle.getMake().compareToIgnoreCase(promo.getVehicleClass()) == 0) {
									//Connect sale to promotionId
									sale.setPromotionId(promo.getPromotionId());

									//Formula: Total Sales Amount
									int totalSalesAmt = ((vehicle.getTagPrice() - promo.getCashbackBonus()) * sale.getTotalSalesCount());
									sale.setTotalSalesAmount(totalSalesAmt);

									//Formula: Total Profit
									int totalProfit = totalSalesAmt - sale.getTotalCost(); 
									sale.setTotalProfit(totalProfit);

									//System.out.println(String.format("Updating dealership %s; cashback=%s", sale.getDealershipId(), promo.getCashbackBonus()));
									break;
								}
							}
						}
					}
				}
			}
		}

		for (List<VehicleSale> allSales : masterSales) {
			List<VehicleSale> allSalesFinal = new ArrayList<VehicleSale>(allSales.size());
			for (VehicleSale sale : allSales) {
				if (sale.getPromotionId() > 0)
					allSalesFinal.add(sale); //need to operate on sales with promotions
			}

			if (allSalesFinal.size() > 0)
				VehicleSale.updateVehicleSalesCalculations(allSalesFinal);
		}
	}

    /**
     * Calculate the total sales and profits before promotions are taken into account (i.e. ignore promotions)
     *
     * Implements updateVehicleSalesCalculations of the VehicleSale class {@link VehicleSale#updateVehicleSalesCalculations(List)}
     *
     * @param vehicles  list of vehicle objects
     */
	private static void calculateSalesProfitsWithoutPromotions(List<Vehicle> vehicles) {
		List<VehicleSale> sales = VehicleSale.getAllSalesWithoutPromotions();
		for (VehicleSale sale : sales) {
			for (Vehicle vehicle : vehicles) {
				if (vehicle.getModel().compareToIgnoreCase(sale.getModelId()) == 0) {
					//Formula: Total Sales Amount
					int totalSalesAmt = vehicle.getTagPrice() * sale.getTotalSalesCount();
					sale.setTotalSalesAmount(totalSalesAmt);

					//Formula: Total Profit
					int totalProfit = totalSalesAmt - sale.getTotalCost(); 
					sale.setTotalProfit(totalProfit);
					System.out.println(String.format("Updating %s", sale.toString()));
					break;
				}
			}
		}
		//Save to DB for "this" dealership
		VehicleSale.updateVehicleSalesCalculations(sales);
	}
}

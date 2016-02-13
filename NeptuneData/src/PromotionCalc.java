import java.io.IOException;
import java.util.List;

public class PromotionCalc {

	public static void run() throws NumberFormatException, IOException 
	{
		//Prepare objects
		List<Vehicle> vehicles = Vehicle.getAll();
		List<Dealership> dealers = Dealership.getAllBase();

		//Big, ugly, nested loop. This is better-suited for a TSQL join
		for (Dealership dealer : dealers) {
			List<VehicleSale> sales = VehicleSale.getAllSalesByDealershipID(dealer.getDealershipId());
			List<Promotion> promotions = Promotion.getAllPromotionsByDealershipID(dealer.getDealershipId());

			for (Promotion promo : promotions) {
				for (VehicleSale sale : sales) {
					for (Vehicle vehicle : vehicles) {
						if ( (vehicle.getModel().compareToIgnoreCase(sale.getModelId()) == 0) && (promo.getMonth() == sale.getMonth()) ) {

							//Connect sale to promotionId
							sale.setPromotionId(promo.getPromotionId());

							//Formula: Total Sales Amount
							int totalSalesAmt = ((vehicle.getTagPrice() - promo.getCashbackBonus()) * sale.getTotalSalesCount());
							sale.setTotalSalesAmount(totalSalesAmt);

							//Formula: Total Profit
							int totalProfit = totalSalesAmt - sale.getTotalCost(); 
							sale.setTotalProfit(totalProfit);

							System.out.println(String.format("Updating %s; cashback=%s", sale.toString(), promo.getCashbackBonus()));
							break;
						}
					}
				}
			}
			//Save to DB for "this" dealership
			VehicleSale.updateVehicleSalesCalculations(sales);
		}


	}
}

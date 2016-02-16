import java.text.NumberFormat;
import java.util.Locale;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class VehicleSaleTotal {

    /**
     * Calculate vehicle sales for a specified employee.
     */
    public static void calculateTotalCarSalesEmployee(int employeeID) {

        // Import all records for selected employee.
        // Declare the JDBC objects.
        Connection conn;
        Statement stmt;
        ResultSet rs;

        try {

            // Generate SQL Query
            String sql = "Select employeeID, "
                    + "month, "
                    + "modelID, "
                    + "dealershipID, "
                    + "promotionID, "
                    + "totalSalesCount, "
                    + "totalSalesAmount, "
                    + "totalCost, "
                    + "totalProfit "
                    + "FROM VehicleSales "
                    + "WHERE employeeID = %s";

            conn = ConnectionFactory.getConnection();
            stmt = conn.createStatement();

            List<VehicleSale> salesList = new ArrayList<>();

            rs = stmt.executeQuery(String.format(sql,employeeID));

            while (rs.next()) {
                VehicleSale item = new VehicleSale(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9));

                salesList.add(item);
            }

            // Only calculate yearly sales total if records exist, otherwise notify the user
            // no sales data exists for the specified employeeID.
            if(salesList.size() > 0){

                int totalSales = 0;

                for(VehicleSale sale : salesList){

                    totalSales += sale.getTotalSalesAmount();

                }

                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);


                System.out.println(String.format("Total sales for employee %s: %s", employeeID, currencyFormat.format(totalSales)));

            } else {

                System.out.println("No sales data exists for the specified employeeID: " + employeeID);

            }
        }

        // Handle errors.
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Calculate the total car sales for a specified dealership.
     */
    public static void calculateTotalCarSalesDealership(int dealershipID) {

        // Import all records for selected employee.
        // Declare the JDBC objects.
        Connection conn;
        Statement stmt;
        ResultSet rs;

        try {

            // Generate SQL Query
            String sql = "Select employeeID, "
                    + "month, "
                    + "modelID, "
                    + "dealershipID, "
                    + "promotionID, "
                    + "totalSalesCount, "
                    + "totalSalesAmount, "
                    + "totalCost, "
                    + "totalProfit "
                    + "FROM VehicleSales "
                    + "WHERE dealershipID = %s";

            conn = ConnectionFactory.getConnection();
            stmt = conn.createStatement();

            List<VehicleSale> salesList = new ArrayList<>();

            rs = stmt.executeQuery(String.format(sql,dealershipID));

            while (rs.next()) {
                VehicleSale item = new VehicleSale(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9));

                salesList.add(item);
            }

            // Only calculate yearly sales total if records exist, otherwise notify the user
            // no sales data exists for the specified employeeID.
            if(salesList.size() > 0){

                int totalSales = 0;

                for(VehicleSale sale : salesList){

                    totalSales += sale.getTotalSalesAmount();

                }

                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);


                System.out.println(String.format("Total sales for dealership %s: %s", dealershipID, currencyFormat.format(totalSales)));

            } else {

                System.out.println("No sales data exists for the specified dealershipID: " + dealershipID);

            }
        }

        // Handle errors.
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculate vehicle sales for a specified state.
     */
    public static void calculateTotalCarSalesState(String state) {

        //TODO: Validate user has input a valid state abbreviation.



        // Import all records for selected employee.
        // Declare the JDBC objects.
        Connection conn;
        Statement stmt;
        ResultSet rs;

        try {

            // Generate SQL Query
            String sql = "Select a.employeeID, "
                    + "a.month, "
                    + "a.modelID, "
                    + "a.dealershipID, "
                    + "a.promotionID, "
                    + "a.totalSalesCount, "
                    + "a.totalSalesAmount, "
                    + "a.totalCost, "
                    + "a.totalProfit "
                    + "FROM VehicleSales a "
                    + "LEFT JOIN Dealerships b on a.dealershipID = b.dealershipID "
                    + "WHERE b.State = \'%s\'";

            conn = ConnectionFactory.getConnection();
            stmt = conn.createStatement();

            List<VehicleSale> salesList = new ArrayList<>();

            rs = stmt.executeQuery(String.format(sql,state));

            while (rs.next()) {
                VehicleSale item = new VehicleSale(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9));

                salesList.add(item);
            }

            // Only calculate yearly sales total if records exist, otherwise notify the user
            // no sales data exists for the specified employeeID.
            if(salesList.size() > 0){

                int totalSales = 0;

                for(VehicleSale sale : salesList){

                    totalSales += sale.getTotalSalesAmount();

                }

                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);


                System.out.println(String.format("Total sales for State %s: %s", state, currencyFormat.format(totalSales)));

            } else {

                System.out.println("No sales data exists for the specified State: " + state);

            }
        }

        // Handle errors.
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Calculate vehicle sales across the nation (i.e. the entire dataset)
     */
    public static void calculateTotalCarSalesNational() {

        // Import all records for selected employee.
        // Declare the JDBC objects.
        Connection conn;
        Statement stmt;
        ResultSet rs;

        try {

            // Generate SQL Query
            String sql = "SELECT SUM(CONVERT(BIGINT,totalSalesAmount)) "
                    + "FROM VehicleSales;"
                    ;

            conn = ConnectionFactory.getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery(String.format(sql));

            // Query will only ever return one row, so this should be OK without a loop.
            rs.next();

            long totalSales = rs.getLong(1);

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

            System.out.println(String.format("Total sales nationwide: %s", currencyFormat.format(totalSales)));

        }

        // Handle errors.
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
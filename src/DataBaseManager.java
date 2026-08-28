import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class DataBaseManager {
    static String url = "jdbc:mysql://localhost:3306/fashion_retail";
    static String username = "root";
    public static Connection connection;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter MySQL password: ");
        String password = scanner.nextLine();

        // Connecting this Java program to the database
        try {
            connection = connectToDatabase(password);

            if (connection == null) {
                return;
            }

            System.out.println();
            System.out.println("Connected to MySQL successfully!");

            boolean running = true;

            while (running) { // showing the main menu to the user
                System.out.println();
                System.out.println("------------------------------------");
                System.out.println("       FASHION RETAIL SYSTEM");
                System.out.println("------------------------------------");
                System.out.println("1. View All Products");
                System.out.println("2. View Specific Product");
                System.out.println("3. Add Product");
                System.out.println("4. Update Product");
                System.out.println("5. Delete Product");
                System.out.println("6. Business Queries");
                System.out.println("7. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    readProducts(connection);
                } else if (choice == 2) {
                    findProduct(connection, scanner);
                } else if (choice == 3) {
                    addProduct(connection, scanner);
                } else if (choice == 4) {
                    updateProduct(connection, scanner);
                } else if (choice == 5) {
                    deleteProduct(connection, scanner);
                } else if (choice == 6) {
                    businessQueries(connection, scanner);
                } else if (choice == 7) {
                    System.out.println();
                    System.out.println("Thank you for using Fashion Retail System!");
                    running = false;
                } else {
                    System.out.println();
                    System.out.println("Invalid choice. Please try again.");
                }
            }

            connection.close();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Something went wrong.");
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

    public static void readProducts(Connection connection) {
        try {
            String sql = "SELECT * FROM PRODUCT"; // Getting all products from the database
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            System.out.println();
            System.out.println("---------- ALL PRODUCTS ----------");

            while (result.next()) {
                printProduct(result);
            }

            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not read products.");
            System.out.println(e.getMessage());
        }
    }

    public static void findProduct(Connection connection, Scanner scanner) {
        try {
            System.out.println();
            System.out.println("---------- FIND PRODUCT ----------");
            System.out.print("Enter Product ID: ");

            long productId = scanner.nextLong(); // getting the Product ID from user

            String sql = "SELECT * FROM PRODUCT " +
                    "WHERE product_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, productId);

            ResultSet result = statement.executeQuery();

            // checking if the product exists or not
            if (result.next()) {
                printProduct(result);
            } else {
                System.out.println();
                System.out.println("Product ID was not found.");
            }

            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not find the product.");
            System.out.println(e.getMessage());
        }
    }

    public static void addProduct(Connection connection, Scanner scanner) {
        try {
            System.out.println();
            System.out.println("---------- ADD PRODUCT ----------");

            System.out.print("Product ID: ");
            long productId = scanner.nextLong();
            scanner.nextLine();

            System.out.print("SKU: ");
            String sku = scanner.nextLine();

            System.out.print("Product Name: ");
            String productName = scanner.nextLine();

            System.out.print("Price: ");
            double price = scanner.nextDouble();

            System.out.print("Discount: ");
            double discount = scanner.nextDouble();

            System.out.print("Number of Sales: ");
            long sales = scanner.nextLong();

            System.out.print("Number of Reviews: ");
            long reviews = scanner.nextLong();
            scanner.nextLine();

            System.out.print("Seller Location: ");
            String location = scanner.nextLine();

            System.out.print("Product Link: ");
            String productLink = scanner.nextLine();

            // adding the new product into the database
            String sql = "INSERT INTO PRODUCT " +
                    "(product_id, product_name, sku, price, discount, " +
                    "no_of_sales, no_of_reviews, seller_location, product_link) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, productId);
            statement.setString(2, productName);
            statement.setString(3, sku);
            statement.setDouble(4, price);
            statement.setDouble(5, discount);
            statement.setLong(6, sales);
            statement.setLong(7, reviews);
            statement.setString(8, location);
            statement.setString(9, productLink);

            statement.executeUpdate();

            System.out.println();
            System.out.println("Product added successfully!");

            statement.close();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not add the product.");
            System.out.println(e.getMessage());
        }
    }

    public static void updateProduct(Connection connection, Scanner scanner) {
        try {
            System.out.println();
            System.out.println("---------- UPDATE PRODUCT ----------");
            System.out.print("Enter Product ID: ");

            long productId = scanner.nextLong();

            String checkSql = "SELECT product_id FROM PRODUCT " +
                    "WHERE product_id = ?";

            PreparedStatement checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setLong(1, productId);

            ResultSet result = checkStatement.executeQuery();

            if (!result.next()) {
                System.out.println();
                System.out.println("Product ID was not found.");
                result.close();
                checkStatement.close();
                return;
            }

            System.out.print("Enter new price: ");
            double newPrice = scanner.nextDouble();

            System.out.print("Enter new discount: ");
            double newDiscount = scanner.nextDouble();

            String sql = "UPDATE PRODUCT " +
                    "SET price = ?, discount = ? " +
                    "WHERE product_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, newPrice);
            statement.setDouble(2, newDiscount);
            statement.setLong(3, productId);

            statement.executeUpdate();

            System.out.println();
            System.out.println("Product updated successfully!");

            result.close();
            checkStatement.close();
            statement.close();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not update the product.");
            System.out.println(e.getMessage());
        }
    }

    public static void deleteProduct(Connection connection, Scanner scanner) {
        try {
            System.out.println();
            System.out.println("---------- DELETE PRODUCT ----------");
            System.out.print("Enter Product ID: ");

            long productId = scanner.nextLong();

            String checkSql = "SELECT product_id FROM PRODUCT " +
                    "WHERE product_id = ?";

            PreparedStatement checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setLong(1, productId);

            ResultSet result = checkStatement.executeQuery();

            if (!result.next()) {
                System.out.println();
                System.out.println("Product ID was not found.");
                result.close();
                checkStatement.close();
                return;
            }

            scanner.nextLine();

            // asking for confirmation before deleting any record/data
            System.out.print("Are you sure you want to delete this product? (yes/no): ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("yes")) {
                String sql = "DELETE FROM PRODUCT " +
                        "WHERE product_id = ?";

                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setLong(1, productId);

                statement.executeUpdate();

                System.out.println();
                System.out.println("Product deleted successfully!");

                statement.close();
            } else {
                System.out.println();
                System.out.println("Product was not deleted.");
            }

            result.close();
            checkStatement.close();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not delete the product.");
            System.out.println(e.getMessage());
        }
    }

    public static String getAllProductsForGUI(Connection connection) { // getting all products for the GUI
        String output = "";

        try {
            String sql = "SELECT * FROM PRODUCT";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                output += "Product ID: " +
                        result.getLong("product_id") + "\n";
                output += "Product Name: " +
                        result.getString("product_name") + "\n";
                output += "SKU: " +
                        result.getString("sku") + "\n";
                output += "Price: " +
                        result.getDouble("price") + "\n";
                output += "Discount: " +
                        result.getDouble("discount") + "\n";
                output += "Sales: " +
                        result.getLong("no_of_sales") + "\n";
                output += "Reviews: " +
                        result.getLong("no_of_reviews") + "\n";
                output += "Location: " +
                        result.getString("seller_location") + "\n";
                output += "------------------------------\n\n";
            }

            result.close();
            statement.close();
        } catch (Exception e) {
            return "Could not read products.";
        }

        return output;
    }

    public static String findProductForGUI(Connection connection, long productId) { // finding a product (GUI)
        try {
            String sql = "SELECT * FROM PRODUCT " +
                    "WHERE product_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, productId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String output = "";

                output += "Product ID: " +
                        result.getLong("product_id") + "\n";
                output += "Product Name: " +
                        result.getString("product_name") + "\n";
                output += "SKU: " +
                        result.getString("sku") + "\n";
                output += "Price: " +
                        result.getDouble("price") + "\n";
                output += "Discount: " +
                        result.getDouble("discount") + "\n";
                output += "Sales: " +
                        result.getLong("no_of_sales") + "\n";
                output += "Reviews: " +
                        result.getLong("no_of_reviews") + "\n";
                output += "Location: " +
                        result.getString("seller_location") + "\n";
                output += "Product Link: " +
                        result.getString("product_link");

                result.close();
                statement.close();

                return output;
            } else {
                result.close();
                statement.close();
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean addProductForGUI(
            Connection connection,
            long productId,
            String sku,
            String productName,
            double price,
            double discount,
            long sales,
            long reviews,
            String location,
            String productLink) {

        try {
            String sql = "INSERT INTO PRODUCT " +
                    "(product_id, product_name, sku, price, discount, " +
                    "no_of_sales, no_of_reviews, seller_location, product_link) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, productId);
            statement.setString(2, productName);
            statement.setString(3, sku);
            statement.setDouble(4, price);
            statement.setDouble(5, discount);
            statement.setLong(6, sales);
            statement.setLong(7, reviews);
            statement.setString(8, location);
            statement.setString(9, productLink);

            statement.executeUpdate();
            statement.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean updateProductForGUI(
            Connection connection,
            long productId,
            double price,
            double discount) {

        try {
            String sql = "UPDATE PRODUCT " +
                    "SET price = ?, discount = ? " +
                    "WHERE product_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, price);
            statement.setDouble(2, discount);
            statement.setLong(3, productId);

            int rows = statement.executeUpdate();
            statement.close();

            return rows > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean deleteProductForGUI(
            Connection connection,
            long productId) {

        try {
            String sql = "DELETE FROM PRODUCT " +
                    "WHERE product_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, productId);

            int rows = statement.executeUpdate();
            statement.close();

            return rows > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static void businessQueries( // showing the business query options to the user
            Connection connection,
            Scanner scanner) {

        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("====================================");
            System.out.println("          BUSINESS QUERIES");
            System.out.println("====================================");
            System.out.println("1. Top Selling Products");
            System.out.println("2. Highest Discount Products");
            System.out.println("3. Average Product Price");
            System.out.println("4. Products by Location");
            System.out.println("5. High Sales and High Discount");
            System.out.println("6. Average Price by Location");
            System.out.println("7. Sales Performance by Location");
            System.out.println("8. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            if (choice == 1) {
                topSellingProducts(connection);
            } else if (choice == 2) {
                highestDiscountProducts(connection);
            } else if (choice == 3) {
                averageProductPrice(connection);
            } else if (choice == 4) {
                productsByLocation(connection);
            } else if (choice == 5) {
                highSalesAndDiscount(connection);
            } else if (choice == 6) {
                averagePriceByLocation(connection);
            } else if (choice == 7) {
                salesPerformanceByLocation(connection);
            } else if (choice == 8) {
                running = false;
            } else {
                System.out.println();
                System.out.println("Invalid choice.");
            }
        }
    }

    public static void topSellingProducts(Connection connection) { // getting all the top 10 selling products
        try {
            String sql = "SELECT product_id, product_name, " +
                    "price, no_of_sales " +
                    "FROM PRODUCT " +
                    "ORDER BY no_of_sales DESC " +
                    "LIMIT 10";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            System.out.println();
            System.out.println("---------- TOP SELLING PRODUCTS ----------");

            while (result.next()) {
                System.out.println(
                        "Product ID: " +
                                result.getLong("product_id")
                );

                System.out.println(
                        "Product Name: " +
                                result.getString("product_name")
                );

                System.out.println(
                        "Price: " +
                                result.getDouble("price")
                );

                System.out.println(
                        "Sales: " +
                                result.getLong("no_of_sales")
                );

                System.out.println(
                        "------------------------------"
                );
            }

            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not get top selling products.");
            System.out.println(e.getMessage());
        }
    }

    public static void highestDiscountProducts(Connection connection) { // Getting products with the highest discounts
        try {
            String sql = "SELECT product_id, product_name, " +
                    "price, discount " +
                    "FROM PRODUCT " +
                    "ORDER BY discount DESC " +
                    "LIMIT 10";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            System.out.println();
            System.out.println("---------- HIGHEST DISCOUNTS ----------");

            while (result.next()) {
                System.out.println(
                        "Product ID: " +
                                result.getLong("product_id")
                );

                System.out.println(
                        "Product Name: " +
                                result.getString("product_name")
                );

                System.out.println(
                        "Price: " +
                                result.getDouble("price")
                );

                System.out.println(
                        "Discount: " +
                                result.getDouble("discount") +
                                "%"
                );

                System.out.println(
                        "------------------------------"
                );
            }

            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not get highest discounts.");
            System.out.println(e.getMessage());
        }
    }

    public static void averageProductPrice(Connection connection) { // Average product price calculation
        try {
            String sql = "SELECT AVG(price) AS average_price " +
                    "FROM PRODUCT";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                double averagePrice =
                        result.getDouble("average_price");

                System.out.println();
                System.out.println("---------- AVERAGE PRODUCT PRICE ----------");

                System.out.println(
                        "Average Price: " +
                                averagePrice
                );
            }

            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not calculate average price.");
            System.out.println(e.getMessage());
        }
    }

    public static void productsByLocation(Connection connection) { //counting products for each location
        try {
            String sql = "SELECT seller_location, " +
                    "COUNT(*) AS number_of_products " +
                    "FROM PRODUCT " +
                    "GROUP BY seller_location";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            System.out.println();
            System.out.println("---------- PRODUCTS BY LOCATION ----------");

            while (result.next()) {
                String location =
                        result.getString("seller_location");

                int numberOfProducts =
                        result.getInt("number_of_products");

                System.out.println(
                        "Location: " + location
                );

                System.out.println(
                        "Number of Products: " +
                                numberOfProducts
                );

                System.out.println(
                        "------------------------------"
                );
            }

            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not get products by location.");
            System.out.println(e.getMessage());
        }
    }

    public static void highSalesAndDiscount(Connection connection) {  // Finding the products with high sales and high discounts
        try {
            String sql = "SELECT product_id, product_name, " +
                    "price, discount, no_of_sales " +
                    "FROM PRODUCT " +
                    "WHERE discount >= 50 " +
                    "AND no_of_sales >= 100 " +
                    "ORDER BY no_of_sales DESC";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            System.out.println();
            System.out.println("---------- HIGH SALES AND HIGH DISCOUNT ----------");

            while (result.next()) {
                System.out.println(
                        "Product ID: " +
                                result.getLong("product_id")
                );

                System.out.println(
                        "Product Name: " +
                                result.getString("product_name")
                );

                System.out.println(
                        "Price: " +
                                result.getDouble("price")
                );

                System.out.println(
                        "Discount: " +
                                result.getDouble("discount") +
                                "%"
                );

                System.out.println(
                        "Sales: " +
                                result.getLong("no_of_sales")
                );

                System.out.println(
                        "------------------------------"
                );
            }

            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not get the products.");
            System.out.println(e.getMessage());
        }
    }

    public static void averagePriceByLocation(Connection connection) { // calculating average price for each location
        try {
            String sql = "SELECT seller_location, " +
                    "AVG(price) AS average_price " +
                    "FROM PRODUCT " +
                    "GROUP BY seller_location";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            System.out.println();
            System.out.println("---------- AVERAGE PRICE BY LOCATION ----------");

            while (result.next()) {
                String location =
                        result.getString("seller_location");

                double averagePrice =
                        result.getDouble("average_price");

                System.out.println(
                        "Location: " + location
                );

                System.out.println(
                        "Average Price: " +
                                averagePrice
                );

                System.out.println(
                        "------------------------------"
                );
            }

            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not calculate average price by location.");
            System.out.println(e.getMessage());
        }
    }

    public static void salesPerformanceByLocation(Connection connection) { // Calculating total sales for each location
        try {
            String sql = "SELECT seller_location, " +
                    "SUM(no_of_sales) AS total_sales " +
                    "FROM PRODUCT " +
                    "GROUP BY seller_location " +
                    "ORDER BY total_sales DESC";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            System.out.println();
            System.out.println("---------- SALES PERFORMANCE BY LOCATION ----------");

            while (result.next()) {
                String location =
                        result.getString("seller_location");

                long totalSales =
                        result.getLong("total_sales");

                System.out.println(
                        "Location: " + location
                );

                System.out.println(
                        "Total Sales: " + totalSales
                );

                System.out.println(
                        "------------------------------"
                );
            }

            result.close();
            statement.close();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Could not calculate sales performance by location.");
            System.out.println(e.getMessage());
        }
    }

    public static void printProduct(ResultSet result) throws Exception {
        long productId = result.getLong("product_id");
        String productName = result.getString("product_name");
        String sku = result.getString("sku");
        double price = result.getDouble("price");
        double discount = result.getDouble("discount");
        long sales = result.getLong("no_of_sales");
        long reviews = result.getLong("no_of_reviews");
        String location = result.getString("seller_location");
        String productLink = result.getString("product_link");

        System.out.println();
        System.out.println("Product ID: " + productId);
        System.out.println("Product Name: " + productName);
        System.out.println("SKU: " + sku);
        System.out.println("Price: " + price);
        System.out.println("Discount: " + discount);
        System.out.println("Sales: " + sales);
        System.out.println("Reviews: " + reviews);
        System.out.println("Location: " + location);
        System.out.println("Product Link: " + productLink);
        System.out.println("------------------------------");
    }

    public static Connection connectToDatabase(String password) {
        try {
            connection = DriverManager.getConnection(
                    url,
                    username,
                    password
            );

            return connection;
        } catch (Exception e) {
            System.out.println("Could not connect to MySQL.");
            return null;
        }
    }
}
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVReader {

    public static void main(String[] args) throws FileNotFoundException {
        String filePath = "/Users/saadiyashaikh/Desktop/Advance Programming/daraz_raw_tshirt_sales_data/daraz_raw_tshirt_sales_data.csv";

        File file = new File(filePath);
        Scanner scanner = new Scanner(file, "UTF-8");

        String header = scanner.nextLine();

        System.out.println("Header:");
        System.out.println(header);

        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Long> productIds = new ArrayList<>();

        int totalRows = 0;
        int invalidRows = 0;
        int duplicateRows = 0;

        while (scanner.hasNextLine()) { // reading each CSV row
            String line = scanner.nextLine();
            totalRows++;

            try {
                ArrayList<String> data = splitCSVLine(line); //splitting the row into columns

                while (data.size() > 9 &&
                        data.get(data.size() - 1).trim().isEmpty()) {
                    data.remove(data.size() - 1);
                }

                if (data.size() != 9) { //Checking if the rows has correct number of columns
                    invalidRows++;
                    continue;
                }

                String productName = data.get(0).trim();
                double price = cleanPrice(data.get(1));
                double discount = cleanDiscount(data.get(2));
                long noOfSales = cleanSales(data.get(3));
                long noOfReviews = cleanReviews(data.get(4));
                String sellerLocation = data.get(5).trim();
                String productLink = data.get(6).trim();
                long productId = Long.parseLong(data.get(7).trim());
                String sku = data.get(8).trim();

                // checking for duplicate Product ID
                if (productIds.contains(productId)) {
                    duplicateRows++;
                    continue;
                }

                productIds.add(productId);

                // creating a Product with the cleaned data
                Product product = new Product(
                        productId,
                        sku,
                        productName,
                        price,
                        discount,
                        noOfSales,
                        noOfReviews,
                        sellerLocation,
                        productLink
                );

                products.add(product);

            } catch (Exception e) {
                invalidRows++;
            }
        }

        scanner.close();

        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println("       DATA CLEANING REPORT");
        System.out.println("--------------------------------------");
        System.out.println("Original rows: " + totalRows);
        System.out.println("Valid unique products: " + products.size());
        System.out.println("Duplicate rows removed: " + duplicateRows);
        System.out.println("Invalid rows: " + invalidRows);

        saveCleanData(products);
    }

    public static ArrayList<String> splitCSVLine(String line) {
        ArrayList<String> data = new ArrayList<>();
        String currentValue = "";
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == '"') {
                insideQuotes = !insideQuotes;
            } else if (character == ',' && !insideQuotes) {
                data.add(currentValue);
                currentValue = "";
            } else {
                currentValue = currentValue + character;
            }
        }

        data.add(currentValue);
        return data;
    }

    public static double cleanPrice(String priceText) {
        priceText = priceText.trim();
        priceText = priceText.replaceAll("[^0-9.]", ""); // removing non-numeric characters

        if (priceText.isEmpty()) {
            return 0;
        }

        return Double.parseDouble(priceText);
    }

    public static double cleanDiscount(String discountText) {
        discountText = discountText.trim();

        // converting missing discount to 0
        if (discountText.equalsIgnoreCase("N/A")) {
            return 0;
        }

        discountText = discountText.replace("% Off", "").trim();

        if (discountText.isEmpty()) {
            return 0;
        }

        return Double.parseDouble(discountText);
    }

    public static long cleanSales(String salesText) {
        salesText = salesText.trim();

        if (salesText.equalsIgnoreCase("N/A")) {
            return 0;
        }

        salesText = salesText.replace("sold", "").trim();

        // Converting K notation into actual sales number
        if (salesText.endsWith("K")) {
            salesText = salesText.replace("K", "");

            double number = Double.parseDouble(salesText);
            return (long) (number * 1000);
        }

        return Long.parseLong(salesText);
    }

    public static long cleanReviews(String reviewText) {
        reviewText = reviewText.trim();

        if (reviewText.equalsIgnoreCase("N/A")) {
            return 0;
        }

        long reviews = Long.parseLong(reviewText);

        if (reviews < 0) { // Negative reviews are treated as 0
            return 0;
        }

        return reviews;
    }

    public static void saveCleanData(ArrayList<Product> products) {
        String outputFile =
                "/Users/saadiyashaikh/Desktop/Advance Programming/daraz_cleaned_products.csv";

        try {
            FileWriter writer = new FileWriter(outputFile);

            writer.write(
                    "Product ID,SKU,Product Name,Price,Discount,No. of Sales,No. of Reviews,Seller Location,Product Link\n"
            );

            for (Product product : products) { // writing each cleaned product to the file
                writer.write(
                        product.getProductId() + "," +
                                product.getSku() + "," +
                                "\"" +
                                product.getProductName().replace("\"", "\"\"") +
                                "\"," +
                                product.getPrice() + "," +
                                product.getDiscount() + "," +
                                product.getNoOfSales() + "," +
                                product.getNoOfReviews() + "," +
                                product.getSellerLocation() + "," +
                                product.getProductLink() +
                                "\n"
                );
            }

            writer.close();

            System.out.println();
            System.out.println("Clean data saved successfully!");
            System.out.println("File: " + outputFile);

        } catch (IOException e) {
            System.out.println("Could not save the cleaned file.");
        }
    }
}
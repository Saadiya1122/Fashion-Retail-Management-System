import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class FashionRetailGUI {
    static Connection connection;

    public static void main(String[] args) {
        String password = JOptionPane.showInputDialog(
                null,
                "Enter MySQL password:"
        );

        if (password == null) {
            return;
        }
        connection = DataBaseManager.connectToDatabase(password); // connecting to the database

        if (connection == null) {
            JOptionPane.showMessageDialog(
                    null,
                    "Could not connect to MySQL."
            );
            return;
        }
        createWindow();
    }

    public static void createWindow() { // creating the main window
        JFrame frame = new JFrame("Fashion Retail System");
        frame.setSize(450, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));

        JLabel title = new JLabel(
                "FASHION RETAIL SYSTEM",
                SwingConstants.CENTER
        );
        title.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        panel.add(title);

    // creating buttons for the main menu
        JButton viewAllButton =
                new JButton("View All Products");

        JButton viewButton =
                new JButton("View Specific Product");

        JButton addButton =
                new JButton("Add Product");

        JButton updateButton =
                new JButton("Update Product");

        JButton deleteButton =
                new JButton("Delete Product");

        JButton businessButton =
                new JButton("Business Queries");

        panel.add(viewAllButton);
        panel.add(viewButton);
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(businessButton);

        // connecting buttons to their functions
        viewAllButton.addActionListener(
                e -> viewAllProducts()
        );

        viewButton.addActionListener(
                e -> viewProduct()
        );

        addButton.addActionListener(
                e -> addProduct()
        );

        updateButton.addActionListener(
                e -> updateProduct()
        );

        deleteButton.addActionListener(
                e -> deleteProduct()
        );

        businessButton.addActionListener(
                e -> businessQueries()
        );

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void viewAllProducts() { // getting the Product ID from the user
        String output =
                DataBaseManager.getAllProductsForGUI(connection);

        showResults(
                output,
                "All Products"
        );
    }

    public static void viewProduct() {
        String input =
                JOptionPane.showInputDialog(
                        "Enter Product ID:"
                );

        if (input == null) { // Checking if the Product ID exists or not in the database
            return;
        }

        try {
            long productId =
                    Long.parseLong(input);

            String output =
                    DataBaseManager.findProductForGUI(
                            connection,
                            productId
                    );

            if (output == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Product ID was not found."
                );
            } else {
                showResults(
                        output,
                        "Product Information"
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Please enter a valid Product ID."
            );
        }
    }

    public static void addProduct() {
        try {
            String idText =
                    JOptionPane.showInputDialog(
                            "Product ID:"
                    );

            if (idText == null) {
                return;
            }

            long productId =
                    Long.parseLong(idText);

            String sku =
                    JOptionPane.showInputDialog(
                            "SKU:"
                    );

            if (sku == null) {
                return;
            }

            String productName =
                    JOptionPane.showInputDialog(
                            "Product Name:"
                    );

            if (productName == null) {
                return;
            }

            String priceText =
                    JOptionPane.showInputDialog(
                            "Price:"
                    );

            if (priceText == null) {
                return;
            }

            double price =
                    Double.parseDouble(priceText);

            String discountText =
                    JOptionPane.showInputDialog(
                            "Discount:"
                    );

            if (discountText == null) {
                return;
            }

            double discount =
                    Double.parseDouble(discountText);

            String salesText =
                    JOptionPane.showInputDialog(
                            "Number of Sales:"
                    );

            if (salesText == null) {
                return;
            }

            long sales =
                    Long.parseLong(salesText);

            String reviewsText =
                    JOptionPane.showInputDialog(
                            "Number of Reviews:"
                    );

            if (reviewsText == null) {
                return;
            }

            long reviews =
                    Long.parseLong(reviewsText);

            String location =
                    JOptionPane.showInputDialog(
                            "Seller Location:"
                    );

            if (location == null) {
                return;
            }

            String productLink =
                    JOptionPane.showInputDialog(
                            "Product Link:"
                    );

            if (productLink == null) {
                return;
            }

            boolean added =
                    DataBaseManager.addProductForGUI(
                            connection,
                            productId,
                            sku,
                            productName,
                            price,
                            discount,
                            sales,
                            reviews,
                            location,
                            productLink
                    );

            if (added) {
                JOptionPane.showMessageDialog(
                        null,
                        "Product added successfully!"
                );
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Could not add the product."
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Please enter valid information."
            );
        }
    }

    public static void updateProduct() {
        String input =
                JOptionPane.showInputDialog(
                        "Enter Product ID:"
                );

        if (input == null) {
            return;
        }

        try {
            long productId =
                    Long.parseLong(input);
            // checking if the product exists before updating
            String product =
                    DataBaseManager.findProductForGUI(
                            connection,
                            productId
                    );

            if (product == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Product ID was not found."
                );
                return;
            }

            String priceText =
                    JOptionPane.showInputDialog(
                            "Enter new price:"
                    );

            if (priceText == null) {
                return;
            }

            double price =
                    Double.parseDouble(priceText);

            String discountText =
                    JOptionPane.showInputDialog(
                            "Enter new discount:"
                    );

            if (discountText == null) {
                return;
            }

            double discount =
                    Double.parseDouble(discountText);

            // updating the product information
            boolean updated =
                    DataBaseManager.updateProductForGUI(
                            connection,
                            productId,
                            price,
                            discount
                    );

            if (updated) {
                JOptionPane.showMessageDialog(
                        null,
                        "Product updated successfully!"
                );
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Could not update the product."
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Please enter valid information."
            );
        }
    }

    public static void deleteProduct() {
        String input =
                JOptionPane.showInputDialog(
                        "Enter Product ID:"
                );

        if (input == null) {
            return;
        }

        try {
            long productId =
                    Long.parseLong(input);

            String product =
                    DataBaseManager.findProductForGUI(
                            connection,
                            productId
                    );

            if (product == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Product ID was not found."
                );
                return;
            }

            int answer =
                    JOptionPane.showConfirmDialog( // asking the user for confirmation before deleting
                            null,
                            "Are you sure you want to delete this product?",
                            "Delete Product",
                            JOptionPane.YES_NO_OPTION
                    );

            if (answer == JOptionPane.YES_OPTION) {
                boolean deleted =
                        DataBaseManager.deleteProductForGUI(
                                connection,
                                productId
                        );

                if (deleted) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Product deleted successfully!"
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Could not delete the product."
                    );
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Please enter a valid Product ID."
            );
        }
    }
    // Creating the business query menu
    public static void businessQueries() {
        JFrame frame =
                new JFrame("Business Queries");

        frame.setSize(550, 430);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel();

        mainPanel.setLayout(
                new BorderLayout(10, 10)
        );

        JLabel title =
                new JLabel(
                        "BUSINESS QUERIES",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.setLayout(
                new GridLayout(4, 2, 10, 10)
        );

        JButton topSellingButton =
                new JButton("Top Selling Products");

        JButton highestDiscountButton =
                new JButton("Highest Discount Products");

        JButton averagePriceButton =
                new JButton("Average Product Price");

        JButton locationButton =
                new JButton("Products by Location");

        JButton highSalesButton =
                new JButton("High Sales and High Discount");

        JButton averageLocationButton =
                new JButton("Average Price by Location");

        JButton salesLocationButton =
                new JButton("Sales Performance by Location");

        JButton backButton =
                new JButton("Back");

        buttonPanel.add(topSellingButton);
        buttonPanel.add(highestDiscountButton);
        buttonPanel.add(averagePriceButton);
        buttonPanel.add(locationButton);
        buttonPanel.add(highSalesButton);
        buttonPanel.add(averageLocationButton);
        buttonPanel.add(salesLocationButton);
        buttonPanel.add(backButton);

        topSellingButton.addActionListener(
                e -> showBusinessQuery(
                        "topSellingProducts",
                        "Top Selling Products"
                )
        );

        highestDiscountButton.addActionListener(
                e -> showBusinessQuery(
                        "highestDiscountProducts",
                        "Highest Discount Products"
                )
        );

        averagePriceButton.addActionListener(
                e -> showBusinessQuery(
                        "averageProductPrice",
                        "Average Product Price"
                )
        );

        locationButton.addActionListener(
                e -> showBusinessQuery(
                        "productsByLocation",
                        "Products by Location"
                )
        );

        highSalesButton.addActionListener(
                e -> showBusinessQuery(
                        "highSalesAndDiscount",
                        "High Sales and High Discount"
                )
        );

        averageLocationButton.addActionListener(
                e -> showBusinessQuery(
                        "averagePriceByLocation",
                        "Average Price by Location"
                )
        );

        salesLocationButton.addActionListener(
                e -> showBusinessQuery(
                        "salesPerformanceByLocation",
                        "Sales Performance by Location"
                )
        );

        backButton.addActionListener(
                e -> frame.dispose()
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public static void showBusinessQuery(
            String query,
            String title) {
        // Sending the selected query to DataBaseManager
        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        PrintStream oldOutput =
                System.out;

        System.setOut(
                new PrintStream(output)
        );

        if (query.equals("topSellingProducts")) {
            DataBaseManager.topSellingProducts(
                    connection
            );
        } else if (
                query.equals("highestDiscountProducts")) {

            DataBaseManager.highestDiscountProducts(
                    connection
            );
        } else if (
                query.equals("averageProductPrice")) {

            DataBaseManager.averageProductPrice(
                    connection
            );
        } else if (
                query.equals("productsByLocation")) {

            DataBaseManager.productsByLocation(
                    connection
            );
        } else if (
                query.equals("highSalesAndDiscount")) {

            DataBaseManager.highSalesAndDiscount(
                    connection
            );
        } else if (
                query.equals("averagePriceByLocation")) {

            DataBaseManager.averagePriceByLocation(
                    connection
            );
        } else if (
                query.equals("salesPerformanceByLocation")) {

            DataBaseManager.salesPerformanceByLocation(
                    connection
            );
        }

        System.out.flush();
        System.setOut(oldOutput);

        showResults(
                output.toString(),
                title
        );
    }

    public static void showResults( // showing the result in a separate window
            String text,
            String title) {

        JTextArea textArea =
                new JTextArea(text);

        textArea.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(textArea);

        scrollPane.setPreferredSize(
                new Dimension(600, 500)
        );

        JOptionPane.showMessageDialog(
                null,
                scrollPane,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
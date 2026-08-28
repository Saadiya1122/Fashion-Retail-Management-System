// Storing the product information
public class Product {
    private long productId;
    private String sku;
    private String productName;
    private double price;
    private double discount;
    private long noOfSales;
    private long noOfReviews;
    private String sellerLocation;
    private String productLink;

    public Product(long productId, String sku, String productName,
                   double price, double discount, long noOfSales,
                   long noOfReviews, String sellerLocation,
                   String productLink) {
        // setting the values for the product
        this.productId = productId;
        this.sku = sku;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.noOfSales = noOfSales;
        this.noOfReviews = noOfReviews;
        this.sellerLocation = sellerLocation;
        this.productLink = productLink;
    }

    public long getProductId() {
        return productId;
    }

    public String getSku() {
        return sku;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public long getNoOfSales() {
        return noOfSales;
    }

    public long getNoOfReviews() {
        return noOfReviews;
    }

    public String getSellerLocation() {
        return sellerLocation;
    }

    public String getProductLink() {
        return productLink;
    }
}
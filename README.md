# Fashion Retail Management System

A Java-based Fashion Retail Management System developed for the Advanced Programming module.

The project uses a T-shirt sales dataset and includes data cleaning, MySQL database management, CRUD operations, business queries and a Java Swing graphical user interface.

## Technologies

- Java
- Java Swing
- MySQL
- JDBC
- Git & GitHub

## Project Structure

```text
Fashion-Retail-Management-System/
├── data/
│   ├── daraz_raw_tshirt_sales_data.csv
│   └── daraz_cleaned_products.csv
├── sql/
│   └── fashion_retail.sql
└── src/
    ├── CSVReader.java
    ├── Product.java
    ├── DataBaseManager.java
    └── FashionRetailGUI.java
```

## Database Setup

The `sql/fashion_retail.sql` file contains the database and table setup and the commands for importing the cleaned data.

> **Note:** Before running the SQL file, check the `LOAD DATA LOCAL INFILE` path and change it to the location of `daraz_cleaned_products.csv` on your computer.

## Running the Project

1. Open the project in IntelliJ IDEA.
2. Make sure MySQL is running.
3. Run the SQL file to set up the database.
4. Check the database connection details in `DataBaseManager.java`.
5. Run `FashionRetailGUI.java`.

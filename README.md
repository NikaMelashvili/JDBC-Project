# Java-JDBC-Project
# Purpose:
The application allows users to manage products by adding them to a database, displaying them in a table, and visualizing the quantity of each product using a pie chart.

# Class Overview:
HelloApplication class extends javafx.application.Application and serves as the main class for the application.
It includes UI elements such as labels, text fields, a table view (productTable), and a pie chart (pieChart).
Products are represented by the Product class, which contains properties for ID, name, price, and quantity.
# Database Interaction:
getConnection() method establishes a connection to the MySQL database using JDBC.
insertProduct() method adds a new product to the database by executing an SQL INSERT query.
getAllProducts() retrieves all products from the database using an SQL SELECT query and returns them as an observable list.
refreshData() clears and updates the product list and pie chart by calling getAllProducts() and groupProductsByQuantity() methods.
# Data Visualization:
groupProductsByQuantity() method retrieves products, groups them by name, sums their quantities, and populates the pie chart with this information.
# UI Setup (start() method):
Sets up labels, text fields, a table view, a pie chart, and an "Add" button in a vertical layout (VBox).
Defines columns for the product table to display ID, name, price, and quantity.
Handles the "Add" button action to insert new products and update the UI.
Initializes the scene, sets the stage, and displays the UI.
# Main Method:
main() method that launches the JavaFX application.

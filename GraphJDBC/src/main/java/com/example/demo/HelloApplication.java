package com.example.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloApplication extends Application {
    private TableView<Product> productTable = new TableView<>();
    private PieChart pieChart = new PieChart();
    private ObservableList<Product> products = FXCollections.observableArrayList();

    private Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/ug-final";
        String username = "root";
        String password = "";
        return DriverManager.getConnection(url, username, password);
    }

    private void insertProduct(int id, int quantity, String name, int price) {
        try (Connection connection = getConnection()) {
            String insertQuery = "INSERT INTO `finaluris-msgavsi` (id, quantity, name, price) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setString(3, name);
            preparedStatement.setInt(4, price);
            preparedStatement.executeUpdate();

            refreshData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private ObservableList<Product> getAllProducts() {
        ObservableList<Product> productList = FXCollections.observableArrayList();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM `finaluris-msgavsi`");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int quantity = resultSet.getInt("quantity");

                Product product = new Product(id, name, price, quantity);
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }
    private void refreshData() {
        products.clear();
        products.addAll(getAllProducts());

        productTable.setItems(products);
        pieChart.getData().clear();
        groupProductsByQuantity();
    }
    private void groupProductsByQuantity() {
        List<Product> products = getAllProducts();
        Map<String, Integer> productQuantityMap = new HashMap<>();

        products.forEach(product ->
                productQuantityMap.merge(product.getName(), product.getQuantity(), Integer::sum));

        productQuantityMap.forEach((productName, quantity) ->
                pieChart.getData().add(new PieChart.Data(productName + " - " + quantity + " pieces", quantity)));
    }

    @Override
    public void start(Stage primaryStage) {
        Label quantityLabel = new Label();
        Label nameLabel = new Label();
        Label priceLabel = new Label();
        quantityLabel.setText("Enter Quantity");
        nameLabel.setText("Enter Name");
        priceLabel.setText("Enter Price");
        TextField quantityField = new TextField();
        TextField nameField = new TextField();
        TextField priceField = new TextField();
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            int id = products.size() + 1;
            int quantity = Integer.parseInt(quantityField.getText());
            String name = nameField.getText();
            int price = Integer.parseInt(priceField.getText());

            insertProduct(id, quantity, name, price);

            refreshData();
            quantityField.clear();
            nameField.clear();
            priceField.clear();
        });

        TableColumn<Product, Number> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Product, Number> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());

        TableColumn<Product, Number> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());

        productTable.getColumns().addAll(idColumn, nameColumn, priceColumn, quantityColumn);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                nameLabel, nameField, priceLabel, priceField, quantityLabel, quantityField, addButton, productTable, pieChart
        );

        Scene scene = new Scene(layout, 900, 900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Product Management");
        primaryStage.show();

        refreshData();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

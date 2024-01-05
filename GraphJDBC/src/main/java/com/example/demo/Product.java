package com.example.demo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Product {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty price;
    private final SimpleIntegerProperty quantity;

    public Product(int id, String name, int price, int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleIntegerProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public String getName() {
        return name.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public ObservableValue<String> nameProperty() {
        return name;
    }

    public ObservableValue<Number> priceProperty() {
        return price;
    }

    public ObservableValue<Number> quantityProperty() {
        return quantity;
    }

    public ObservableValue<Number> idProperty() {
        return  id;
    }
}

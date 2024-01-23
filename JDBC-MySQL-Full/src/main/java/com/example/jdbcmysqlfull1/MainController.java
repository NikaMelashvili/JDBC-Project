package com.example.jdbcmysqlfull1;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MainController {
    @FXML
    private TextField createTableField;
    @FXML
    private TextField columnsAmount;
    @FXML
    private AnchorPane layout;
    ObservableList<String> selectedValues = FXCollections.observableArrayList();
    ObservableList<StringProperty> rows = FXCollections.observableArrayList();
    Database db = new Database("jdbc:mysql://localhost:3307/mydb", "root", "thegoatlevi123");
    double initialY = 50.0;

    public void nextTableBtnAction(ActionEvent event) {
        String newTableNameForQuery = createTableField.getText();
        int columnAmount = Integer.parseInt(columnsAmount.getText());
        String[] cols = new String[columnAmount];

        layout.getChildren().clear();
        AtomicReference<Double> n = new AtomicReference<>(0.0);
        double totalHeight = 0.0;

        for (int i = 0; i < columnAmount; i++) {
            Label colNum = new Label("Enter column N" + (i + 1) + " name");
            TextField columnNum = new TextField();
            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.getStyleClass().add("custom-combo-box");
            comboBox.getItems().addAll(
                    "INT",
                    "VARCHAR",
                    "DATE",
                    "DECIMAL"
            );

            AnchorPane.setLeftAnchor(colNum, 10.0);
            AnchorPane.setTopAnchor(colNum, initialY + n.get());
            AnchorPane.setLeftAnchor(columnNum, 10.0);
            AnchorPane.setTopAnchor(columnNum, initialY + 30.0 + n.get());
            AnchorPane.setTopAnchor(comboBox, initialY + 30.0 + n.get());
            AnchorPane.setLeftAnchor(comboBox, 200.0);

            n.updateAndGet(v -> v + 60.0);
            comboBox.setLayoutX(colNum.getLayoutX() + colNum.prefWidth(0) + 10);
            totalHeight = initialY + n.get();

            layout.getChildren().addAll(colNum, columnNum, comboBox);

            int finalI = i;
            columnNum.textProperty().addListener((observable, oldValue, newValue) -> {
                cols[finalI] = newValue;
            });

            comboBox.setOnAction(e -> {
                String selectedItem = comboBox.getValue();
                if (selectedItem != null) {
                    selectedValues.add(selectedItem);
                }
            });
        }

        Button createTableBtn = new Button("Create");
        AnchorPane.setTopAnchor(createTableBtn, totalHeight + 20.0);
        layout.getChildren().add(createTableBtn);

        n.set(0.0);

        createTableBtn.setOnAction(e -> {
            try {
                db.createTable(newTableNameForQuery, cols, selectedValues);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            layout.getChildren().clear();

            // insert rows section
            for (int i = 0; i < columnAmount; i++) {
                Label rowNum = new Label("Enter row element N" + (i + 1) + " value");
                TextField rowNumText = new TextField();

                AnchorPane.setLeftAnchor(rowNum, 10.0);
                AnchorPane.setTopAnchor(rowNum, initialY + n.get());
                AnchorPane.setLeftAnchor(rowNumText, 10.0);
                AnchorPane.setTopAnchor(rowNumText, initialY + 30.0 + n.get());

                n.updateAndGet(v -> v + 60.0);
                layout.getChildren().addAll(rowNum, rowNumText);
                rows.add(rowNumText.textProperty());
                System.out.println(rows.get(i));
            }

            Button addRowBtn = new Button("Add row");
            layout.getChildren().add(addRowBtn);

            addRowBtn.setOnAction(event1 -> {
                try {
                    db.addRow(rows);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
        });
    }
}

package com.example.jdbcmysqlfull;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class HelloApplication extends Application {
    ObservableList<String> selectedValues = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        VBox layout = new VBox(10);
        Label newTableName = new Label();
        Label columnsTable = new Label();
        newTableName.setText("Enter the name of the table you want to create");
        columnsTable.setText("Enter the amount of columns in the table");
        TextField newTableText = new TextField();
        TextField columns = new TextField();
        Button nextTableBtn = new Button("Next");

        nextTableBtn.setOnAction(e -> {
            String newTableNameForQuery = newTableText.getText();
            int columnAmount = Integer.parseInt(columns.getText());
            String cols[] = new String[columnAmount];
            layout.getChildren().clear();

            for (int i = 0; i < columnAmount; i++) {
                Label colNum = new Label("Enter column N" + i + 1 + " name");
                TextField columnNum = new TextField();
                ComboBox<String> comboBox = new ComboBox<>();
                comboBox.getItems().addAll(
                        "INT",
                        "VARCHAR",
                        "DATE",
                        "DECIMAL"
                );
                layout.getChildren().addAll(
                        colNum, columnNum, comboBox
                );
                int finalI = i;
                columnNum.textProperty().addListener((observable, oldValue, newValue) -> {
                    cols[finalI] = newValue;
                });
                comboBox.setOnAction(event -> {
                    String selectedItem = comboBox.getValue();
                    if (selectedItem != null) {
                        selectedValues.add(selectedItem);
                    }
                });
            }
            Button createTableBtn = new Button("Create");
            layout.getChildren().add(createTableBtn);

            Database db = new Database();
            createTableBtn.setOnAction(event -> {
                try {
                    db.createTable(newTableNameForQuery, cols, selectedValues);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
        layout.getChildren().addAll(
                newTableName, newTableText, columnsTable, columns, nextTableBtn
        );
        Scene scene = new Scene(layout, 900, 900);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

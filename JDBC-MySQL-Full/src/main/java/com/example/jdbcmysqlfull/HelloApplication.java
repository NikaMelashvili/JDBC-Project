package com.example.jdbcmysqlfull;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Label newTableName = new Label();
        Label columnsTable = new Label();
        newTableName.setText("Enter the name of the table you want to create");
        columnsTable.setText("Enter the amount of columns in the table");
        TextField newTableText = new TextField();
        newTableText.getStyleClass().add("custom-text-field");
        TextField columns = new TextField();
        Button createTableBtn = new Button("Create");
        createTableBtn.setOnAction(e -> {
            String newTableNameForQuery = newTableText.getText();
        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                newTableName, newTableText, columnsTable, columns, createTableBtn
        );
        Scene scene = new Scene(layout, 900, 900);
//        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

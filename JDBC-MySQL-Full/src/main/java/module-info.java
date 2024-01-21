module com.example.jdbcmysqlfull {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.jdbcmysqlfull to javafx.fxml;
    exports com.example.jdbcmysqlfull;
}
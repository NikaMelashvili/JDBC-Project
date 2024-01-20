module com.example.jdbcmysqlfull {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.jdbcmysqlfull to javafx.fxml;
    exports com.example.jdbcmysqlfull;
}
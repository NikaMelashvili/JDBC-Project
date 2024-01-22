module com.example.JDBCMySQLFull {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;

    opens com.example.jdbcmysqlfull to javafx.fxml;
    exports com.example.jdbcmysqlfull;
}

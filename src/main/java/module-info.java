module com.bank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.bank to javafx.fxml;
    opens com.bank.controller to javafx.fxml;
    opens com.bank.model to javafx.fxml;

    exports com.bank;
    exports com.bank.controller;
    exports com.bank.model;
    exports com.bank.database;
}

module gr2116.ui {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;

    requires gr2116.core;
    requires gr2116.persistence;
    
    opens gr2116.ui.app to javafx.graphics;
    opens gr2116.ui.controller to javafx.fxml;
    opens gr2116.ui.main to javafx.fxml;
    opens gr2116.ui.login to javafx.fxml;
    opens gr2116.ui.money to javafx.fxml;
}

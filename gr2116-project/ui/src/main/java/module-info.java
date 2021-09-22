module gr2116.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires gr2116.core;
    opens gr2116.ui.app to javafx.graphics;
    opens gr2116.ui.controller to javafx.fxml;
    opens gr2116.ui.components to javafx.fxml;
    opens gr2116.ui.pages to javafx.fxml;
}

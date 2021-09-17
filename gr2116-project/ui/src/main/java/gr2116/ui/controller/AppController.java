package gr2116.ui.controller;
import javafx.fxml.FXML;
import gr2116.core.Hotel;

public class AppController {

    @FXML
    void initialize() {
        Hotel hotel = new Hotel();
        System.out.println("HOTEL CLASS: " + hotel);
    }
}

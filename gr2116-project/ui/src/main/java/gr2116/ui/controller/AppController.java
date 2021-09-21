package gr2116.ui.controller;

import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;
import gr2116.ui.components.RoomItem;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class AppController {

    @FXML VBox roomItemContainer;

    @FXML
    void initialize() {
        Hotel hotel = new Hotel();
        hotel.addRoom(new HotelRoom(HotelRoomType.Single, 101));
        hotel.addRoom(new HotelRoom(HotelRoomType.Double, 102));
        hotel.addRoom(new HotelRoom(HotelRoomType.Single, 103));
        hotel.addRoom(new HotelRoom(HotelRoomType.Suite, 201));
        
        for (HotelRoom room : hotel) {
            RoomItem roomItem = new RoomItem(room);
            roomItemContainer.getChildren().add(roomItem);
        }
    }
}

package gr2116.ui.components;

import gr2116.core.HotelRoom;
import gr2116.ui.utils.FXMLUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class HotelRoomListItem extends HBox {
    private HotelRoom room;

    @FXML
    private Label numberLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Button makeReservationButton;

    public HotelRoomListItem(HotelRoom room) {
        this.room = room;
        FXMLUtils.loadFXML(this);
    }

    @FXML
    private void initialize() {
        numberLabel.setText("HotelRoom " + room.getNumber());
        typeLabel.setText(room.getRoomType().getDescription());
        makeReservationButton.setText("Make reservation.");
    }

	public void setOnMakeReservationButtonAction(EventHandler<ActionEvent> eventHandler) {
        makeReservationButton.setOnAction(eventHandler);
	}
}

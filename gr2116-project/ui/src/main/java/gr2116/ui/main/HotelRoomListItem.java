package gr2116.ui.main;

import gr2116.core.HotelRoom;
import gr2116.ui.utils.FxmlUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * A gui element for displaying hotel rooms in a list.
 */
public class HotelRoomListItem extends HBox {
  private HotelRoom room;

  @FXML
  private Label numberLabel;

  @FXML
  private Label typeLabel;

  @FXML
  private Label amenitiesLabel;

  @FXML
  private Label pricePerNightLabel;

  @FXML
  private Label totalPriceLabel;

  @FXML
  private Label errorLabel;

  @FXML
  private Button makeReservationButton;

  /**
   * Constructs a HotelRoomListItem for the given HotelRoom.
   *
   * @param room hotelRoom to be constructed from.
   */
  public HotelRoomListItem(final HotelRoom room) {
    if (room == null) {
      throw new NullPointerException("Error initializing HotelRoomListItem: room is null.");
    }
    this.room = room;
    FxmlUtils.loadFxml(this);
  }

  @FXML
  private void initialize() {
    numberLabel.setText("HotelRoom " + room.getNumber());
    typeLabel.setText(room.getRoomType().getDescription());
    makeReservationButton.setText("Make reservation.");
    makeReservationButton.setDisable(true); 
    pricePerNightLabel.setText("Price per night: " + Double.toString(room.getPrice()));
    totalPriceLabel.setText("");
    errorLabel.setText("");
    errorLabel.setTextFill(Color.RED);
    errorLabel.setMinHeight(Region.USE_PREF_SIZE);

    int roomNumber = room.getNumber();
    this.setId("hotelRoom" + Integer.toString(roomNumber) + "listItem");
    numberLabel.setId("hotelRoom" + Integer.toString(roomNumber) + "NumberLabel");
    typeLabel.setId("hotelRoom" + Integer.toString(roomNumber) + "TypeLabel");
    pricePerNightLabel.setId("hotelRoom" + Integer.toString(roomNumber) + "PricePerNightLabel");
    totalPriceLabel.setId("hotelRoom" + Integer.toString(roomNumber) + "TotalPriceLabel");
    errorLabel.setId("hotelRoom" + Integer.toString(roomNumber) + "ErrorLabel");
    makeReservationButton.setId("hotelRoom" + Integer.toString(roomNumber) + "Button");

    String amenitiesText = "";
    for (String amenity : room.getAmenities()) {
      amenitiesText += amenity + ", ";
    }
    amenitiesLabel.setText(amenitiesText);
    amenitiesLabel.setMinHeight(Region.USE_PREF_SIZE);
  }

  public void setTotalPriceLabel(String price) {
    totalPriceLabel.setText(price);
  }

  public void setErrorLabel(String error) {
    errorLabel.setText(error);
  }

  /**
   * Sets the action of the makeReservationButton.
   *
   * @param eventHandler the eventHandler that supplies the action.
   */
  public final void setOnMakeReservationButtonAction(
      final EventHandler<ActionEvent> eventHandler) {
    makeReservationButton.setOnAction(eventHandler);
    makeReservationButton.setDisable(eventHandler == null);
  }
}

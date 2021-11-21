package gr2116.ui.main;

import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomType;
import gr2116.ui.utils.FxmlUtils;
import java.util.Collection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * A gui element for displaying hotel rooms in a list.
 * Has information about the room and button to make a reservation.
 */
public class HotelRoomListItem extends HBox {
  private final int roomNumber;
  private final double price;
  private final HotelRoomType roomType;
  private final Collection<String> amenities;

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
  private Label totalPriceTextLabel;

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
      throw new IllegalArgumentException("Error initializing HotelRoomListItem: room is null.");
    }
    roomNumber = room.getNumber();
    price = room.getPrice();
    amenities = room.getAmenities();
    roomType = room.getRoomType();

    FxmlUtils.loadFxml(this);
  }

  /**
   * Initializes the component. Add the room to HotelRoomList.
   */
  @FXML
  private void initialize() {
    numberLabel.setText("Hotel room " + roomNumber);
    typeLabel.setText(roomType.getDescription());
    // MakeReservationButton can be pressed.
    makeReservationButton.setText("Make reservation.");
    makeReservationButton.setDisable(true); 
    pricePerNightLabel.setText(Double.toString(price));
    totalPriceLabel.setText("");
    // No error messeage.
    errorLabel.setText("");
    errorLabel.setMinHeight(Region.USE_PREF_SIZE);

    // Sets id used in the tests.
    this.setId("hotelRoom" + Integer.toString(roomNumber) + "ListItem");
    numberLabel.setId("hotelRoom" + Integer.toString(roomNumber) + "NumberLabel");
    typeLabel.setId("hotelRoom" + Integer.toString(roomNumber) + "TypeLabel");
    pricePerNightLabel.setId("hotelRoom" + Integer.toString(roomNumber) + "PricePerNightLabel");
    totalPriceLabel.setId("hotelRoom" + Integer.toString(roomNumber) + "TotalPriceLabel");
    errorLabel.setId("hotelRoom" + Integer.toString(roomNumber) + "ErrorLabel");
    makeReservationButton.setId("hotelRoom" + Integer.toString(roomNumber) + "Button");

    // Adds the rooms amenities.
    StringBuilder amenitiesText = new StringBuilder();
    for (String amenity : amenities) {
      amenitiesText.append(amenity + ", ");
    }
    amenitiesLabel.setText(amenitiesText.toString());
    amenitiesLabel.setMinHeight(Region.USE_PREF_SIZE);
  }

  /**
   * Sets the totalPriceLabel to given price.
   *
   * @param price given total price
   */
  public void setTotalPriceLabel(String price) {
    totalPriceTextLabel.setText("Total Price: ");
    totalPriceLabel.setText(price);
  }

  /**
   * Sets errorLabel to given error.
   *
   * @param error given error
   */
  public void setErrorLabel(String error) {
    errorLabel.setText(error);
  }

  /**
   * Sets the action of the makeReservationButton.
   *
   * @param eventHandler the eventHandler that supplies the action
   */
  public final void setOnMakeReservationButtonAction(
      final EventHandler<ActionEvent> eventHandler) {
    makeReservationButton.setOnAction(eventHandler);
    makeReservationButton.setDisable(eventHandler == null);
  }
}

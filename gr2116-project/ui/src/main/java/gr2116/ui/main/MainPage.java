package gr2116.ui.main;

import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomFilter;
import gr2116.core.Person;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import gr2116.ui.utils.FxmlUtils;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Main page, which contains the main panel for booking hotel nights.
 */
public class MainPage extends VBox implements MessageListener {
  private final FilterPanel filterPanel = new FilterPanel();
  private UserPanel userPanel;
  private final Hotel hotel;
  private HotelRoomFilter hotelRoomFilter
      = new HotelRoomFilter(null, null, null, null, null);
  private final Person person;
  private final Collection<MessageListener> listeners = new HashSet<>();

  /**
   * Constructs a main page for a given person and hotel.
   *
   * @param person the person the main page should be constructed for.
   * @param hotel the hotel the main page should be constructed for.
   */
  public MainPage(Person person, Hotel hotel) {
    if (person == null) {
      throw new NullPointerException("Person is null.");
    }
    if (hotel == null) {
      throw new NullPointerException("Hotel is null.");
    }
    this.hotel = hotel;
    this.person = person;
    FxmlUtils.loadFxml(this);
  }

  @FXML
  private VBox roomItemContainer;

  @FXML
  private AnchorPane filterPane;

  @FXML
  private AnchorPane userPane;

  /**
   * Initializes the main page, which includes adding 
   * the user panel and the filter panel to the respective panes.
   */
  @FXML
  final void initialize() {
    userPanel = new UserPanel(person);
    userPane.getChildren().add(userPanel);
    userPanel.addListener(this);


    filterPane.getChildren().add(filterPanel);
    filterPanel.addListener(this);

    

    buildRoomList();
  }

  /**
   * Build list of rooms according to selected filters.
   * Puts the filtered rooms into the roomItemContainer, 
   * which is where the user can select to book them.
   */
  private void buildRoomList() {
    roomItemContainer.getChildren().clear();
    Label label = new Label();
    label.setId("filterError");
    label.setTextFill(Color.RED);

    if (!hotelRoomFilter.hasValidDates()) {
      LocalDate startDate = hotelRoomFilter.getStartDate();
      LocalDate endDate = hotelRoomFilter.getEndDate();
      roomItemContainer.getChildren().add(label);

      if (startDate == null || endDate == null) {
        label.setText(
            "You must choose both a start date "
            + "and an end date to make a reservation."
        );
      } else if (!startDate.isBefore(endDate)) {
        label.setText(
            "You must choose an end date which is "
            + "after the start date to make a reservation."
        );
      } else if (startDate.isBefore(LocalDate.now())) {
        label.setText(
            "You must choose a start date that is "
            + "today or later to make a reservation." 
        );
      }
    }

    Collection<HotelRoom> filteredRooms = hotel.getRooms(hotelRoomFilter);

    for (HotelRoom hotelRoom : filteredRooms) {
      HotelRoomListItem roomItem = new HotelRoomListItem(hotelRoom);
      if (hotelRoomFilter.hasValidDates()) {
        double totalPrice = hotelRoom.getPrice(
                              hotelRoomFilter.getStartDate(),
                              hotelRoomFilter.getEndDate());
        roomItem.setOnMakeReservationButtonAction((event) -> {
          person.makeReservation(
              hotelRoom,
              hotelRoomFilter.getStartDate(),
              hotelRoomFilter.getEndDate()
          );
          buildRoomList();
        });
        roomItem.setTotalPriceLabel("Total price: "
                                    + Double.toString(totalPrice));
        if (person.getBalance() < totalPrice) {
          roomItem.setOnMakeReservationButtonAction(null);
          roomItem.setErrorLabel("You don't have enough money to "
                                  + "make this reservation.");
        }
      } else {
        roomItem.setOnMakeReservationButtonAction(null);
      }
      roomItemContainer.getChildren().add(roomItem);
    }
  }

  /**
   * Receive notification (as a listener) and act accordingly.
   * Includes notification to log out, and filtering.
   */
  @Override
  public final void receiveNotification(
      final Object from,
      final Message message,
      final Object data) {
    if (message == Message.Filter && data instanceof HotelRoomFilter) {
      this.hotelRoomFilter = (HotelRoomFilter) data;
      buildRoomList();
    }
    if (message == Message.SignOut) {
      notifyListeners(Message.SignOut, person);
    }

    if (message == Message.MoneyPage) {
      notifyListeners(Message.MoneyPage, person);
    }
  }

  /**
   * Add a listener to the main page.
   *
   * @param listener The listener to be added
   */
  public final void addListener(final MessageListener listener) {
    if (listener == null) {
      throw new NullPointerException("Listener is null.");
    }
    listeners.add(listener);
  }

  /**
   * Remove a listener to the login page.
   *
   * @param listener The listener to be removed
   */
  public final void removeListener(final MessageListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notify listeners of some message (from the Message Enum) and send an object as data.
   *
   * @param message The message (from Message Enum)
   * @param data The object to send with the notification
   */
  public final void notifyListeners(
      final Message message,
      final Object data) {
    for (MessageListener listener : listeners) {
      listener.receiveNotification(this, message, data);
    }
  }
}

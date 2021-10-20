package gr2116.ui.pages;

import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.Person;
import gr2116.ui.components.FilterPanel;
import gr2116.ui.components.HotelRoomFilter;
import gr2116.ui.components.HotelRoomListItem;
import gr2116.ui.components.UserPanel;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import gr2116.ui.utils.FxmlUtils;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;
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
  private final Hotel hotel = new Hotel();
  private HotelRoomFilter hotelRoomFilter
      = new HotelRoomFilter(null, null, null, null, null);
  private final Person person;
  private final Collection<MessageListener> listeners = new HashSet<>();

  /**
   * Constructs a main page for a given person.
   *
   * @param person the person the main page should be constructed for.
   */
  public MainPage(final Person person) {
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

    if (!hotelRoomFilter.hasValidDates()) {
      LocalDate startDate = hotelRoomFilter.getStartDate();
      LocalDate endDate = hotelRoomFilter.getEndDate();

      if (startDate == null || endDate == null) {
        Label label = new Label(
            "You must choose both a start date "
            + "and an end date to make a reservation."
        );
        label.setTextFill(Color.RED);
        roomItemContainer.getChildren().add(label);
      } else if (!startDate.isBefore(endDate)) {
        Label label = new Label(
            "You must choose an end date which is "
            + "after the start date to make a reservation."
        );
        label.setId("filterError");
        label.setTextFill(Color.RED);
        roomItemContainer.getChildren().add(label);
      }
    }

    Predicate<HotelRoom> predicate = hotelRoomFilter.getPredicate();
    Collection<HotelRoom> filteredRooms = hotel.getRooms(predicate);

    for (HotelRoom hotelRoom : filteredRooms) {
      HotelRoomListItem roomItem = new HotelRoomListItem(hotelRoom);
      if (hotelRoomFilter.hasValidDates()) {
        roomItem.setOnMakeReservationButtonAction((event) -> {
          person.makeReservation(
              hotelRoom,
              hotelRoomFilter.getStartDate(),
              hotelRoomFilter.getEndDate()
          );
          buildRoomList();
        });
      } else {
        roomItem.setOnMakeReservationButtonAction(null);
      }
      roomItemContainer.getChildren().add(roomItem);
    }
  }

  /**
   * Add rooms to the hotel. These rooms will show up when filters are selected.
   * Usually, the rooms will be provided by the Loader class.
   *
   * @param rooms Collection of rooms to add to the hotel.
   */
  public final void addRooms(final Collection<HotelRoom> rooms) {
    rooms.forEach((HotelRoom room) -> hotel.addRoom(room));
    buildRoomList();
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

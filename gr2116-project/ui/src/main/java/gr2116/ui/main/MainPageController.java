package gr2116.ui.main;

import gr2116.core.HotelRoom;
import gr2116.core.HotelRoomFilter;
import gr2116.core.Person;
import gr2116.core.PersonListener;
import gr2116.ui.DynamicText;
import gr2116.ui.access.HotelAccess;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Main page, which contains the main panel for booking hotel nights.
 */
public class MainPageController implements MessageListener, PersonListener {
  private HotelAccess hotelAccess;
  private HotelRoomFilter hotelRoomFilter
      = new HotelRoomFilter(null, null, null, null, null);
  private Person person;
  private final Collection<MessageListener> listeners = new HashSet<>();

  @FXML
  private VBox roomItemContainer;

  @FXML
  private VBox filterPanelView;

  @FXML
  private VBox userPanelView;

  @FXML
  private Label errorLabel;

  @FXML
  private UserPanelController userPanelViewController;

  @FXML
  private FilterPanelController filterPanelViewController;


  /**
   * Constructs a main page for a given person and hotel.
   *
   */
  public MainPageController() {}

  /**
   * Sets the hotelAccess from which to build the main page.
   *
   * @param hotelAccess the hotelAccess the main page should be constructed for.
   *
   * @throws IllegalArgumentException throws if hotel is null.
   */
  public void setHotelAccess(HotelAccess hotelAccess) {
    if (hotelAccess == null) {
      throw new IllegalArgumentException("Hotel is null.");
    }
    this.hotelAccess = hotelAccess;
    if (person != null) {
      buildRoomList();
    }
  }

  /**
   * Sets the person to build the main page for.
   *
   * @param person the person the main page should be constructed for.
   *
   * @throws IllegalArgumentException throws if person is null.
   */
  public void setPerson(Person person) {
    if (person == null) {
      throw new IllegalArgumentException("Person is null.");
    }
    if (this.person != null) {
      this.person.removeListener(this);
    }
    this.person = person;
    person.addListener(this);
    userPanelViewController.setPerson(person);
    if (hotelAccess != null) {
      buildRoomList();
    }
  }

  /**
   * Initializes the main page, which includes adding 
   * the user panel and the filter panel to the respective panes.
   */
  @FXML
  final void initialize() {
    userPanelViewController.addListener(this);
    filterPanelViewController.addListener(this);

    errorLabel.setTextFill(Color.RED);
    errorLabel.setMinHeight(Region.USE_PREF_SIZE);
  }

  /**
   * Build list of rooms according to selected filters.
   * Puts the filtered rooms into the roomItemContainer, 
   * which is where the user can select to book them.
   */
  private void buildRoomList() {
    if (person == null || hotelAccess == null) {
      throw new IllegalStateException("Cannot build room list without person and hotel.");
    }
    // Sets first empty list of rooms.
    roomItemContainer.getChildren().clear();
    errorLabel.setText("");
    if (!hotelRoomFilter.hasValidDates()) {
      LocalDate startDate = hotelRoomFilter.getStartDate();
      LocalDate endDate = hotelRoomFilter.getEndDate();

      if (startDate == null || endDate == null) {
        errorLabel.setText(DynamicText.StartAndEndError.getMessage());
      } else if (!startDate.isBefore(endDate)) {
        errorLabel.setText(DynamicText.TimeOrderError.getMessage());
      } else if (startDate.isBefore(LocalDate.now())) {
        errorLabel.setText(DynamicText.BeforeNowError.getMessage());
      }
    }
    
    Collection<HotelRoom> filteredRooms = hotelAccess.getRooms(hotelRoomFilter);

    // If dates are valid, add all filtered room.
    for (HotelRoom hotelRoom : filteredRooms) {
      HotelRoomListItem roomItem = new HotelRoomListItem(hotelRoom);
      if (hotelRoomFilter.hasValidDates()) {
        double totalPrice = hotelRoom.getPrice(
                              hotelRoomFilter.getStartDate(),
                              hotelRoomFilter.getEndDate());
        roomItem.setOnMakeReservationButtonAction((event) -> {
          hotelAccess.makeReservation(
              person,
              hotelRoom.getNumber(),
              hotelRoomFilter.getStartDate(),
              hotelRoomFilter.getEndDate()
          );
          buildRoomList();
        });
        roomItem.setTotalPriceLabel(Double.toString(totalPrice));
        if (person.getBalance() < totalPrice) {
          roomItem.setOnMakeReservationButtonAction(null);
          roomItem.setErrorLabel(DynamicText.NotEnoughMoneyError.getMessage());
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
  public final void receiveMessage(
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

  @Override
  public void onPersonChanged(Person person) {
    buildRoomList();
  }

  /**
   * Add a listener to the main page.
   *
   * @param listener The listener to be added
   */
  public final void addListener(final MessageListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Listener is null.");
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
      listener.receiveMessage(this, message, data);
    }
  }
}

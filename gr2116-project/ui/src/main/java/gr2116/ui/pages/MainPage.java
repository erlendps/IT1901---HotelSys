package gr2116.ui.pages;
import gr2116.ui.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

import gr2116.core.Hotel;
import gr2116.core.HotelRoom;
import gr2116.core.Person;
import gr2116.ui.components.UserPanel;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import gr2116.ui.components.FilterPanel;
import gr2116.ui.components.HotelRoomFilter;
import gr2116.ui.components.HotelRoomListItem;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
   * @param person
   */
  public MainPage(final Person person) {
    this.person = person;
    FXMLUtils.loadFXML(this);
  }

  @FXML
  private VBox roomItemContainer;

  @FXML
  private AnchorPane filterPane;

  @FXML
  private AnchorPane userPane;

  @FXML
  final void initialize() {
    userPanel = new UserPanel(person);
    userPane.getChildren().add(userPanel);
    userPanel.addListener(this);

    filterPane.getChildren().add(filterPanel);
    filterPanel.addListener(this);

    buildRoomList();
  }

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
        roomItemContainer.getChildren().add(label);
      } else if (endDate.isBefore(startDate)) {
        Label label = new Label(
          "The end date must be after the start date."
        );
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

  public final void addRooms(final Collection<HotelRoom> rooms) {
    rooms.forEach((HotelRoom room) -> hotel.addRoom(room));
    buildRoomList();
  }

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
  }

  public final void addListener(final MessageListener listener) {
    listeners.add(listener);
  }

  public final void removeListener(final MessageListener listener) {
    listeners.remove(listener);
  }

  public final void notifyListeners(
      final Message message,
      final Object data) {
    for (MessageListener listener : listeners) {
      listener.receiveNotification(this, message, data);
    }
  }
}

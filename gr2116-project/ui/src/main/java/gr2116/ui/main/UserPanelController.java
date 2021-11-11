package gr2116.ui.main;

import gr2116.core.Person;
import gr2116.core.PersonListener;
import gr2116.core.Reservation;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import java.util.Collection;
import java.util.HashSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * UserPanel, the panel to see your balance, your reservations and log out.
 */
public class UserPanelController implements PersonListener {
  private Collection<MessageListener> listeners = new HashSet<>();
  private Person person;

  @FXML
  private Label nameLabel;

  @FXML
  private Label usernameLabel;

  @FXML
  private Label balanceLabel;

  @FXML
  private Button signOutButton;

  @FXML
  private Button makeDepositButton;

  @FXML
  private ListView<Label> reservationListView;

  /**
   * Initialize the user panel.
   *
   */
  public UserPanelController() { }

  /**
   * Initialize the user panel with a person. This persons' attributes will be displayed.
   *
   * @param person the person we want to initialize for
   *
   * @throws IllegalArgumentException if person is null
   */
  public void setPerson(Person person) {
    if (person == null) {
      throw new IllegalArgumentException("Error initializing UserPanel: person is null.");
    }
    this.person = person;
    person.addListener(this);
    updatePanel();
  }

  /**
   * Initialize the FXML. Set the sign out button to send a 
   * message to listeners, to sign out.
   */
  @FXML
  private void initialize() {
    signOutButton.setOnAction((event) -> {
      notifyListeners(Message.SignOut);
    });

    makeDepositButton.setOnAction((event) -> {
      notifyListeners(Message.MoneyPage);
    });
  }

  /**
   * Add a listener.
   *
   * @param listener The listener to be added
   */
  public final void addListener(final MessageListener listener) {
    listeners.add(listener);
  }

  /**
   * Remove a listener.
   *
   * @param listener The listener to be removed
   */
  public final void removeListener(final MessageListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notify the listeners with a message.
   *
   * @param message the message to notify
   */
  public final void notifyListeners(final Message message) {
    for (MessageListener listener : listeners) {
      listener.receiveMessage(this, message, null);
    }
  }


  /**
   * Receive a notification to update attributes for the person.
   *
   * @param person person to update attributes for
   */
  @Override
  public final void onPersonChanged(final Person person) {
    if (this.person == person) {
      updatePanel();
    }
  }

  /**
   * Update the person panel with attributes that might have changed.
   * This includes name, email, balance and reservations.
   */
  private void updatePanel() {
    nameLabel.setText(person.getName());
    usernameLabel.setText(person.getUsername());
    balanceLabel.setText(Double.toString(person.getBalance()));

    reservationListView.getItems().clear();
    // Add the person's reservations.
    for (Reservation reservation : person.getReservations()) {
      int roomNumber = reservation.getRoomNumber();
      String startDate = reservation.getStartDate().toString();
      String endDate = reservation.getEndDate().toString();
      Label label = new Label("Room: " + roomNumber
          + ". From " + startDate
          + " to " + endDate + "."
      );
      label.setId("hotelRoom" + Integer.toString(roomNumber)
          + "reservation" + startDate + "to" + endDate);
      reservationListView.getItems().add(label);
    }
  }
}

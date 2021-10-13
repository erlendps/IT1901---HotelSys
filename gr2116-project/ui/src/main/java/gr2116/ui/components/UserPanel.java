package gr2116.ui.components;

import gr2116.core.Person;
import gr2116.core.PersonListener;
import gr2116.core.Reservation;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import gr2116.ui.utils.FxmlUtils;
import java.util.Collection;
import java.util.HashSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * UserPanel, the panel to see your balance, your reservations and log out.
 */
public class UserPanel extends VBox implements PersonListener {
  private Collection<MessageListener> listeners = new HashSet<>();
  private Person person;

  @FXML
  private Label nameLabel;
  @FXML
  private Label emailLabel;
  @FXML
  private Label balanceLabel;
  @FXML
  private Button signOutButton;

  @FXML
  private ListView<Label> reservationListView;

  /**
   * Initialize the user panel with a person. This persons' attributes will be displayed.
   *
   * @param person the person we want to initialize for.
   */
  public UserPanel(final Person person) {
    this.person = person;
    person.addListener(this);
    FxmlUtils.loadFXML(this);
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
    updatePanel(person);
  }

  /**
   * Add a listener.
   *
   * @param listener The listener to be added.
   */
  public final void addListener(final MessageListener listener) {
    listeners.add(listener);
  }

  /**
   * Remove a listener.
   *
   * @param listener The listener to be removed.
   */
  public final void removeListener(final MessageListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notify the listeners with a message.
   *
   * @param message the message to notify.
   */
  public final void notifyListeners(final Message message) {
    for (MessageListener listener : listeners) {
      listener.receiveNotification(this, message, null);
    }
  }

  /**
   * Receive a notification to update attributes for the person.
   */
  @Override
  public final void receiveNotification(final Person person) {
    updatePanel(person);
  }

  /**
   * Update the person panel with attributes that might have changed.
   * This includes name, email, balance and reservations.
   *
   * @param person The person to show attributes for.
   */
  private void updatePanel(final Person person) {
    nameLabel.setText(person.getName());
    emailLabel.setText(person.getEmail());
    balanceLabel.setText(Double.toString(person.getBalance()));

    reservationListView.getItems().clear();
    for (Reservation reservation : person.getReservations()) {
      // TODO: Make prettier reservationListItems
      int roomNumber = reservation.getRoom().getNumber();
      String startDate = reservation.getStartDate().toString();
      String endDate = reservation.getEndDate().toString();
      reservationListView.getItems().add(
        new Label("Room: " + roomNumber
          + ". From " + startDate
          + " to " + endDate + "."
        )
      );
    }
  }
}

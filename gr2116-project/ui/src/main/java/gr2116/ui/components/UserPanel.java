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

  public UserPanel(final Person person) {
    this.person = person;
    person.addListener(this);
    FxmlUtils.loadFXML(this);
  }

  @FXML
  private void initialize() {
    signOutButton.setOnAction((event) -> {
      notifyListeners(Message.SignOut);
    });
    updatePanel(person);
  }

  public final void addListener(final MessageListener listener) {
    listeners.add(listener);
  }

  public final void removeListener(final MessageListener listener) {
    listeners.remove(listener);
  }

  public final void notifyListeners(final Message message) {
    for (MessageListener listener : listeners) {
      listener.receiveNotification(this, message, null);
    }
  }

  @Override
  public final void receiveNotification(final Person person) {
    updatePanel(person);
  }

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

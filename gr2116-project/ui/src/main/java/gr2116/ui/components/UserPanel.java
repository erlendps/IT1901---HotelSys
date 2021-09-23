package gr2116.ui.components;

import java.util.Collection;
import java.util.HashSet;

import gr2116.core.HotelRoom;
import gr2116.core.Person;
import gr2116.core.PersonListener;
import gr2116.core.Reservation;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import gr2116.ui.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class UserPanel extends VBox implements PersonListener {
	private Collection<MessageListener> listeners = new HashSet<>();
	private Person person;

	@FXML
	private Label nameLabel, emailLabel, balanceLabel;

	@FXML
	private Button signOutButton;

	@FXML
	private ListView<Label> reservationListView;
	
    public UserPanel(Person person) {
		this.person = person;
		person.addListener(this);
        FXMLUtils.loadFXML(this);
    }
	
    @FXML
    private void initialize() {
		signOutButton.setOnAction((event) -> {
			notifyListeners(Message.SignOut);
		});
		updatePanel(person);
    }
	
	public void addListener(MessageListener listener) {
		listeners.add(listener);
	}
	public void removeListener(MessageListener listener) {
		listeners.remove(listener);
	}
	public void notifyListeners(Message message) {
		for (MessageListener listener : listeners) {
			listener.receiveNotification(this, message, null);
		}
	}

	@Override
	public void receiveNotification(Person person) {
		updatePanel(person);
	}

	private void updatePanel(Person person) {
		nameLabel.setText(person.getName());
		emailLabel.setText(person.getEmail());
		balanceLabel.setText(Double.toString(person.getBalance())); // TODO: Make sure this stays up to date.

		reservationListView.getItems().clear();
		for (Reservation reservation : person.getReservations()) {
			String roomNumber = Integer.toString(reservation.getRoom().getNumber()); // TODO: Make prettier reservationListItems
			String startDate = reservation.getStartDate().toString();
			String endDate = reservation.getEndDate().toString();
			reservationListView.getItems().add(new Label("Room: " + roomNumber + ". From " + startDate + " to " + endDate + "."));
		}
	}
}
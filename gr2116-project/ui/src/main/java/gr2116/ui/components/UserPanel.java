package gr2116.ui.components;

import java.util.Collection;
import java.util.HashSet;

import gr2116.core.Person;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import gr2116.ui.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class UserPanel extends VBox {
	private Collection<MessageListener> listeners = new HashSet<>();
	private final Person person;
	
	@FXML
	private Label nameLabel, emailLabel;

	@FXML
	private Button signOutButton;

	
    public UserPanel(Person person) {
		this.person = person;
        FXMLUtils.loadFXML(this);
    }
	
    @FXML
    private void initialize() {
		signOutButton.setOnAction((event) -> {
			notifyListeners(Message.SignOut);
		});
		nameLabel.setText(person.getName());
		emailLabel.setText(person.getEmail());
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
}
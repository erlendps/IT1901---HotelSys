package gr2116.ui.pages;
import java.util.Collection;
import java.util.HashSet;

import gr2116.core.Person;
import gr2116.ui.components.MessageListener;
import gr2116.ui.message.Message;
import gr2116.ui.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginPage extends AnchorPane {
    private Collection<MessageListener> listeners = new HashSet<>();
    private Person person;

    @FXML
    private TextField nameTextField, emailTextField;

    @FXML
    private Button signInButton;

    @FXML
    private Label emailErrorLabel;
    
    public LoginPage() {
        FXMLUtils.loadFXML(this);
    }
    
    @FXML
    private void initialize() {
        signInButton.setOnAction((event) -> {
            try {
                person = new Person(nameTextField.getText());
                try {
                    person.setEmail(emailTextField.getText());
                    notifyListeners(Message.SignIn, person);
                } catch (Exception e) {
                    emailErrorLabel.setText("Invalid email!");
                }
            } catch (Exception e) {
                emailErrorLabel.setText("Invalid name!");
            }
        });
    }
    
    public void addListener(MessageListener listener) {
		listeners.add(listener);
	}
	public void removeListener(MessageListener listener) {
		listeners.remove(listener);
	}
	public void notifyListeners(Message message, Object data) {
        for (MessageListener listener : listeners) {
            listener.receiveNotification(this, message, data);
        }
    }
}

package gr2116.ui.pages;
import java.util.Collection;
import java.util.HashSet;

import gr2116.core.Person;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
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
    private Label emailErrorLabel, nameTitleLabel;
    
    public LoginPage() {
        FXMLUtils.loadFXML(this);
    }
    
    @FXML
    private void initialize() {
        setNameVisible(false);

        signInButton.setOnAction((event) -> {
            try {
                // Try to sign in with existing email
                throw new Exception("Sign in with existing email not implemented!");
            }
            catch (Exception e) {
                try {
                    if (!nameTextField.isVisible()) {
                        setNameVisible(true);
                        return;
                    }
                    person = new Person(nameTextField.getText());
                    try {
                        person.setEmail(emailTextField.getText());
                        notifyListeners(Message.SignIn, person);
                    } catch (Exception e1) {
                        emailErrorLabel.setText("Invalid email!");
                    }
                } catch (Exception e2) {
                    emailErrorLabel.setText("Invalid name!");
                }
            }
        });
    }

    private void setNameVisible(boolean visible) {
        nameTextField.setVisible(visible);
        nameTitleLabel.setVisible(visible);
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

package gr2116.ui.front;

import gr2116.core.Person;
import gr2116.ui.DynamicText;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import java.util.Collection;
import java.util.HashSet;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * 
 */
public class LoginPanelController {
  private Collection<MessageListener> listeners = new HashSet<>();

  @FXML
  private TextField usernameTextField;
  @FXML
  private TextField passwordTextField;
  @FXML
  private Label errorLabel;

  @FXML
  private void loginButtonOnAction() {
    String username = usernameTextField.getText();
    if (!Person.isValidUsername(username)) {
      setErrorLabel(DynamicText.InvalidUsername.getMessage());
      return;
    }
    
    String password = passwordTextField.getText();
    if (!Person.isValidPassword(password)) {
      setErrorLabel(DynamicText.InvalidPassword.getMessage());
      return;
    }

    Person person = new Person(username);
    person.setPassword(password);

    notifyListeners(Message.Login, person);
  }

  @FXML
  private void cancelButtonOnAction() {
    notifyListeners(Message.Cancel, null);
  }

  public void setErrorLabel(String text) {
    errorLabel.setText(text);
  }

  /**
   * Add a listener to the login page.
   *
   * @param listener The listener to be added.
   */
  public final void addListener(final MessageListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Cant add null to listeners.");
    }
    listeners.add(listener);
  }

  /**
   * Remove a listener to the login page.
   *
   * @param listener The listener to be removed.
   */
  public final void removeListener(final MessageListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notify listeners that something has happened.
   * Carries a message, which can be chosen from an enum of values.
   * Carries data, which is any object.
   *
   * @param message Pick a message from the Enum Message to be sent
   * @param data Any object
   */
  public final void notifyListeners(final Message message, final Object data) {
    for (MessageListener listener : listeners) {
      listener.receiveMessage(this, message, data);
    }
  }
}

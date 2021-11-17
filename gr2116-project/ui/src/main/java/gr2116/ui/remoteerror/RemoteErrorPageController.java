package gr2116.ui.remoteerror;

import java.util.Collection;
import java.util.HashSet;

import gr2116.ui.DynamicText;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 * RemoteErrorPage class, which is used to inform the user that there is something wrong
 * with the REST API.
 */
public class RemoteErrorPageController {

  Collection<MessageListener> listeners = new HashSet<>();

  @FXML
  private Button reconnectButton;

  @FXML
  private Text errorText;

  @FXML
  private Label failuresLabel;

  public RemoteErrorPageController() {}

  /**
   * Initializes this controller. It sets the error text and defines the Action event
   * when pressing the reconnect button.
   */
  @FXML
  public void initialize() {
    errorText.setText(DynamicText.RemoteServerError.getMessage());
    reconnectButton.setOnAction((event) -> {
      notifyListeners(Message.Reconnect);
    });
  }

  /**
   * Increments the number of failures.
   */
  public void incrementFailures() {
    int failures = Integer.parseInt(failuresLabel.getText());
    failuresLabel.setText(String.valueOf(failures + 1));
  }

  /**
   * Add a listener to the main page.
   *
   * @param listener The listener to be added
   *
   * @throws IllegalArgumentException if the listener is null
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
  public final void notifyListeners(final Message message) {
    System.out.println(listeners);
    for (MessageListener listener : listeners) {
      listener.receiveMessage(this, message, null);
    }
  }
}

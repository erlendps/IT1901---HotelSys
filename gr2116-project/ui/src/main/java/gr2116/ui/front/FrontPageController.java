package gr2116.ui.front;

import gr2116.core.Person;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import java.util.Collection;
import java.util.HashSet;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * Front page controller, for the page the user meets when the program starts.
 */
public class FrontPageController implements MessageListener {
  private Collection<MessageListener> listeners = new HashSet<>();

  @FXML
  private VBox panelContainer;
  @FXML
  private VBox defaultPanel;
  @FXML
  private VBox signUpPanelView;
  @FXML
  private LoginPanelController loginPanelViewController;
  @FXML
  private VBox loginPanelView;
  @FXML
  private SignUpPanelController signUpPanelViewController;

  /**
   * Initialize FrontPage.
   * Shows the default panel (sign up and login buttons)
   */
  @FXML
  private void initialize() {
    signUpPanelViewController.addListener(this);
    loginPanelViewController.addListener(this);
    showPanel(defaultPanel);
  }

  @FXML
  private void startLoginButtonOnAction() {
    showPanel(loginPanelView);
  }

  @FXML
  private void startSignUpButtonOnAction() {
    showPanel(signUpPanelView);
  }

  /**
   * Clears the panelContainer and shows a new panel.
   *
   * @param panel the panel to show
   */
  private void showPanel(VBox panel) {
    panelContainer.getChildren().clear();
    panelContainer.getChildren().add(panel);
  }

  public void showDefaultPanel() {
    showPanel(defaultPanel);
  }

  public void setLoginPanelViewErrorLabel(String text) {
    loginPanelViewController.setErrorLabel(text);
  }

  public void setSignUpPanelViewErrorLabel(String text) {
    signUpPanelViewController.setErrorLabel(text);
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

  /**
   * Receives messages from various parts of the program,
   * and acts accordingly.
   * Implemented messages are:
   * - Message.SignUp, with Person as data
   * - Message.Login, with Person as data
   * - Message.Cancel

   *  @param from the object the message is from
   *  @param message the message to receive
   *  @param data the data to go along with the message
   * 
   */
  @Override
  public void receiveMessage(Object from, Message message, Object data) {
    if (message == Message.Cancel) {
      showDefaultPanel();
    } else if (from instanceof LoginPanelController
        && message == Message.Login
        && data instanceof Person) {
      notifyListeners(Message.Login, data);
    } else if (from instanceof SignUpPanelController
        && message == Message.SignUp
        && data instanceof Person) {
      notifyListeners(Message.SignUp, data);
      notifyListeners(Message.Login, data);
    }
  }
}

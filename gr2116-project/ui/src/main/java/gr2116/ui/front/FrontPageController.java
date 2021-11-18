package gr2116.ui.front;

import gr2116.core.Person;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import java.util.Collection;
import java.util.HashSet;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * Login page class, which is what meets the user when the program starts.
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
   * Initialize FrontPage. Sets action for the login button.
   * Set sign-in button to validate input.
   * Add register field if necessary.
   * If login is completed, redirect to
   * main page.
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

  private void showPanel(VBox panel) {
    panelContainer.getChildren().clear();
    panelContainer.getChildren().add(panel);
  }

  public void showDefaultPanel() {
    panelContainer.getChildren().clear();
    panelContainer.getChildren().add(defaultPanel);
  }

  public LoginPanelController getLoginPanelViewController() {
    return loginPanelViewController;
  }

  public SignUpPanelController getSignUpPanelViewController() {
    return signUpPanelViewController;
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
    }
  }
}

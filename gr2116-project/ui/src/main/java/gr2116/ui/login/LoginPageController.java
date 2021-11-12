package gr2116.ui.login;

import gr2116.core.Person;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import java.util.Collection;
import java.util.HashSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Login page class, which is what meets the user when the program starts.
 */
public class LoginPageController {
  private Collection<MessageListener> listeners = new HashSet<>();
  private Collection<Person> loadedPersons;

  @FXML
  private TextField nameTextField;
  @FXML
  private TextField usernameTextField;
  @FXML
  private Button signInButton;

  @FXML
  private Label usernameErrorLabel;
  @FXML
  private Label nameTitleLabel;

  /**
   * Load FXML for login page.
   */
  public LoginPageController() {}

  /**
   * Take in a collection of persons and add them to the internal state,
   * so that they can be used for login.
   *
   * @param loadedPersons Collection of persons, usually imported with Loader
   */
  public final void setLoadedPersons(final Collection<Person> loadedPersons) {
    this.loadedPersons = new HashSet<>(loadedPersons);
  }

  /**
   * Initialize LoginPage. Sets action for the login button.
   * Set sign-in button to validate input.
   * Add register field if necessary.
   * If login is completed, redirect to
   * main page.
   */
  @FXML
  private void initialize() {
    setNameVisible(false);
    signInButton.setOnAction((event) -> {
      String username = usernameTextField.getText();
      String name = nameTextField.getText();

      if (!Person.isValidUsername(username)) {
        usernameErrorLabel.setText("Invalid username!");
        return;
      }
      // Find person and if already registred, notify to
      // sign in.
      for (Person loadedPerson : loadedPersons) {
        if (loadedPerson.getUsername().equals(username)) {
          notifyListeners(Message.SignIn, loadedPerson);
          return;
        }
      }

      // If user was not registred, make name field appear.
      if (!nameTextField.isVisible()) {
        setNameVisible(true);
        return;
      } else if (!Person.isValidName(name)) {
        usernameErrorLabel.setText("Invalid name!");
        return;
      }
      // If both fields were set, create new person and log in.
      Person person = new Person(name);
      person.setUsername(username);
      notifyListeners(Message.SignIn, person);
    });
  }

  /**
   * The name field should only be visible
   * if the username was not found in the collection of persons,
   * in which case a new account will be created.
   * This method sets the field visibility.
   *
   * @param visible Boolean, if true the field is visible.
   */
  private void setNameVisible(final boolean visible) {
    nameTextField.setVisible(visible);
    nameTitleLabel.setVisible(visible);
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

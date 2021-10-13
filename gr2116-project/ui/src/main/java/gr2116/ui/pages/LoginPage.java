package gr2116.ui.pages;

import gr2116.core.Person;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import gr2116.ui.utils.FxmlUtils;
import java.util.Collection;
import java.util.HashSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Login page class, which is what meets the user when the program starts.
 */
public class LoginPage extends AnchorPane {
  private Collection<MessageListener> listeners = new HashSet<>();
  private Collection<Person> loadedPersons;

  @FXML
  private TextField nameTextField;
  @FXML
  private TextField emailTextField;
  @FXML
  private Button signInButton;

  @FXML
  private Label emailErrorLabel;
  @FXML
  private Label nameTitleLabel;

  /**
   * Set the login page to show up in the program window
   */
  public LoginPage() {
    FxmlUtils.loadFXML(this);
  }

  /**
   * Take in a collection of persons and add them to the internal state, so that they can be used for login
   * @param loadedPersons Collection of persons, usually imported with Loader
   */
  public final void setLoadedPersons(final Collection<Person> loadedPersons) {
    this.loadedPersons = new HashSet<>(loadedPersons);
  }
  /**
   * Initialize LoginPage. Sets action for the login button.
   */
  @FXML
  private void initialize() {
    setNameVisible(false);

    signInButton.setOnAction((event) -> {
      String email = emailTextField.getText();
      String name = nameTextField.getText();

      if (!Person.isValidEmail(email)) {
        emailErrorLabel.setText("Invalid email!");
        return;
      }

      for (Person loadedPerson : loadedPersons) {
        if (loadedPerson.getEmail().equals(email)) {
          notifyListeners(Message.SignIn, loadedPerson);
          return;
        }
      }

      if (!nameTextField.isVisible()) {
        setNameVisible(true);
        return;
      } else if (!Person.isValidName(name)) {
        emailErrorLabel.setText("Invalid name!");
        return;
      }

      Person person = new Person(name);
      person.setEmail(email);
      notifyListeners(Message.SignIn, person);
    });
  }

  /**
   * The name field should only be visible if the email was not found in the collection of persons,
   * in which case a new account will be created. This method sets the field visibility.
   * @param visible Boolean, if true the field is visible.
   */
  private void setNameVisible(final boolean visible) {
    nameTextField.setVisible(visible);
    nameTitleLabel.setVisible(visible);
  }

  /**
   * Add a listener to the login page
   * @param listener The listener
   */
  public final void addListener(final MessageListener listener) {
    listeners.add(listener);
  }

  /**
   * Remove a listener to the login page
   * @param listener The listener
   */
  public final void removeListener(final MessageListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notify listeners that something has happened.
   * Carries a message, which can be chosen from an enum of values.
   * Carries data, which is any object.
   * @param message Pick a message from the Enum Message to be sent
   * @param data Any objet
   */
  public final void notifyListeners(final Message message, final Object data) {
    for (MessageListener listener : listeners) {
      listener.receiveNotification(this, message, data);
    }
  }
}

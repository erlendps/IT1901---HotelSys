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

  public LoginPage() {
    FxmlUtils.loadFXML(this);
  }

  public final void setLoadedPersons(final Collection<Person> loadedPersons) {
    this.loadedPersons = new HashSet<>(loadedPersons);
  }

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

  private void setNameVisible(final boolean visible) {
    nameTextField.setVisible(visible);
    nameTitleLabel.setVisible(visible);
  }

  public final void addListener(final MessageListener listener) {
    listeners.add(listener);
  }

  public final void removeListener(final MessageListener listener) {
    listeners.remove(listener);
  }

  public final void notifyListeners(final Message message, final Object data) {
    for (MessageListener listener : listeners) {
      listener.receiveNotification(this, message, data);
    }
  }
}

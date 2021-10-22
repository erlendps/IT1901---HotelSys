package gr2116.ui.money;

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
 * Money page class, which is where users can add money to their accounts.
 */
public class MoneyPage extends AnchorPane {
  private Collection<MessageListener> listeners = new HashSet<>();
  private Person person;

  @FXML
  private TextField cardTextField;
  @FXML
  private TextField moneyAmountTextField;
  @FXML
  private Button addFundsButton;

  @FXML
  private Button moneyCancelButton;
  
  @FXML
  private Label moneyErrorLabel;

  /**
   * Load FXML for money page. Takes in the person to add money to.
   */
  public MoneyPage(final Person person) {
    this.person = person;
    FxmlUtils.loadFxml(this);
  }

  /**
   * Card number validator.
   * A valid card number is divided into four parts.
   * The first part signifies the country. 195 countries are allowed,
   * meaning the first 4 digits should be between 0001 and 0195. 
   * 0000 is not allowed.
   * The next 8 digits are assigned randomly.
   * The final 4 digits are check digits, and must conform to xxxx % 13 = 9
   * @param cardNumber
   */
  private void validateCardNumber(String cardNumber) {
    if (!cardNumber.matches("[0-9]+")) {
      throw new IllegalArgumentException("Card number must contain numbers only.");
    }
    if (cardNumber.length() != 16) {
      throw new IllegalArgumentException("Card numbers must be exactly 16 characters long.");
    } 
    int firstDigits = Integer.parseInt(cardNumber.substring(0, 4));
    if (!(firstDigits > 0) || !(firstDigits <= 195)) {
      throw new IllegalArgumentException("Card number has invalid format.");
    } 

    int lastDigits = Integer.parseInt(cardNumber.substring(12, 16));
    if (lastDigits % 13 != 9) {
      throw new IllegalArgumentException("Card number has invalid format.");
    }
  }
  /**
   * Method to validate a money string.
   * Money can only be a posiitve integer, strictly less than one million.
   * (1 000 000)
   *
   * @param moneyAmount
   * @throws IllegalArgumentException if balance is not a positive integer between 1 and 999 999.
   */
  private void validateMoneyAmount(String moneyAmount) {
    if (!moneyAmount.matches("^[0-9]+$")) {
      throw new IllegalArgumentException("Balance must be a positive integer.");
    }
    if (moneyAmount.length() > 6) {
      throw new IllegalArgumentException("Balance must be less than 1 000 000.");
    }
    int money = Integer.parseInt(moneyAmount);
    if (money == 0) {
      throw new IllegalArgumentException("Balance must be strictly greater than zero.");
    }
    
  }
  /**
   * Initialize MoneyPage. Sets action for the add funds-button.
   */
  @FXML
  private void initialize() {

    addFundsButton.setOnAction((event) -> {
      String cardNumber = cardTextField.getText();
      String moneyAmount = moneyAmountTextField.getText();
      try {
        validateCardNumber(cardNumber);
      } catch (Exception e) {
        moneyErrorLabel.setText(e.getMessage());
        return;
      }
      try {
        validateMoneyAmount(moneyAmount);
      } catch (Exception e) {
        moneyErrorLabel.setText(e.getMessage());
        return;
      }
      person.addBalance(Integer.parseInt(moneyAmount));
      
      notifyListeners(Message.SignIn, person);
    });

    moneyCancelButton.setOnAction((event) -> {
      notifyListeners(Message.SignIn, person);
    });

  }

  /**
   * Add a listener to the money page.
   *
   * @param listener The listener to be added.
   */
  public final void addListener(final MessageListener listener) {
    listeners.add(listener);
  }

  /**
   * Remove a listener to the money page.
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
      listener.receiveNotification(this, message, data);
    }
  }
}

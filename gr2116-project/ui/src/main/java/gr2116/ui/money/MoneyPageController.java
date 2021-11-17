package gr2116.ui.money;

import gr2116.core.Person;
import gr2116.ui.DynamicText;
import gr2116.ui.access.HotelAccess;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import java.util.Collection;
import java.util.HashSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Money page class, which is where users can add money to their accounts.
 */
public class MoneyPageController {
  private Collection<MessageListener> listeners = new HashSet<>();
  private Person person;
  private HotelAccess hotelAccess;

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
  public MoneyPageController() { }

  public void setPerson(Person person) {
    this.person = person;
  }

  /**
   * Sets the hotelAccess.
   *
   * @param hotelAccess the hotelAccess
   *
   * @throws IllegalArgumentException throws if hotel is null.
   */
  public void setHotelAccess(HotelAccess hotelAccess) {
    if (hotelAccess == null) {
      throw new IllegalArgumentException("Hotel is null.");
    }
    this.hotelAccess = hotelAccess;
  }

  /**
   * Validates the given card number.
   * Checks the following:
   * 1) That all characters are digits (0-9) and the length is 16.
   * 2) That the first four digits matches a Visa or a Mastercard
   * 3) That the control digit (last digit) is correct.
   *
   * @param cardNumber the given card number.
   */
  private void validateCardNumber(String cardNumber) {
    cardNumber = cardNumber.replaceAll(" ", "");
    if (!cardNumber.matches("[0-9]+")) {
      throw new IllegalArgumentException(DynamicText.NonNumericCardNumberError.getMessage());
    }
    if (cardNumber.length() != 16) {
      throw new IllegalArgumentException(DynamicText.WrongLengthCardNumberError.getMessage());
    }
    int first = Integer.parseInt(cardNumber.substring(0, 1));
    int firstTwo = Integer.parseInt(cardNumber.substring(0, 2));
    int firstFour = Integer.parseInt(cardNumber.substring(0, 4));

    if (!(first == 4 || (51 <= firstTwo && firstTwo <= 55)
        || (2221 <= firstFour && firstFour <= 2720))) {
      throw new IllegalArgumentException(
        DynamicText.InvalidCardIdentifierError.getMessage());
    } 

    if (!checkLuhnsAlgorithm(cardNumber)) {
      throw new IllegalArgumentException(
        DynamicText.InvalidCardControlDigitError.getMessage());
    }
  }

  /**
   * Checks the given card number, using Luhns algorithm,
   * Luhns algorithm uses a checksum and compares with the control digit (the last digit).
   *
   * @param cardNumber the given card number.
   * @return whether or not the last digit of the card number is correct. 
   */
  private boolean checkLuhnsAlgorithm(String cardNumber) {
    int numberOfDigits = cardNumber.length();
    int checkSum = 0;
    boolean isSecond = false;
    for (int i = numberOfDigits - 1; i >= 0; i--) {
      int d = cardNumber.charAt(i) - '0';
      if (isSecond == true) {
        d = d * 2;
      }
      checkSum += d / 10;
      checkSum += d % 10;
      isSecond = !isSecond;
    }
    return (checkSum % 10 == 0);
  }
 
  /**
   * Method to validate a money string.
   * Money can only be a posiitve integer, strictly less than one million.
   * (1 000 000)
   *
   * @param moneyAmount the amount to validate
   * @throws IllegalArgumentException if balance is not a positive integer between 1 and 999 999.
   */
  private void validateMoneyAmount(String moneyAmount) {
    if (!moneyAmount.matches("^[0-9]+$")) {
      throw new IllegalArgumentException(DynamicText.NonIntegerError.getMessage());
    }
    if (moneyAmount.length() > 6) {
      throw new IllegalArgumentException(DynamicText.TooLargeBalanceError.getMessage());
    }
    double money = Double.parseDouble(moneyAmount);
    if (money == 0) {
      throw new IllegalArgumentException(DynamicText.ZeroBalanceError.getMessage());
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
      hotelAccess.addBalance(person, Double.parseDouble(moneyAmount));
      
      notifyListeners(Message.MainPage, person);
    });

    moneyCancelButton.setOnAction((event) -> {
      notifyListeners(Message.MainPage, person);
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
      listener.receiveMessage(this, message, data);
    }
  }
}

package gr2116.ui;

/**
 * An enum of dynamic messages that might be displayed to the user.
 */
public enum DynamicText {
  StartAndEndError(
    "You must choose both a start date and an end date to make a reservation."),
  BeforeNowError(
    "You must choose a start date that is today or later to make a reservation."),
  TimeOrderError(
    "You must choose an end date which is after the start date to make a reservation."),
  NotEnoughMoneyError("You don't have enough money to make this reservation."),
  NonNumericCardNumberError("Card number must contain numbers only."),
  WrongLengthCardNumberError("Card numbers must be exactly 16 characters long."),
  InvalidCardIdentifierError("Invalid card issuer - Only Visa and Mastercard are supported."),
  InvalidCardControlDigitError(
    "The card number has invalid format."),
  NonIntegerError("Balance must be a positive integer."),
  TooLargeBalanceError("Balance must be less than 1 000 000."),
  ZeroBalanceError("Balance must be strictly greater than zero."),
  RemoteServerError("Error reading from the server. Perhaps the REST api is not online?"),
  InvalidUsername("The username is invalid."),
  InvalidFirstName("The first name is invalid."),
  InvalidLastName("The last name is invalid."),
  InvalidPassword("The password is invalid."),
  UsernameTaken("The username is taken."),
  UsernameHasNoMatches("The username does not match an existing user."),
  WrongPassword("Incorrect password.");

  private final String message;

  /**
   * Make an entry for the DynamicText enum with a message.
   *
   * @param message A string, not null
   */
  DynamicText(String message) {
    if (message == null) {
      throw new IllegalArgumentException("Message can not be null.");
    }
    this.message = message;
  }

  /**
   * Returns the message to be displayed.
   *
   * @return The message (String) to be displayed
   */
  public String getMessage() {
    return this.message;
  }
}

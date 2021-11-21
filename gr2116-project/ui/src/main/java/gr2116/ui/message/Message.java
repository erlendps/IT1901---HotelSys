package gr2116.ui.message;

/**
 * An enum which allows to pick between different messages.
 * These are used in MessageListener.
 */
public enum Message {
  Login,          // Log in a Person and move to MainPage, set current Person
  SignUp,         // Sign up a Person, does not log in
  SignOut,        // Sign out and move to LoginPage
  Filter,         // Update HotelRoomFilter
  ShowMainPage,   // Move to MainPage
  ShowMoneyPage,  // Move to MoneyPage
  Reconnect,      // Try to reconnect
  Cancel,         // Generic cancel message, used to move back from signup/login to front page
  AddBalance;     // Add balance to the current Person
}

package gr2116.ui.controller;

import gr2116.core.Hotel;
import gr2116.core.Person;
import gr2116.persistence.HotelPersistence;
import gr2116.ui.login.LoginPage;
import gr2116.ui.main.MainPage;
import gr2116.ui.money.MoneyPage;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;

import java.util.ArrayList;
import java.util.Collection;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

/**
 * The controller for the application.
 * The controller implements the MessageListener interface,
 * as it receives notifications from various parts of the program.
 */
public class AppController implements MessageListener {
  private HotelPersistence hotelPersistence = new HotelPersistence();
  private String prefix = "data";
  private Hotel hotel;
  private MainPage mainPage;

  @FXML
  private StackPane root;

  /**
   * Initialize the program.
   * Moves to Login page, which is the first window the user sees.
   */
  @FXML
  private void initialize() {
    load();
    moveToLoginPage();
  }

  @Override
  public final void receiveNotification(final Object from,
      final Message message, final Object data) {
    if (message == Message.SignIn && data instanceof Person) {
      Person person = (Person) data;
      if (mainPage == null) { // Sign in if no one is signed in right now
        mainPage = new MainPage(person, hotel);
      }
      if (!getPersons().contains(person)) {
        hotel.addPerson(person);
      }
      moveToMainPage(person);
    } else if (message == Message.SignOut) {
      save();
      mainPage = null; // Reset the signed in person when signing out
      moveToLoginPage();
    } else if (message == Message.MoneyPage && data instanceof Person) {
      Person person = (Person) data;
      moveToMoneyPage(person);
    }
  }

  public void setPrefix(String prefix) {
    if (!prefix.matches("^([a-z]){3,10}([A-Z]{1}[a-z]{1,8})*$")) {
      throw new IllegalArgumentException("prefix is not valid");
    }
    this.prefix = prefix;
  }

  /**
   * Move to the login page.
   * This involves clearing AppControllers children,
   * creating a new LoginPage instance,
   * adding AppController as a listener and setting the persons from own memory.
   * AppController finally adds the login page as a child instance of itself.
   */
  public void moveToLoginPage() {
    root.getChildren().clear();
    LoginPage loginPage = new LoginPage();
    loginPage.addListener(this);
    if (getPersons() != null) {
      loginPage.setLoadedPersons(getPersons());
    } else {
      throw new IllegalStateException("No loaded persons were set.");
    }
    root.getChildren().add(loginPage);
  }

  /**
   * Moves to main page.
   * This involves clearing AppControllers children,
   * creating a new MainPage instance,
   * adding AppController as a listener and setting the rooms from memory.
   * The MainPage is created with the selected person (which is usually selected from LoginPage).
   * Finally adds MainPage as a child of itself.
   *
   * @param person The person to be logged in as
   */
  public void moveToMainPage(final Person person) {
    if (mainPage == null) {
      throw new IllegalStateException("Page was not loaded before being called.");
    }
    root.getChildren().clear();
    mainPage.addListener(this);
    root.getChildren().add(mainPage);
  }

  public void moveToMoneyPage(final Person person) {
    root.getChildren().clear();
    MoneyPage moneyPage = new MoneyPage(person);
    moneyPage.addListener(this);
    root.getChildren().add(moneyPage);
  }

  public Collection<Person> getPersons() {
    return new ArrayList<>(hotel.getPersons());
  }

  /**
   * Load data from JSON files. Creates a hotel object,
   * which is used to create pages with correct data in them.
   */
  public void load() {
    try {
      hotel = hotelPersistence.loadHotel(prefix);
    } catch (Exception e) {
      throw new IllegalStateException("Something when wrong with loading data. Prefix: " + prefix);
    }
  }
  
  /**
   * Save data to JSON files, from memory.
   * Files might have been modified, as users might have been created 
   * or bookings might have been made.
   */
  private void save() {
    try {
      hotelPersistence.saveHotel(hotel, prefix);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

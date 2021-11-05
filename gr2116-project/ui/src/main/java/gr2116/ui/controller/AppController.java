package gr2116.ui.controller;

import gr2116.core.Person;
import gr2116.persistence.HotelPersistence;
import gr2116.ui.access.DirectHotelAccess;
import gr2116.ui.access.HotelAccess;
import gr2116.ui.login.LoginPage;
import gr2116.ui.main.MainPage;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import gr2116.ui.money.MoneyPage;
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
  private HotelPersistence hotelPersistence = new HotelPersistence("data");
  private HotelAccess hotelAccess = new DirectHotelAccess(hotelPersistence);

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
      if (!getPersons().contains(person)) {
        hotelAccess.addPerson(person);
      }
      moveToMainPage(person);
    } else if (message == Message.SignOut) {
      save();
      moveToLoginPage();
    } else if (message == Message.MoneyPage && data instanceof Person) {
      Person person = (Person) data;
      moveToMoneyPage(person);
    }
  }

  /**
   * Sets the data filename prefix.
   *
   * @param prefix The prefix the be set
   */
  public void setPrefix(String prefix) {
    hotelPersistence.setPrefix(prefix);
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
    root.getChildren().clear();
    MainPage mainPage = new MainPage(person, hotelAccess);
    mainPage.addListener(this);
    root.getChildren().add(mainPage);
  }

  /**
   * Moves to money page.
   *
   * @param person The person to make the money page for
   */
  public void moveToMoneyPage(final Person person) {
    root.getChildren().clear();
    MoneyPage moneyPage = new MoneyPage(person);
    moneyPage.addListener(this);
    root.getChildren().add(moneyPage);
  }

  public Collection<Person> getPersons() {
    return new ArrayList<>(hotelAccess.getPersons());
  }

  /**
   * Load data from JSON files. Creates a hotel object,
   * which is used to create pages with correct data in them.
   */
  public void load() {
    hotelAccess.loadHotel();
  }
  
  /**
   * Save data to JSON files, from memory.
   * Files might have been modified, as users might have been created 
   * or bookings might have been made.
   */
  private void save() {
    hotelAccess.saveHotel();
  }
}

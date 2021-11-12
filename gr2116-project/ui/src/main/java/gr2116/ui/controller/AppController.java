package gr2116.ui.controller;

import gr2116.core.Person;
import gr2116.persistence.HotelPersistence;
import gr2116.ui.login.LoginPageController;
import gr2116.ui.main.MainPageController;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import gr2116.ui.money.MoneyPageController;
import gr2116.ui.access.DirectHotelAccess;
import gr2116.ui.access.HotelAccess;
import gr2116.ui.access.RemoteHotelAccess;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * The controller for the application.
 * The controller implements the MessageListener interface,
 * as it receives notifications from various parts of the program.
 */
public class AppController implements MessageListener {
  private HotelPersistence hotelPersistence = new HotelPersistence("data");
  private HotelAccess hotelAccess = new RemoteHotelAccess(hotelPersistence, URI.create("http://localhost:8080/rest/hotel/"));
  //private HotelAccess hotelAccess = new DirectHotelAccess(hotelPersistence);

  @FXML
  private MainPageController mainPageViewController;

  @FXML
  private VBox mainPageView;

  @FXML
  private LoginPageController loginPageViewController;

  @FXML
  private AnchorPane loginPageView;

  @FXML
  private MoneyPageController moneyPageViewController;

  @FXML
  private AnchorPane moneyPageView;

  @FXML
  private AnchorPane root;

  /**
   * Initialize the program.
   * Moves to Login page, which is the first window the user sees.
   */
  @FXML
  private void initialize() {
    loginPageViewController.addListener(this);
    mainPageViewController.addListener(this);
    moneyPageViewController.addListener(this);
    load();
    moveToLoginPage();
  }

  @Override
  public final void receiveMessage(final Object from,
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
    if (getPersons() != null) {
      loginPageViewController.setLoadedPersons(getPersons());
    } else {
      throw new IllegalStateException("No loaded persons were set.");
    }
    root.getChildren().add(loginPageView);
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
    mainPageViewController.setPerson(person);
    mainPageViewController.setHotelAccess(hotelAccess);
    root.getChildren().clear();
    root.getChildren().add(mainPageView);
  }

  /**
   * Moves to money page.
   *
   * @param person The person to make the money page for
   */
  public void moveToMoneyPage(final Person person) {
    root.getChildren().clear();
    moneyPageViewController.setPerson(person);
    moneyPageViewController.setHotelAccess(hotelAccess);
    root.getChildren().add(moneyPageView);
  }

  public Collection<Person> getPersons() {
    return new ArrayList<>(hotelAccess.getPersons());
  }

  public HotelAccess getHotelAccess() {
    return hotelAccess;
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

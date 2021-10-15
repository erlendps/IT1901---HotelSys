package gr2116.ui.controller;

import gr2116.core.HotelRoom;
import gr2116.core.Person;
import gr2116.persistence.Loader;
import gr2116.persistence.Saver;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import gr2116.ui.pages.LoginPage;
import gr2116.ui.pages.MainPage;
import java.util.Collection;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

/**
 * The controller for the application.
 * The controller implements the MessageListener interface,
 * as it receives notifications from various parts of the program.
 */
public class AppController implements MessageListener {
  private Collection<Person> loadedPersons;
  private Collection<HotelRoom> loadedRooms;
  private String prefix = "data";

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
      if (!loadedPersons.contains(person)) {
        loadedPersons.add(person);
      }
      moveToMainPage(person);
    } else if (message == Message.SignOut) {
      save();
      moveToLoginPage();
    }
  }

  public void setPrefix(String prefix) {
    if (!prefix.matches("^([a-z]){3,10}$")) {
      throw new IllegalArgumentException("prefix is not valid");
    }
    this.prefix = prefix;
  }

  /**
   * Move to the login page.
   * This involves clearing AppControllers children,
   * creating a new LoginPage instance,
   * adding AppController as a listener and setting the loadedPersons from own memory.
   * AppController finally adds the login page as a child instance of itself.
   */
  private void moveToLoginPage() {
    root.getChildren().clear();
    LoginPage loginPage = new LoginPage();
    loginPage.addListener(this);
    if (loadedPersons != null) {
      loginPage.setLoadedPersons(loadedPersons);
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
  private void moveToMainPage(final Person person) {
    root.getChildren().clear();
    MainPage mainPage = new MainPage(person);
    mainPage.addListener(this);
    if (loadedRooms != null) {
      mainPage.addRooms(loadedRooms);
    }
    root.getChildren().add(mainPage);
  }

  /**
   * Load data from JSON files. Puts this data into loadedPersons and loadedRooms,
   * which is used to create pages with correct data in them.
   */
  private void load() {
    Loader loader = new Loader();
    try {
      loader.loadData(prefix);
      loadedPersons = loader.getPersons();
      loadedRooms = loader.getRooms();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Save data to JSON files, from memory.
   * Files might have been modified, as users might have been created 
   * or bookings might have been made.
   */
  private void save() {
    Saver saver = new Saver();
    try {
      saver.writeToFile(loadedRooms, loadedPersons, prefix);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

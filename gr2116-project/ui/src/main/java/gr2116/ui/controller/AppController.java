package gr2116.ui.controller;

import gr2116.core.Person;
import gr2116.ui.DynamicText;
import gr2116.ui.access.DirectHotelAccess;
import gr2116.ui.access.HotelAccess;
import gr2116.ui.access.RemoteHotelAccess;
import gr2116.ui.front.FrontPageController;
import gr2116.ui.main.MainPageController;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import gr2116.ui.money.MoneyPageController;
import gr2116.ui.remoteerror.RemoteErrorPageController;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;

/**
 * The controller for the application.
 * The controller implements the MessageListener interface,
 * as it receives notifications from various parts of the program.
 */
public class AppController implements MessageListener {
  private HotelAccess hotelAccess;
  private Person currentPerson;

  @FXML
  private String endpointUri;

  @FXML
  private MainPageController mainPageViewController;

  @FXML
  private SplitPane mainPageView;

  @FXML
  private FrontPageController frontPageViewController;

  @FXML
  private StackPane frontPageView;

  @FXML
  private MoneyPageController moneyPageViewController;

  @FXML
  private StackPane moneyPageView;

  @FXML
  private RemoteErrorPageController remoteErrorPageViewController;

  @FXML
  private StackPane remoteErrorPageView;

  @FXML
  private StackPane root;

  /**
   * Initialize the program.
   * Moves to Login page, which is the first window the user sees.
   */
  @FXML
  private void initialize() {
    if (endpointUri != null) {
      RemoteHotelAccess remoteHotelAccess;
      try {
        System.out.println("Using endpoint URI @ " + endpointUri);
        remoteHotelAccess = new RemoteHotelAccess(new URI(endpointUri));
        hotelAccess = remoteHotelAccess;
        System.out.println("Using RemoteHotelAccess as access model.");
      } catch (URISyntaxException e) {
        System.err.println("Failed to create a URI with endpoint: " + endpointUri);
        System.err.println(e);
      }
    }
    if (hotelAccess == null) {
      DirectHotelAccess directHotelAccess = new DirectHotelAccess("data");
      hotelAccess = directHotelAccess;
      System.out.println("Using DirectHotelAccess as access model.");
    }
    try {
      load();
      moveToFrontPage();
    } catch (RuntimeException e) {
      moveToRemoteErrorPage();
    }
    frontPageViewController.addListener(this);
    mainPageViewController.addListener(this);
    moneyPageViewController.addListener(this);
    remoteErrorPageViewController.addListener(this);
    mainPageViewController.setHotelAccess(hotelAccess);
  }

  @Override
  public final void receiveMessage(final Object from,
      final Message message, final Object data) {
    if (message == Message.SignUp && data instanceof Person) {
      Person dataPerson = (Person) data;
      Person person = hotelAccess.getPersons().stream().filter(
            (Person p) -> p.getUsername().equals(dataPerson.getUsername())
          ).findAny().orElse(null);
      if (person != null) {
        frontPageViewController.setSignUpPanelViewErrorLabel(
            DynamicText.UsernameTaken.getMessage());
        return;
      }
      hotelAccess.addPerson(dataPerson);
    } else if (message == Message.Login && data instanceof Person) {
      Person dataPerson = (Person) data;
      Person person = hotelAccess.getPersons().stream().filter(
            (Person p) -> p.getUsername().equals(dataPerson.getUsername())
          ).findAny().orElse(null);
      if (person == null) {
        frontPageViewController.setLoginPanelViewErrorLabel(
            DynamicText.UsernameHasNoMatches.getMessage());
        return;
      }
      if (person.getHashedPassword() == null) {
        throw new IllegalArgumentException(
          "Tried to sign up a person without a password!");
      }
      if (!person.getHashedPassword().equals(dataPerson.getHashedPassword())) {
        frontPageViewController.setLoginPanelViewErrorLabel(DynamicText.WrongPassword.getMessage());
        return;
      }
      currentPerson = person;
      moveToMainPage();
    } else if (message == Message.SignOut) {
      currentPerson = null;
      moveToFrontPage();
    } else if (message == Message.ShowMoneyPage) {
      moveToMoneyPage();
    } else if (message == Message.ShowMainPage) {
      moveToMainPage();
    } else if (message == Message.Reconnect) {
      try {
        load();
        moveToFrontPage();
      } catch (RuntimeException e) {
        remoteErrorPageViewController.incrementFailures();
      }
    } else if (message == Message.AddBalance && data instanceof Double) {
      if (currentPerson == null) {
        throw new IllegalStateException("Cannot add balance when current person is null!");
      } 
      hotelAccess.addBalance(currentPerson, (double) data);
    }
  }

  /**
   * Sets the data filename prefix.
   *
   * @param prefix The prefix the be set
   */
  public void setPrefix(String prefix) {
    hotelAccess.setPrefix(prefix);
  }

  /**
   * Move to the login page.
   * This involves clearing AppControllers children,
   * creating a new FrontPage instance,
   * adding AppController as a listener and setting the persons from own memory.
   * AppController finally adds the login page as a child instance of itself.
   */
  public void moveToFrontPage() {
    root.getChildren().clear();
    root.getChildren().add(frontPageView);
    frontPageViewController.showDefaultPanel();
  }

  /**
   * Moves to main page.
   * This involves clearing AppControllers children,
   * creating a new MainPage instance,
   * adding AppController as a listener and setting the rooms from memory.
   * The MainPage is created with the selected person (which is usually selected from FrontPage).
   * Finally adds MainPage as a child of itself.
   *
   * @throws IllegalStateException if currentPerson is null
   */
  public void moveToMainPage() {
    if (currentPerson == null) {
      throw new IllegalStateException("Cannot move to main page without a person!");
    }
    mainPageViewController.setPerson(currentPerson);
    root.getChildren().clear();
    root.getChildren().add(mainPageView);
  }

  /**
   * Moves to money page.
   */
  public void moveToMoneyPage() {
    root.getChildren().clear();
    root.getChildren().add(moneyPageView);
  }

  /**
   * Moves to the RemoteErrorPage. This page is loaded if there is an error with the REST API.
   */
  public void moveToRemoteErrorPage() {
    root.getChildren().clear();
    root.getChildren().add(remoteErrorPageView);
  }

  /**
   * Returns a collection of Person objects.
   *
   * @return Collection of Person
   */
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
}


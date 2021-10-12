package gr2116.ui.controller;

import java.util.Collection;
import gr2116.core.HotelRoom;
import gr2116.core.Person;
import gr2116.persistence.Loader;
import gr2116.persistence.Saver;
import gr2116.ui.message.Message;
import gr2116.ui.message.MessageListener;
import gr2116.ui.pages.LoginPage;
import gr2116.ui.pages.MainPage;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class AppController implements MessageListener {
  private Collection<Person> loadedPersons;
  private Collection<HotelRoom> loadedRooms;

  @FXML
  private StackPane root;

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

  private void moveToLoginPage() {
    root.getChildren().clear();
    LoginPage loginPage = new LoginPage();
    loginPage.addListener(this);
    if (loadedPersons != null) {
      loginPage.setLoadedPersons(loadedPersons);
    }
    root.getChildren().add(loginPage);
  }

  private void moveToMainPage(final Person person) {
    root.getChildren().clear();
    MainPage mainPage = new MainPage(person);
    mainPage.addListener(this);
    if (loadedRooms != null) {
      mainPage.addRooms(loadedRooms);
    }
    root.getChildren().add(mainPage);
  }

  private void load() {
    Loader loader = new Loader();
    try {
      loader.loadData();
      loadedPersons = loader.getPersons();
      loadedRooms = loader.getRooms();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void save() {
    Saver saver = new Saver();
    try {
      saver.writeToFile(loadedRooms, loadedPersons);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

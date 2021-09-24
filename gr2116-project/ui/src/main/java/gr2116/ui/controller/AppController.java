package gr2116.ui.controller;

import java.util.Collection;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import gr2116.core.HotelRoom;
import gr2116.core.Person;
import gr2116.core.Reservation;
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
        Loader loader = new Loader();
        try {
            loader.loadData();
            loadedPersons = loader.getPersons();
            loadedRooms = loader.getRooms();
        } catch (Exception e) {
            e.printStackTrace();
        }
        moveToLoginPage();
    }
    @Override
    public void receiveNotification(Object from, Message message, Object data) {
        if (message == Message.SignIn && data instanceof Person) {
            Person person = (Person) data;
            moveToMainPage(person);
        }
        if (message == Message.SignOut) {
            moveToLoginPage();
            Saver saver = new Saver();

            ArrayList<Reservation> reservations = new ArrayList<Reservation>();

            // TODO: If a new person is created while in the program, this will be missing from the list.
            loadedPersons.forEach((Person p) -> reservations.addAll(p.getReservations()));
            try {
                saver.writeToFile(loadedRooms, loadedPersons, reservations);
            } catch (Exception e) {
                e.printStackTrace();
                // TODO;
            }
        }
    }
    
    private void moveToLoginPage() {
        root.getChildren().clear();
        LoginPage loginPage = new LoginPage();
        loginPage.addListener(this);
        loginPage.setLoadedPersons(loadedPersons);

        root.getChildren().add(loginPage);
    }
    
    private void moveToMainPage(Person person) {
        root.getChildren().clear();
        MainPage mainPage = new MainPage(person);
        mainPage.addListener(this);
        mainPage.addRooms(loadedRooms);
        root.getChildren().add(mainPage);
    }
}

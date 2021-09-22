package gr2116.ui.controller;

import gr2116.core.Person;
import gr2116.ui.components.MessageListener;
import gr2116.ui.message.Message;
import gr2116.ui.pages.LoginPage;
import gr2116.ui.pages.MainPage;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class AppController implements MessageListener {

    @FXML
    private StackPane root;

    @FXML
    private void initialize() {
        LoginPage loginPage = new LoginPage();
        loginPage.addListener(this);
        root.getChildren().add(loginPage);
    }

    @Override
    public void receiveNotification(Object from, Message message, Object data) {
        if (message == Message.SignIn && data instanceof Person) {
            root.getChildren().clear();
            Person person = (Person) data;
            MainPage mainPage = new MainPage(person);
            mainPage.addListener(this);
            root.getChildren().add(mainPage);
        }
        if (message == Message.SignOut) {
            root.getChildren().clear();
            LoginPage loginPage = new LoginPage();
            loginPage.addListener(this);
            root.getChildren().add(loginPage);
        }
    }
}

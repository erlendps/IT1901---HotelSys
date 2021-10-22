package gr2116.ui;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.api.FxAssert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gr2116.ui.controller.AppController;


/**
 * FXML Application, the booking system application.
 */

public class LoginTest extends ApplicationTest{
  AppController appController;
  /**
   * Start the app, load FXML and show scene.
   * @throws IOException
   */

  @Start
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("App.fxml"));
    Parent parent = fxmlLoader.load();
    appController = (AppController) fxmlLoader.getController();
    appController.setPrefix("testUi");
    appController.load();
    stage.setScene(new Scene(parent));
    stage.show();
  }
  
  @Test
  public void makeNewUserTest() {
    FxAssert.verifyThat("#signInButton", LabeledMatchers.hasText("Sign in"));
    clickOn("#emailTextField").write("jonathan@people.com");
    clickOn("#signInButton");
    clickOn("#nameTextField").write("Jonathan Spark");
    clickOn("#signInButton");
  }

  @Test
  public void loginUserTest() {
    FxAssert.verifyThat("#signInButton", LabeledMatchers.hasText("Sign in"));
    clickOn("#emailTextField").write("tom@richpeople.org");
    clickOn("#signInButton");
  }

  @Test
  public void testInvalidEmail() {
    FxAssert.verifyThat("#signInButton", LabeledMatchers.hasText("Sign in"));
    clickOn("#emailTextField").write("tp^skm@f?ake.email");
    clickOn("#signInButton");
    FxAssert.verifyThat("#emailErrorLabel", LabeledMatchers.hasText("Invalid email!"));

  }
}
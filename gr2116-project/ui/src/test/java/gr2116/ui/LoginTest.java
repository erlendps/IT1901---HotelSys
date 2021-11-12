package gr2116.ui;

import gr2116.ui.controller.AppController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

/**
 * FXML Application, the booking system application.
 */

public class LoginTest extends ApplicationTest {
  AppController appController;
  /**
   * Start the app, load FXML and show scene.
   *
   * @throws IOException if App.fxml cannot be loaded
   */

  @Start
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("App.fxml"));
    Parent parent = fxmlLoader.load();
    stage.setScene(new Scene(parent));
    stage.show();
    appController = (AppController) fxmlLoader.getController();
    appController.setPrefix("test");
    appController.load();
  }
  
  @Test
  public void makeNewUserTest() {
    FxAssert.verifyThat("#signInButton", LabeledMatchers.hasText("Sign in"));
    clickOn("#usernameTextField").write("jonathan");
    clickOn("#signInButton");
    clickOn("#nameTextField").write("Jonathan Spark");
    clickOn("#signInButton");
  }

  @Test
  public void loginUserTest() {
    FxAssert.verifyThat("#signInButton", LabeledMatchers.hasText("Sign in"));
    clickOn("#usernameTextField").write("tom");
    clickOn("#signInButton");
  }

  @Test
  public void testInvalidUsername() {
    FxAssert.verifyThat("#signInButton", LabeledMatchers.hasText("Sign in"));
    clickOn("#usernameTextField").write("tp^skm@f?");
    clickOn("#signInButton");
    FxAssert.verifyThat("#usernameErrorLabel", LabeledMatchers.hasText("Invalid username!"));

  }
}
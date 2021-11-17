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

public class FrontPageTest extends ApplicationTest {
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
  public void signUpTest() {
    FxAssert.verifyThat("#startSignUpButton", LabeledMatchers.hasText("Sign up"));
    clickOn("#startSignUpButton");
    FxAssert.verifyThat("#signUpButton", LabeledMatchers.hasText("Sign up"));
    clickOn("#signUpButton");
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.InvalidUsername.getMessage()));
    clickOn("#usernameTextField").write("jonathan");
    clickOn("#signUpButton");
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.InvalidFirstName.getMessage()));
    clickOn("#firstNameTextField").write("Jonathan");
    clickOn("#signUpButton");
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.InvalidLastName.getMessage()));
    clickOn("#lastNameTextField").write("Spark");
    clickOn("#signUpButton");
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.InvalidPassword.getMessage()));
    clickOn("#passwordTextField").write("password123");
    clickOn("#signUpButton");
    FxAssert.verifyThat("#nameLabel",
        LabeledMatchers.hasText("Jonathan Spark"));
  }

  @Test
  public void loginTest() {
    FxAssert.verifyThat("#startLoginButton", LabeledMatchers.hasText("Login"));
    clickOn("#startLoginButton");
    FxAssert.verifyThat("#loginButton", LabeledMatchers.hasText("Login"));
    clickOn("#loginButton");
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.InvalidUsername.getMessage()));
    clickOn("#usernameTextField").write("thomas");
    clickOn("#loginButton");
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.InvalidPassword.getMessage()));
    clickOn("#passwordTextField").write("password1234");
    clickOn("#loginButton");
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.UsernameHasNoMatches.getMessage()));
    clickOn("#usernameTextField").eraseText(6).write("tom"); 
    clickOn("#loginButton");
    FxAssert.verifyThat("#errorLabel",
        LabeledMatchers.hasText(DynamicText.WrongPassword.getMessage()));
    clickOn("#passwordTextField").eraseText(1);
    clickOn("#loginButton");
    FxAssert.verifyThat("#nameLabel",
        LabeledMatchers.hasText("Tom Hanks"));
  }
}